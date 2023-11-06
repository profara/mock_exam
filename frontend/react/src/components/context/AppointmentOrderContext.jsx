import {createContext, useContext, useEffect, useState} from "react";
import {getAllSortedAppointments} from "../../services/client.js";
import {useAuth} from "./AuthContext.jsx";


const AppointmentOrderContext = createContext({});

export const useAppointmentOrder = () => useContext(AppointmentOrderContext);


const AppointmentOrderProvider = ({ children }) => {
    const [orderMap, setOrderMap] = useState({});
    const {isUserAuthenticated, user} = useAuth();

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
        } catch (err) {
            console.error(err);
        }
    };

    useEffect(() => {

        if(isUserAuthenticated()) {
            fetchAppointments();
        }
    }, [user]);

    const updateOrderAfterDeletion = () => {
        fetchAppointments();
    }

    const getOrderForAppointment = (appointment) => {
        const key = `${appointment.id}`;
        return orderMap[key];
    };

    return (
        <AppointmentOrderContext.Provider
            value={{getOrderForAppointment, updateOrderAfterDeletion}}
        >
            {children}
        </AppointmentOrderContext.Provider>
    );
}

export default AppointmentOrderProvider;
