import {
    Flex,
    Text,
    Box
} from "@chakra-ui/react";

const CandidateByAppointmentCard = ({ candidate, isOdd, rowNum}) => {


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

            <Box ml={4} w="2%">
                <Text>{rowNum}.</Text>
            </Box>
            <Box w="12.25%">
                <Text>{candidate.name}</Text>
            </Box>
            <Box w="12.25%">
                <Text>{candidate.surname}</Text>
            </Box>
            <Box w="12.25%">
                <Text>{candidate.userProfile.email}</Text>
            </Box>
            <Box w="12.25%">
                <Text>{candidate.city.name}</Text>
            </Box>
            <Box w="12.25%">
                <Text>{candidate.address}</Text>
            </Box>
            <Box w="12.25%">
                <Text>{candidate.school.name}</Text>
            </Box>
            <Box w="12.25%">
                <Text>{candidate.attendedPreparation ? 'Da' : 'Ne'}</Text>
            </Box>

        </Flex>
    );
};

export default CandidateByAppointmentCard;