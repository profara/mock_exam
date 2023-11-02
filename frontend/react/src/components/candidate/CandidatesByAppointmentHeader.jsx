import {Box, Flex, Text} from "@chakra-ui/react";

const CandidatesByAppointmentHeader = () => {
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
                <Text fontWeight="bold">Ime</Text>
            </Box>
            <Box w="12.25%">
                <Text fontWeight="bold">Prezime</Text>
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
