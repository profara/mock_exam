import {Button, Flex, Heading, Text, Image} from "@chakra-ui/react";
import {useLocation} from "react-router-dom";

const EmailVerificationPage = () => {
    const location = useLocation();
    const email = location.state?.email;

    return (
        <Flex minH="100vh" direction={{ base: 'column', md: 'row' }} alignItems="center" justifyContent="center" p={5}>
            <Flex flexDir="column" maxW="1200px" alignItems="center" justifyContent="center" p={8} border="1px solid #E2E8F0" borderRadius="8px">
                <Image src="/email-confirmation.jpg" mb={6} boxSize="600px" />

                <Heading fontSize="2xl" mb={4}>Potvrdite svoju email adresu</Heading>
                <Text mb={4}>
                    Uneli ste <strong>{email}</strong> kao adresu za svoj nalog.
                </Text>
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
