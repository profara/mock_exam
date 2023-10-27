import {Button, Flex, Heading, Text, Image} from "@chakra-ui/react";
import {useLocation, useNavigate} from "react-router-dom";
import {useEffect} from "react";

const EmailVerificationPage = () => {
    const location = useLocation();
    const navigate = useNavigate();

    useEffect(() => {
        if(!location.state?.submitted){
            navigate("/");
        }
    }, [location, navigate])


    return (
        <Flex minH="100vh" direction={{ base: 'column', md: 'row' }} alignItems="center" justifyContent="center" p={5}>
            <Flex flexDir="column" maxW="1200px" alignItems="center" justifyContent="center" p={8} border="1px solid #E2E8F0" borderRadius="8px">
                <Image src="/email-confirmation.jpg" mb={6} boxSize="600px" />

                <Heading fontSize="2xl" mb={8}>Potvrdite svoju email adresu</Heading>
                <Button colorScheme="blue" mb={4} onClick={() => window.location.href = 'https://mail.google.com/'}>
                    Potvrdi svoj email
                </Button>
                <Text fontSize="sm">
                    Molimo Vas potvrdite svoju email adresu klikom na dugme iznad.
                </Text>
            </Flex>
        </Flex>
    );
}

export default EmailVerificationPage;
