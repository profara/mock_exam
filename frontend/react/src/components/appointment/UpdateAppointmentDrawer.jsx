import {
    Button,
    Drawer,
    DrawerBody,
    DrawerCloseButton,
    DrawerContent, DrawerFooter,
    DrawerHeader,
    DrawerOverlay,
    useDisclosure
} from "@chakra-ui/react";
import {ADD_ICON, CLOSE_ICON} from "../payslip/config/constants.js";
import UpdateAppointmentForm from "./UpdateAppointmentForm.jsx";

const AddIcon = () => ADD_ICON;
const CloseIcon = () => CLOSE_ICON;
const UpdateAppointmentDrawer = ({ fetchAppointments, initialValues, appointmentId }) => {
    const { isOpen, onOpen, onClose } = useDisclosure()

    return <>

        <Button
            bg={'teal'}
            color={'white'}
            rounded={'full'}
            _hover={{
                transform: 'translateY(-2px)',
                boxShadow: 'lg'
            }}
            onClick={onOpen}
        >
            Izmeni
        </Button>
        <Drawer isOpen={isOpen} onClose={onClose} size={"xl"}>
            <DrawerOverlay />
            <DrawerContent>
                <DrawerCloseButton />
                <DrawerHeader>Izmena termina</DrawerHeader>

                <DrawerBody>
                    <UpdateAppointmentForm
                        fetchAppointments={fetchAppointments}
                        initialValues={initialValues}
                        appointmentId={appointmentId}
                    />
                </DrawerBody>

                <DrawerFooter>
                    <Button
                        leftIcon={<CloseIcon/>}
                        colorScheme={"teal"}
                        onClick={onClose}
                    >
                        Zatvori
                    </Button>
                </DrawerFooter>
            </DrawerContent>
        </Drawer>
    </>
}

export default UpdateAppointmentDrawer;
