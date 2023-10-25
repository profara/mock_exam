import {Flex, Heading, Image, Link, Stack} from "@chakra-ui/react";
import CreateUserProfileForm from "../user/CreateUserProfileForm.jsx";

const Signup = () => {

    return (
        <Stack minH={'100vh'} direction={{ base: 'column', md: 'row' }}>
            <Flex p={8} flex={1} align={'center'} justify={'center'}>
                <Stack spacing={4} w={'full'} maxW={'md'}>
                    <Heading fontSize={'2xl'} mb={15}>Registracija</Heading>
                    <CreateUserProfileForm />
                    <Link color={"blue.500"} href={"/"}>
                        Imate nalog? Ulogujte se
                    </Link>
                </Stack>
            </Flex>
            <Flex flex={1}>
                <Image
                    alt={'Login Image'}
                    objectFit={'cover'}
                    src={
                        "/logo.jpg"
                    }
                />
            </Flex>
        </Stack>
    )
}

export default Signup;