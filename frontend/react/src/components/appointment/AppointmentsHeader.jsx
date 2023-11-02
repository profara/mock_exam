import {Box, Flex, Text} from "@chakra-ui/react";

const AppointmentsHeader = () => {
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
            <Box ml={4} w="10%">
                <Text>Br.</Text>
            </Box>
            <Box w="23.3%">
                <Text fontWeight="bold">Naziv</Text>
            </Box>
            <Box w="23.3%">
                <Text fontWeight="bold">Datum</Text>
            </Box>
            <Box w="43.3%" mr={4}>
                <Text>Vreme</Text>
            </Box>
        </Flex>
    );
};

export default AppointmentsHeader;
