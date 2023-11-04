import {Box, Button, Flex, Select, Spinner} from "@chakra-ui/react";
import CandidateCard from "./CandidateCard.jsx";
import {useEffect, useState} from "react";
import {
    filterCandidates,
    getAllCities,
    getAllSchools,
    getCandidates
} from "../../services/client.js";
import CandidatesHeader from "./CandidatesHeader.jsx";
import Simple from "../shared/NavBar.jsx";

const CandidateList = () => {
    const [candidates, setCandidates] = useState([]);
    const [cities, setCities] = useState([]);
    const [schools, setSchools] = useState([]);
    const [loading, setLoading] = useState(true);
    const [page, setPage] = useState(0);
    const pageSize = 20;
    const [selectedCity, setSelectedCity] = useState(null);
    const [selectedSchool, setSelectedSchool] = useState(null);
    const [hasAttendedPreparation, setHasSelectedPreparation] = useState('');

    const fetchCandidates = (page) => {
        getCandidates(page)
            .then(res => {
                setCandidates(res.data.content);
            }).catch(err => {
            console.error(err)
        }).finally(() => {
            setLoading(false);
        })
    }

    const fetchCities = () => {
        getAllCities()
            .then(res => {
                setCities(res.data);
            }).catch(err => {
            console.error(err)
        })
    }

    const fetchSchools = () => {
        getAllSchools()
            .then(res => {
                setSchools(res.data);
            }).catch(err => {
            console.error(err)
        })
    }

    const handleFiltrirajClick = () => {
        filterCandidates(selectedCity, selectedSchool, hasAttendedPreparation, page, pageSize)
            .then(res => {
                setCandidates(res.data.content);
                setPage(0);
            }).catch(err => {
            console.error(err);
        })
    }

    useEffect(() => {
        fetchCities();
        fetchSchools();
    }, [])

    useEffect(() => {
        fetchCandidates(page)
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
                <Flex direction="row" w="100%" justifyContent="center" mb={4}>
                    <Select placeholder="Izaberite grad" w="200px" mr={2} value={selectedCity} onChange={e => setSelectedCity(e.target.value || null)}>
                        {cities.map(city => <option key={city.zipCode} value={city.zipCode}>{city.name}</option> )}
                    </Select>

                    <Select placeholder="Izaberite skolu" w="200px" mr={2} value={selectedSchool} onChange={e => setSelectedSchool(e.target.value || null)}>
                        {schools.map(school => <option key={school.code} value={school.code}>{school.name}</option>)}
                    </Select>

                    <Select placeholder="Isao na pripremu" w="200px" mr={2} value={hasAttendedPreparation} onChange={e => setHasSelectedPreparation(e.target.value || "")}>
                        <option value='true'>Da</option>
                        <option value='false'>Ne</option>
                    </Select>

                    <Button bg={'teal'}
                            color={'white'}
                            rounded={'full'}
                            _hover={{
                                transform: 'translateY(-2px)',
                                boxShadow: 'lg'
                            }}
                            onClick={handleFiltrirajClick}
                    >
                        Filtriraj
                    </Button>
                </Flex>
                <CandidatesHeader
                    page={page}
                    size={pageSize}
                    setCandidates={setCandidates}
                    selectedCity={selectedCity}
                    selectedSchool={selectedSchool}
                    hasAttendedPreparation={hasAttendedPreparation}
                />
                {candidates.map((candidate, index) => (
                    <CandidateCard
                        key={candidate.id}
                        candidate={candidate}
                        isOdd={index % 2 !== 0}
                        rowNum={page * pageSize + index + 1}
                        fetchCandidates={fetchCandidates}
                        page={page}
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

export default CandidateList;