import {Flex, Text, Button, Box} from "@chakra-ui/react";
import UpdateCandidateProfileForm from "./UpdateCandidateProfileForm.jsx";
import {useState} from "react";
import {useNavigate} from "react-router-dom";
import {deleteAppointment, deleteCandidate} from "../../services/client.js";
import {errorNotification, successNotification} from "../../services/notification.js";

const CandidateCard = ({ candidate, isOdd, rowNum, fetchCandidates}) => {
    const navigate = useNavigate();

    const handleUpdateClick = () => {
        navigate('/updateProfil', {state: {selectedCandidate: candidate}});
    }

    const handleDeleteClick = () => {
        deleteCandidate(candidate.id).then(res =>{
            console.log(res)
            successNotification('Kandidat uspesno obrisan')
        }).catch(err => {
            console.log(err)
            errorNotification(
                err.code,
                err?.response.data?.violations[0]?.error
            )
        }).finally(() => {
            fetchCandidates();
        })
    }

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
                <Button colorScheme="teal" mr={2} onClick={handleUpdateClick}>
                    Update
                </Button>
                <Button colorScheme="red" onClick={handleDeleteClick}>
                    Delete
                </Button>
            </Flex>
        </Flex>
    );
};

export default CandidateCard;