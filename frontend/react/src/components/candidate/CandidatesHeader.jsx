import {Box, Flex, IconButton, Text} from "@chakra-ui/react";
import {ChevronDownIcon, ChevronUpIcon} from "@chakra-ui/icons";
import {getSortedCandidatesByColumn} from "../../services/client.js";

const CandidatesHeader = ({page, size, setCandidates}) => {
    function handleSort(column, direction) {
        getSortedCandidatesByColumn(page,size, column, direction)
            .then(res => {
                setCandidates(res.data.content);
            }).catch(err => {
            console.error(err);
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
            <Box w="14%">
                <Flex  alignItems="center">
                    <Text fontWeight="bold" mr={4}>Ime</Text>
                    <Flex direction="column">
                        <IconButton
                            aria-label="Sort Ascending"
                            icon={<ChevronUpIcon />}
                            size="xss"
                            onClick={() => handleSort('name', 'asc')}
                        />
                        <IconButton
                            aria-label="Sort Descending"
                            icon={<ChevronDownIcon />}
                            size="xss"
                            onClick={() => handleSort('name', 'desc')}
                        />
                    </Flex>
                </Flex>
            </Box>
            <Box w="14%">
                <Flex  alignItems="center">
                    <Text fontWeight="bold" mr={4}>Prezime</Text>
                    <Flex direction="column">
                        <IconButton
                            aria-label="Sort Ascending"
                            icon={<ChevronUpIcon />}
                            size="xss"
                            onClick={() => handleSort('surname', 'asc')}
                        />
                        <IconButton
                            aria-label="Sort Descending"
                            icon={<ChevronDownIcon />}
                            size="xss"
                            onClick={() => handleSort('surname', 'desc')}
                        />
                    </Flex>
                </Flex>
            </Box>
            <Box w="14%">
                <Text>Email</Text>
            </Box>
            <Box w="14%">
                <Text>Grad</Text>
            </Box>
            <Box w="18%">
                <Text>Skola</Text>
            </Box>
            <Box w="15%">
                <Text>Isao na pripremu</Text>
            </Box>
            <Box w="10%">
                <Text>Akcije</Text>
            </Box>

        </Flex>
    );
};

export default CandidatesHeader;
