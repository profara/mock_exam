import {Button, Flex, Heading, Text, Image} from "@chakra-ui/react";
import {useNavigate, useLocation} from "react-router-dom";
import {useEffect} from "react";
import {confirmUser} from "../../services/client.js";
import {useAuth} from "../context/AuthContext.jsx";

const EmailConfirmed = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);
    const token = queryParams.get('token');
    const {loadUser} = useAuth();

    useEffect(() => {
        if(token){
            console.log(token)
            localStorage.setItem("access_token", token);
               confirmUser(token).then(res => {
                   console.log(res)
               }).catch(err => {
                   console.error(err)
               });
        }
    }, [token])

    return (
        <Flex minH="100vh" direction={{ base: 'column', md: 'row' }} alignItems="center" justifyContent="center" p={5}>
            <Flex flexDir="column" maxW="1200px" alignItems="center" justifyContent="center" p={8} border="1px solid #E2E8F0" borderRadius="8px">
                <Image src="/email-confirmation.jpg" mb={6} boxSize="600px" />

                <Heading fontSize="2xl" mb={4}>Email uspešno potvrdjen</Heading>
                <Button colorScheme="blue" mb={4} onClick={() => navigate("/")}>
                    Uloguj se
                </Button>
                <Text fontSize="sm">
                    Uspešno ste potvrdili Vašu email adresu, možete se prijaviti klikom na dugme iznad.
                </Text>
            </Flex>
        </Flex>
    );
}

export default EmailConfirmed;
