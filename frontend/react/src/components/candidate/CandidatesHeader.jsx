import {Box, Button, Flex, Text} from "@chakra-ui/react";

const CandidatesHeader = () => {
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
            <Box ml={4} w="5%">
                <Text>Br.</Text>
            </Box>
            <Box w="15%">
                <Text fontWeight="bold">Ime</Text>
            </Box>
            <Box w="15%">
                <Text fontWeight="bold">Prezime</Text>
            </Box>
            <Box w="65%">
                <Text>Email</Text>
            </Box>
        </Flex>
    );
};

export default CandidatesHeader;
