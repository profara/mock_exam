'use client'

import {
    Box,
    Center,
    Text,
    Stack,
    List,
    ListItem,
    useColorModeValue,
    Checkbox,
    Button,
    useDisclosure,
    AlertDialogOverlay,
    AlertDialogContent,
    AlertDialogHeader,
    AlertDialogFooter, AlertDialogBody, AlertDialog,
} from '@chakra-ui/react'
import {useAuth} from "../context/AuthContext.jsx";
import {useRef} from "react";
import {cancelAppointment, deleteAppointment} from "../../services/client.js";
import {errorNotification, successNotification} from "../../services/notification.js";
import UpdateAppointmentDrawer from "./UpdateAppointmentDrawer.jsx";
import {parseISO} from 'date-fns'
import {useNavigate} from "react-router-dom";
import {format, utcToZonedTime} from "date-fns-tz";
import {useAppointmentOrder} from "../context/AppointmentOrderContext.jsx";


export default function Card({id,exam, appointmentDate, toogleCardSelection, isSelected, priceListItem, fetchAppointments, order, appointment, hasApplied,candidate}) {
    const { isOpen, onOpen, onClose } = useDisclosure()
    const cancelRef = useRef()
    const {isAdmin} = useAuth();
    const cetDate = utcToZonedTime(appointmentDate, 'Europe/Belgrade');
    const displayDate = format(cetDate, 'dd.MM.yyyy', {timeZone: 'Europe/Belgrade'});
    const displayTime = format(cetDate, 'HH:mm', {timeZone: 'Europe/Belgrade'})
    const navigate = useNavigate();
    const {updateOrderAfterDeletion} = useAppointmentOrder();

    const handleOdjaviClick = () => {
        cancelAppointment(candidate.id, id)
            .then(res => {
                successNotification('Termin uspesno odjavljen')
            }).catch(err => {
            console.log(err)
            errorNotification(
                err.code,
                err?.response.data.message
            )
        }).finally(() => {
            fetchAppointments();
        })
    }


    return (
        <Center py={6}>
            <Box
                position={'relative'}
                maxW={'330px'}
                minW={'300px'}
                w={'full'}
                m={2}
                bg={useColorModeValue('white', 'gray.800')}
                boxShadow={'2xl'}
                rounded={'md'}
                overflow={'hidden'}>
                {!isAdmin() && !hasApplied && (
                <Checkbox
                    position={'absolute'}
                    top={2}
                    right={2}
                    size={'lg'}
                    colorScheme="green"
                    onChange={toogleCardSelection}
                    isChecked={isSelected}
                />
                )}
                <Stack
                    textAlign={'center'}
                    p={6}
                    color={useColorModeValue('gray.800', 'white')}
                    align={'center'}>
                    <Text
                        fontSize={'sm'}
                        fontWeight={500}
                        bg={useColorModeValue('green.50', 'green.900')}
                        p={2}
                        px={3}
                        color={'green.500'}
                        rounded={'full'}>
                        {order}. termin
                    </Text>
                    <Stack direction={'row'} align={'center'} justify={'center'}>
                        <Text fontSize={'4xl'} fontWeight={800} h={100} m={10}>
                            {exam.name}
                        </Text>
                    </Stack>
                </Stack>

                <Box bg={useColorModeValue('gray.50', 'gray.900')} px={6} py={10}>
                    <List spacing={3}>
                        <ListItem>
                            Datum: {displayDate}
                        </ListItem>
                        <ListItem>
                            Vreme: {displayTime}
                        </ListItem>
                        {!isAdmin() && (
                        <ListItem>
                            Cena: {priceListItem.price} {priceListItem.currency.code}
                        </ListItem>
                        )}
                    </List>
                    {!isAdmin() && hasApplied && (
                        <Stack direction={'row'} justify={'center'} spacing={6} mt={6}>
                            <Button
                                bg={'red'}
                                color={'white'}
                                rounded={'full'}
                                _hover={{
                                    transform: 'translateY(-2px)',
                                    boxShadow: 'lg'
                                }}
                                onClick={handleOdjaviClick}
                            >
                                Odjavi se
                            </Button>
                        </Stack>
                    )}
                    {isAdmin() && (
                    <Stack direction={'row'} justify={'center'} spacing={6} mt={6}>
                        <Button
                            bg={'#084474'}
                            color={'white'}
                            rounded={'full'}
                            _hover={{
                                transform: 'translateY(-2px)',
                                boxShadow: 'lg'
                            }}
                            onClick={() => navigate("/terminKandidati", { state : {id: id}})}
                        >
                            Detalji
                        </Button>
                        <Stack>
                            <UpdateAppointmentDrawer
                                initialValues={{exam, appointmentDate}}
                                appointmentId={id}
                                fetchAppointments={fetchAppointments}
                            />
                        </Stack>
                        <Stack>
                            <Button
                                bg={'red.400'}
                                color={'white'}
                                rounded={'full'}
                                _hover={{
                                    transform: 'translateY(-2px)',
                                    boxShadow: 'lg'
                                }}
                                _focus={{
                                    bg: 'grey.500'
                                }}
                                onClick={onOpen}
                            >
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
                                            Brisanje termina
                                        </AlertDialogHeader>

                                        <AlertDialogBody>
                                            Da li ste sigurni da zelite da izbrisete termin?
                                        </AlertDialogBody>

                                        <AlertDialogFooter>
                                            <Button ref={cancelRef} onClick={onClose}>
                                                Odustani
                                            </Button>
                                            <Button colorScheme='red' onClick={() => {
                                                deleteAppointment(id).then(res =>{
                                                    successNotification('Termin uspesno izbrisan')
                                                    updateOrderAfterDeletion();
                                                    fetchAppointments();
                                                }).catch(err => {
                                                    errorNotification(
                                                        err.code,
                                                        err?.response.data.message
                                                    )
                                                }).finally(() => {
                                                    onClose();
                                                })
                                            }} ml={3}>
                                                Izbrisi
                                            </Button>
                                        </AlertDialogFooter>
                                    </AlertDialogContent>
                                </AlertDialogOverlay>
                            </AlertDialog>
                        </Stack>
                    </Stack>
                    )}
                </Box>
            </Box>
        </Center>
    )
}