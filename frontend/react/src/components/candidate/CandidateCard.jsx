import {Flex, Text, Button, Box} from "@chakra-ui/react";

const CandidateCard = ({ candidate, isOdd, rowNum}) => {
    return (
        <Flex
            alignItems="center"
            p={4}
            borderBottom="1px solid"
            borderColor="gray.200"
            bg={isOdd ? "gray.100" : "white"}
            justifyContent="space-between"
            w={"100%"}
        >

            <Box ml={4} w="5%">
                <Text>{rowNum}.</Text>
            </Box>
            <Box w="15%">
                <Text>{candidate.name}</Text>
            </Box>
            <Box w="15%">
                <Text>{candidate.surname}</Text>
            </Box>
            <Box w="45%">
                <Text>{candidate.userProfile.email}</Text>
            </Box>
            <Flex w="20%" justifyContent="flex-end" mr={4}>
                <Button colorScheme="teal" mr={2}>Update</Button>
                <Button colorScheme="red">Delete</Button>
            </Flex>
        </Flex>
    );
};

export default CandidateCard;