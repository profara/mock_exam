import {
    Flex,
    Text,
    Button,
    Box,
    useDisclosure,
    AlertDialog,
    AlertDialogOverlay,
    AlertDialogContent, AlertDialogHeader, AlertDialogBody, AlertDialogFooter
} from "@chakra-ui/react";
import React from "react";
import {useNavigate} from "react-router-dom";
import {deleteCandidate} from "../../services/client.js";
import {errorNotification, successNotification} from "../../services/notification.js";

const CandidateCard = ({ candidate, isOdd, rowNum, fetchCandidates, page}) => {
    const { isOpen, onOpen, onClose } = useDisclosure();
    const cancelRef = React.useRef();
    const navigate = useNavigate();

    const handleUpdateClick = () => {
        navigate('/updateProfil', {state: {selectedCandidate: candidate}});
    }

    const handleDeleteClick = () => {
        deleteCandidate(candidate.id).then(res =>{
            console.log(res)
            successNotification('Kandidat uspesno izbrisan')
        }).catch(err => {
            console.log(err)
            errorNotification(
                err.code,
                err?.response.data.message
            )
        }).finally(() => {
            fetchCandidates(page);
        })
    }

    const handleOdjaviClick = () => {
        navigate("/mojePrijave", {state:{candidate: candidate, signed: true}})
    }

    const handlePrijaviClick = () => {
        navigate("/mojePrijave", {state:{candidate: candidate, signed: false}})
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

            <Box ml={4} w="2%">
                <Text>{rowNum}.</Text>
            </Box>
            <Box w="14%">
                <Text>{candidate.name}</Text>
            </Box>
            <Box w="14%">
                <Text>{candidate.surname}</Text>
            </Box>
            <Box w="14%">
                <Text>{candidate.userProfile.email}</Text>
            </Box>
            <Box w="14%">
                <Text>{candidate.city.name}</Text>
            </Box>
            <Box w="18%">
                <Text>{candidate.school.name}</Text>
            </Box>
            <Box w="10%">
                <Text>{candidate.attendedPreparation ? 'Da' : 'Ne'}</Text>
            </Box>
            <Flex w="14%" justifyContent="flex-end" mr={4}>
                <Button colorScheme="red" mr={2} onClick={handleOdjaviClick}>
                    Odjavi
                </Button>
                <Button colorScheme="teal" mr={2} onClick={handlePrijaviClick}>
                    Prijavi
                </Button>
                <Button colorScheme="teal" mr={2} onClick={handleUpdateClick}>
                    Azuriraj
                </Button>
                <>
                <Button colorScheme="red" onClick={onOpen}>
                    Izbrisi
                </Button>
                    <AlertDialog
                        isOpen={isOpen}
                        leastDestructiveRef={cancelRef}
                        onClose={onClose}
                    >
                        <AlertDialogOverlay>
                            <AlertDialogContent>
                                <AlertDialogHeader fontSize='lg' fontWeight='bold'>
                                    Brisanje kandidata
                                </AlertDialogHeader>

                                <AlertDialogBody>
                                    Da li ste sigurni da zelite da izbrisete kandidata?
                                </AlertDialogBody>

                                <AlertDialogFooter>
                                    <Button ref={cancelRef} onClick={onClose}>
                                        Odustani
                                    </Button>
                                    <Button colorScheme='red' onClick={handleDeleteClick} ml={3}>
                                        Izbrisi
                                    </Button>
                                </AlertDialogFooter>
                            </AlertDialogContent>
                        </AlertDialogOverlay>
                    </AlertDialog>
                </>
            </Flex>
        </Flex>
    );
};

export default CandidateCard;