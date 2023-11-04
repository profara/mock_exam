import {Box, Button, Flex, Select, Spinner, Text} from "@chakra-ui/react";
import {useEffect, useState} from "react";
import Simple from "../shared/NavBar.jsx";
import {
    getCandidatesByAppointment,
    getAllCandidatesByAppointment,
    getAllCities,
    getAllSchools, filterCandidatesByAppointment
} from "../../services/client.js";
import CandidatesByAppointmentHeader from "./CandidatesByAppointmentHeader.jsx";
import CandidateByAppointmentCard from "./CandidateByAppointmentCard.jsx";
import {useLocation} from "react-router-dom";
import {DownloadIcon} from "@chakra-ui/icons";
import * as XLSX from "xlsx";
import saveAs from "file-saver";

const CandidatesByAppointment = () => {
    const [candidates, setCandidates] = useState([]);
    const [cities, setCities] = useState([]);
    const [schools, setSchools] = useState([]);
    const [selectedCity, setSelectedCity] = useState(null);
    const [selectedSchool, setSelectedSchool] = useState(null);
    const [hasAttendedPreparation, setHasSelectedPreparation] = useState('');
    const [totalCandidates, setTotalCandidates] = useState(0);
    const [page, setPage] = useState(0);
    const [loading, setLoading] = useState(true);
    const location = useLocation();
    const appointmentId = location.state?.id;
    const pageSize = 20;
    const fetchCandidates = (page) => {
        getCandidatesByAppointment(appointmentId, page)
            .then(res => {
                setCandidates(res.data.content);
                setTotalCandidates(res.data.totalElements)
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
        filterCandidatesByAppointment(appointmentId, selectedCity, selectedSchool, hasAttendedPreparation, page, pageSize)
            .then(res => {
                setCandidates(res.data.content);
                setPage(0);
            }).catch(err => {
            console.error(err);
        })
    }

    const wscols = [
        {wch: 5},
        {wch: 10},
        {wch: 15},
        {wch: 25},
        {wch: 10},
        {wch: 25},
        {wch: 30}
    ]

    const handleExportToExcel = () => {
        getAllCandidatesByAppointment(appointmentId, selectedCity, selectedSchool, hasAttendedPreparation)
            .then(res => {
                const mappedCandidates = res.data.map(candidate => ({
                    'ID': candidate.id,
                    'Ime': candidate.name,
                    'Prezime': candidate.surname,
                    'Email': candidate.userProfile.email,
                    'Grad': candidate.city.name,
                    'Adresa': candidate.address,
                    'Skola': candidate.school.name

                }))

                const wb = XLSX.utils.book_new();
                const ws = XLSX.utils.json_to_sheet(mappedCandidates);
                ws['!cols'] = wscols;
                XLSX.utils.book_append_sheet(wb, ws, "Candidates");
                const wbout = XLSX.write(wb, {bookType: 'xlsx', type: 'binary'});

                const s2ab = (s) => {
                    const buf = new ArrayBuffer(s.length);
                    const view = new Uint8Array(buf);
                    for (let i = 0; i < s.length; i++) {
                        view[i] = s.charCodeAt(i) & 0xFF;
                    }
                    return buf;
                }

                saveAs(new Blob([s2ab(wbout)], {type: "application/octet-stream"}), "candidates.xlsx");
            }).catch(err => {
            console.error(err)
        })
    };

    useEffect(() => {
        fetchCandidates(page)
    }, [page])

    useEffect(() => {
        fetchCities();
        fetchSchools();
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
                <Button
                    position="absolute"
                    top="24"
                    right="4"
                    leftIcon={<DownloadIcon/>}
                    onClick={handleExportToExcel}
                    colorScheme="teal"
                >
                    Eksportuj u Excel
                </Button>
                <CandidatesByAppointmentHeader
                    id={appointmentId}
                    page={page}
                    size={pageSize}
                    setCandidates={setCandidates}
                    selectedCity={selectedCity}
                    selectedSchool={selectedSchool}
                    hasAttendedPreparation={hasAttendedPreparation}
                />
                {candidates.map((candidate, index) => (
                    <CandidateByAppointmentCard
                        key={candidate.id}
                        candidate={candidate}
                        isOdd={index % 2 !== 0}
                        rowNum={page * pageSize + index + 1}
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

export default CandidatesByAppointment;