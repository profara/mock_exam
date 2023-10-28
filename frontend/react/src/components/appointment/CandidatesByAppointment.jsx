import {Box, Flex, Spinner, Text} from "@chakra-ui/react";
import {useEffect, useState} from "react";
import Simple from "../shared/NavBar.jsx";
import {getCandidatesByAppointment} from "../../services/client.js";
import CandidatesByAppointmentHeader from "./CandidatesByAppointmentHeader.jsx";
import CandidateByAppointmentCard from "./CandidateByAppointmentCard.jsx";
import {useLocation} from "react-router-dom";

const CandidatesByAppointment = () => {
    const [candidates, setCandidates] = useState([]);
    const [totalCandidates, setTotalCandidates] = useState(0);
    const [loading, setLoading] = useState(true);
    const location = useLocation();
    const appointmentId = location.state?.id;
    const fetchCandidates = () => {
        getCandidatesByAppointment(appointmentId)
            .then(res => {
                console.log(res)
                setCandidates(res.data.content);
                setTotalCandidates(res.data.totalElements)
            }).catch(err => {
            console.error(err)
        }).finally(() => {
            setLoading(false);
        })
    }

    useEffect(() => {
        fetchCandidates()
    }, [])

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
                <Box mb={4} borderRadius="lg" bg="gray.100" p={4} shadow="md">
                    <Text fontSize="xl" fontWeight="bold">
                        Broj kandidata: {totalCandidates}
                    </Text>
                </Box>
                <CandidatesByAppointmentHeader/>
                {candidates.map((candidate, index) => (
                    <CandidateByAppointmentCard
                        key={index}
                        candidate={candidate}
                        isOdd={index % 2 !== 0}
                        rowNum={index + 1}
                    />
                ))}
            </Flex>
        </Simple>
    );
};

export default CandidatesByAppointment;