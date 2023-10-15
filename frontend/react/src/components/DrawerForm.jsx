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
import {ADD_ICON, CLOSE_ICON} from "./payslip/config/constants.js";
import CreateAppointmentForm from "./appointment/CreateAppointmentForm.jsx";

const AddIcon = () => ADD_ICON;
const CloseIcon = () => CLOSE_ICON;
const DrawerForm = () => {
    const { isOpen, onOpen, onClose } = useDisclosure()

    return <>
        <Button
            leftIcon={<AddIcon/>}
            colorScheme={"teal"}
            onClick={onOpen}
        >
            Dodaj termin
        </Button>
        <Drawer isOpen={isOpen} onClose={onClose} size={"xl"}>
            <DrawerOverlay />
            <DrawerContent>
                <DrawerCloseButton />
                <DrawerHeader>Dodaj novi termin</DrawerHeader>

                <DrawerBody>
                    <CreateAppointmentForm/>
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

export default DrawerForm;
