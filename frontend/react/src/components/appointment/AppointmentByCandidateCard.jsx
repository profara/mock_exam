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
import {errorNotification, successNotification} from "../../services/notification.js";
import {format, utcToZonedTime} from "date-fns-tz";
import {useAppointmentOrder} from "../context/AppointmentOrderContext.jsx";
import {cancelAppointment, signCandidateForAppointment} from "../../services/client.js";

const AppointmentByCandidateCard = ({appointment, isOdd, rowNum, fetchAppointments, page, candidate, signed}) => {
    const {isOpen, onOpen, onClose} = useDisclosure();
    const cancelRef = React.useRef();
    const cetDate = utcToZonedTime(appointment.appointmentDate, 'Europe/Belgrade');
    const displayDate = format(cetDate, 'dd.MM.yyyy', {timeZone: 'Europe/Belgrade'});
    const displayTime = format(cetDate, 'HH:mm', {timeZone: 'Europe/Belgrade'})
    const {getOrderForAppointment} = useAppointmentOrder();
    const order = getOrderForAppointment(appointment);

    const handleOdjaviClick = () => {
        cancelAppointment(candidate.id, appointment.id)
            .then(res => {
                successNotification('Termin uspesno odjavljen')
            }).catch(err => {
            console.log(err)
            errorNotification(
                err.code,
                err?.response.data.message
            )
        }).finally(() => {
            fetchAppointments(page);
        })
    }

    const handlePrijaviClick = () => {
        signCandidateForAppointment(candidate.id, appointment.id)
            .then(res => {
                successNotification('Termin uspesno prijavljen')
            }).catch(err => {
            errorNotification(
                err.code,
                err?.response.data.message
            )
        }).finally(() => {
            fetchAppointments(page);
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

            <Box ml={4} w="10%">
                <Text>{rowNum}.</Text>
            </Box>
            <Box w="23.3%">
                <Text>{appointment.exam.name} - {order}. termin</Text>
            </Box>

            <Box w="23.3%">
                <Text>{displayDate}</Text>
            </Box>

            <Box w="23.3%">
                <Text>{displayTime}</Text>
            </Box>

            <Flex w="20%" justifyContent="flex-end" mr={4}>
                {signed ? (
                    <>
                        <Button colorScheme="red" onClick={onOpen}>
                            Odjavi
                        </Button>
                        <AlertDialog
                            isOpen={isOpen}
                            leastDestructiveRef={cancelRef}
                            onClose={onClose}
                        >
                            <AlertDialogOverlay>
                                <AlertDialogContent>
                                    <AlertDialogHeader fontSize='lg' fontWeight='bold'>
                                        Odjavljivanje termina
                                    </AlertDialogHeader>

                                    <AlertDialogBody>
                                        Da li ste sigurni da zelite da odjavite prijavu?
                                    </AlertDialogBody>

                                    <AlertDialogFooter>
                                        <Button ref={cancelRef} onClick={onClose}>
                                            Odustani
                                        </Button>
                                        <Button colorScheme='red' onClick={handleOdjaviClick} ml={3}>
                                            Odjavi
                                        </Button>
                                    </AlertDialogFooter>
                                </AlertDialogContent>
                            </AlertDialogOverlay>
                        </AlertDialog>
                    </>
                ) : (
                    <Button colorScheme="teal" onClick={handlePrijaviClick}>
                        Prijavi
                    </Button>
                )}

            </Flex>
        </Flex>
    );
};

export default AppointmentByCandidateCard;
