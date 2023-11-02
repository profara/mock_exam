import {Wrap, WrapItem, Spinner, Text, Button, Center} from '@chakra-ui/react';
import Simple from "./components/shared/NavBar.jsx";
import {useEffect, useState} from "react";
import {getAppointments, getExams, getPriceListItems} from "./services/client.js";
import Card from "./components/appointment/Card.jsx";
import {useNavigate} from "react-router-dom";
import {useAuth} from "./components/context/AuthContext.jsx";
import {useCard} from "./components/context/SelectedCardsContext.jsx";
import {useApplication} from "./components/context/ApplicationContext.jsx";
import {createApplication, getCurrentDateInSerbiaTimeZone} from "./utils/appUtils.js";
import {DEFAULT_CURRENCY_CODE} from "./components/payslip/config/constants.js";
import CreateAppointmentDrawer from "./components/appointment/CreateAppointmentDrawer.jsx";
import {useAppointmentOrder} from "./components/context/AppointmentOrderContext.jsx";


const App = () => {

    const [appointments, setAppointments] = useState([]);
    const [loading, setLoading] = useState(false);
    const {selectedCards, setSelectedCards} = useCard();
    const [err, setError] = useState("");
    const navigate = useNavigate();
    const {candidate, isAdmin, loadingAuth, user} = useAuth();
    const serbiaDate = getCurrentDateInSerbiaTimeZone();
    const {setApplication} = useApplication();
    const [examNames, setExamNames] = useState([]);
    const currentYear = new Date(serbiaDate).getFullYear().toString();
    const [priceListItem, setPriceListItem] = useState(null);
    const {getOrderForAppointment} = useAppointmentOrder();
    let examResponse;
    let appointmentsResponse;

    const handlePrijaviClick = (candidate) => {
        if (candidate) {
            createApplication(candidate, serbiaDate, selectedCards, setApplication, navigate);
        } else {
            navigate("/profil")
        }

    }
    const toogleCardSelection = (id) => {
        setSelectedCards(prevState => {
            if (prevState.includes(id)) {
                return prevState.filter(cardId => cardId !== id);
            } else {
                return [...prevState, id];
            }

        });
    }


    const fetchAppointmentsForAdmin = () => {
        setLoading(true);

        Promise.all([getExams(), getAppointments()])
            .then(([examsRes, appointmentsRes]) => {
                examResponse = examsRes.data.content.map(exam => exam.name);
                appointmentsResponse = appointmentsRes.data.content;

                setExamNames(examResponse);
                setAppointments(appointmentsResponse);

            }).catch(err => {
            setError(err.response.data.message);
        }).finally(() => {
            setLoading(false);
        })
    }

    const fetchAppointments = () => {
        setLoading(true);

        Promise.all([getExams(), getAppointments(), getPriceListItems()])
            .then(([examsRes, appointmentsRes, priceListItemsRes]) => {

                examResponse = examsRes.data.content.map(exam => exam.name);
                appointmentsResponse = appointmentsRes.data.content;

                const priceMap = {};
                appointmentsResponse.forEach(appointment => {

                    const matchingPriceItem = priceListItemsRes.data.content.find(item =>
                        item.priceList.year === currentYear &&
                        item.privileged === (candidate?.attendedPreparation ?? false) &&
                        item.exam.id === appointment.exam.id &&
                        item.currency.code === DEFAULT_CURRENCY_CODE
                    );
                    if (matchingPriceItem) {
                        priceMap[appointment.id] = matchingPriceItem;
                    }

                });

                setExamNames(examResponse);
                setAppointments(appointmentsResponse);
                setPriceListItem(priceMap);
            }).catch(err => {
            setError(err.response.data.message);
        }).finally(() => {
            setLoading(false);
        })

    }

    useEffect(() => {
        if (user && isAdmin()) {
            fetchAppointmentsForAdmin();
        }
    }, [user]);



    useEffect(() => {
        if (!isAdmin()) {
            fetchAppointments();
        }
    }, [candidate, user]);

    if (loading || loadingAuth) {
        return (
            <Simple>
                <Spinner
                    thickness='4px'
                    speed='0.65s'
                    emptyColor='gray.200'
                    color='blue.500'
                    size='xl'
                />
            </Simple>
        )
    }

    if (err) {
        return (
            <Simple>
                <Text>Doslo je do greske</Text>
            </Simple>
        )
    }

    if(!isAdmin() && !priceListItem){
        return (
            <Simple>
                <Text mt={5}>Nema dostupnih termina</Text>
            </Simple>
        )
    }

    if (appointments.length <= 0) {
        return (
            <Simple>
                {isAdmin() && (
                    <CreateAppointmentDrawer
                    fetchAppointments={fetchAppointmentsForAdmin}
                    />
                )}
                <Text mt={5}>Nema dostupnih termina</Text>
            </Simple>
        )
    }

    return (
        <Simple>
            {isAdmin() && (
                <CreateAppointmentDrawer
                    fetchAppointments={fetchAppointmentsForAdmin}
                />
            )}
            <Wrap justify={"center"} spacing={"30px"}>
                {appointments.map((appointment) => {

                    return (
                        <WrapItem key={appointment.id}>

                            <Card
                                {...appointment}
                                priceListItem={isAdmin() ? null : priceListItem[appointment.id]}
                                toogleCardSelection={() => toogleCardSelection(appointment.id)}
                                isSelected={selectedCards.includes(appointment.id)}
                                fetchAppointments={isAdmin() ? fetchAppointmentsForAdmin : fetchAppointments}
                                order={getOrderForAppointment(appointment)}
                            />
                        </WrapItem>
                    );
                })}

            </Wrap>
            {!isAdmin() && (
            <Center mt={10}>
                <Button colorScheme="green" size="lg" isDisabled={selectedCards.length === 0}
                        onClick={() => handlePrijaviClick(candidate)}>
                    Prijavi
                </Button>
            </Center>
            ) }
        </Simple>
    )
}

export default App;