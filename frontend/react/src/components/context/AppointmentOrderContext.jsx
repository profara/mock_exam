import {createContext, useContext, useEffect, useState} from "react";
import {getAllSortedAppointments} from "../../services/client.js";


const AppointmentOrderContext = createContext({});

export const useAppointmentOrder = () => useContext(AppointmentOrderContext);


const AppointmentOrderProvider = ({ children }) => {
    const [orderMap, setOrderMap] = useState({});

    useEffect(() => {
        const fetchAppointments = async () => {
            try {
                const response = await getAllSortedAppointments();
                const allAppointments = response.data;

                // Group the appointments by exam name
                const groupedAppointments = allAppointments.reduce((acc, appointment) => {
                    (acc[appointment.exam.name] = acc[appointment.exam.name] || []).push(appointment);
                    return acc;
                }, {});

                // Sort each group by date
                Object.values(groupedAppointments).forEach(group => {
                    group.sort((a, b) => new Date(a.appointmentDate) - new Date(b.appointmentDate));
                });

                // Create the order map
                const tempOrderMap = {};
                Object.entries(groupedAppointments).forEach(([examName, appointments]) => {
                    let currentOrder = 1; // Reset the order for each group
                    appointments.forEach(appointment => {
                        const key = `${appointment.id}`;
                        tempOrderMap[key] = currentOrder;
                        currentOrder++;
                    });
                });

                setOrderMap(tempOrderMap);
            } catch (error) {
                console.error(error);
            }
        };

        fetchAppointments();
    }, []);

    const getOrderForAppointment = (appointment) => {
        const key = `${appointment.id}`;
        return orderMap[key];
    };

    return (
        <AppointmentOrderContext.Provider value={{getOrderForAppointment}}>
            {children}
        </AppointmentOrderContext.Provider>
    );
}

export default AppointmentOrderProvider;
