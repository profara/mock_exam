import {Box, Flex, IconButton, Text} from "@chakra-ui/react";
import { ChevronDownIcon, ChevronUpIcon} from "@chakra-ui/icons";
import {sortCandidatesByColumnName} from "../../services/client.js";

const CandidatesByAppointmentHeader = ({id, page, size, setCandidates, selectedCity, selectedSchool, hasAttendedPreparation}) => {

    const handleSort = (columnName, direction) => {
        sortCandidatesByColumnName(id, selectedCity, selectedSchool, hasAttendedPreparation, page, size, columnName, direction)
            .then(res => {
                setCandidates(res.data.content);
            }).catch(err => {
            console.error(err)
        })
    }


    return (
        <Flex
            alignItems="center"
            p={4}
            borderBottom="2px solid"
            borderColor="gray.300"
            justifyContent="space-between"
            w="100%"
            bg="gray.400"
            fontWeight="bold"
        >
            <Box ml={4} w="2%">
                <Text>Br.</Text>
            </Box>
            <Box w="12.25%">
                <Flex  alignItems="center">
                    <Text fontWeight="bold" mr={4}>Ime</Text>
                    <Flex direction="column">
                        <IconButton
                            aria-label="Sort Ascending"
                            icon={<ChevronUpIcon />}
                            size="xss"
                            onClick={() => handleSort('candidate.name', 'asc')}
                        />
                        <IconButton
                            aria-label="Sort Descending"
                            icon={<ChevronDownIcon />}
                            size="xss"
                            onClick={() => handleSort('candidate.name', 'desc')}
                        />
                    </Flex>
                </Flex>
            </Box>
            <Box w="12.25%">
                <Flex  alignItems="center">
                    <Text fontWeight="bold" mr={4}>Prezime</Text>
                    <Flex direction="column">
                        <IconButton
                            aria-label="Sort Ascending"
                            icon={<ChevronUpIcon />}
                            size="xss"
                            onClick={() => handleSort('candidate.surname', 'asc')}
                        />
                        <IconButton
                            aria-label="Sort Descending"
                            icon={<ChevronDownIcon />}
                            size="xss"
                            onClick={() => handleSort('candidate.surname', 'desc')}
                        />
                    </Flex>
                </Flex>
            </Box>
            <Box w="12.25%">
                <Text>Email</Text>
            </Box>
            <Box w="12.25%">
                <Text>Grad</Text>
            </Box>
            <Box w="12.25%">
                <Text>Adresa</Text>
            </Box>
            <Box w="12.25%">
                <Text>Skola</Text>
            </Box>
            <Box w="12.25%">
                <Text>Isao na pripremu</Text>
            </Box>
        </Flex>
    );
};

export default CandidatesByAppointmentHeader;
