import {Box, Button, Flex, Spinner} from "@chakra-ui/react";
import {useEffect, useState} from "react";
import Simple from "../shared/NavBar.jsx";
import {getAppointmentsByCandidate} from "../../services/client.js";
import {useLocation} from "react-router-dom";
import AppointmentsHeader from "./AppointmentsHeader.jsx";
import AppointmentByCandidateCard from "./AppointmentByCandidateCard.jsx";

const AppointmentsByCandidate = () => {
    const [appointments, setAppointments] = useState([]);
    const [loading, setLoading] = useState(true);
    const [page, setPage] = useState(0);
    const pageSize = 20;
    const location = useLocation();
    const candidate = location.state?.candidate;

    const fetchAppointments = (page) => {
        getAppointmentsByCandidate(candidate.id, page)
            .then(res => {
                setAppointments(res.data.content);
            }).catch(err => {
            console.error(err)
        }).finally(() => {
            setLoading(false);
        })
    }


    useEffect(() => {
        fetchAppointments(page)
    }, [page])

    if (loading) {
        return <Spinner
            thickness='4px'
            speed='0.65s'
            emptyColor='gray.200'
            color='blue.500'
            size='xl'
        />
    }

    return (
        <Simple>
            <Flex direction="column" w="100%" alignItems="center" p={4}>
                <AppointmentsHeader/>
                {appointments.map((appointment, index) => (
                    <AppointmentByCandidateCard
                        key={appointment.id}
                        appointment={appointment}
                        isOdd={index % 2 !== 0}
                        rowNum={page * pageSize + index + 1}
                        fetchAppointments={fetchAppointments}
                        page={page}
                        candidate={candidate}
                    />

                ))}

                <Box mt={4}>
                    <Button bg={'teal'}
                            color={'white'}
                            rounded={'full'}
                            _hover={{
                                transform: 'translateY(-2px)',
                                boxShadow: 'lg'
                            }}
                            onClick={() => setPage(prev => prev - 1)}
                            disabled={page === 0}
                    >
                        Prethodna
                    </Button>
                    <Button bg={'teal'}
                            color={'white'}
                            rounded={'full'}
                            _hover={{
                                transform: 'translateY(-2px)',
                                boxShadow: 'lg'
                            }}
                            onClick={() => setPage(prev => prev + 1)}
                            ml={2}
                    >
                        Sledeca
                    </Button>
                </Box>
            </Flex>
        </Simple>
    );
};

export default AppointmentsByCandidate;