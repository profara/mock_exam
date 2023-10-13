'use client'

import {
    Button,
    Checkbox,
    Flex,
    Text,
    FormControl,
    FormLabel,
    Heading,
    Input,
    Stack,
    Image, Link, Box, Alert, AlertIcon,
} from '@chakra-ui/react'
import {Formik, Form, useField} from "formik";
import * as Yup from 'yup';
import {useAuth} from "../context/AuthContext.jsx";
import {errorNotification} from "../../services/notification.js";
import {useNavigate} from "react-router-dom";
import {useEffect} from "react";

const MyTextInput = ({label, ...props}) => {
    // useField() returns [formik.getFieldProps(), formik.getFieldMeta()]
    // which we can spread on <input>. We can use field meta to show an error
    // message if the field is invalid and it has been touched (i.e. visited)

    const [field, meta] = useField(props);
    return(
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Input className="text-input" {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert className="error" status={"error"} mt={2}>
                    <AlertIcon/>
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};

const LoginForm = () => {
    const {login} = useAuth();
    const navigate = useNavigate();

    return (
        <Formik
            validateOnMount={true}
            validationSchema={
                Yup.object({
                    email: Yup.string()
                        .email("Mora biti validan email")
                        .required("Email je obavezan"),
                    password: Yup.string()
                        .min(8, "Lozinka mora imati bar 8 karaktera")
                        .required("Lozinka je obavezna")
                })
            }
            initialValues={{email: '', password: ''}}
            onSubmit={(values, {setSubmitting}) => {
                setSubmitting(true);
                login(values).then(res =>{
                    navigate("/termini")
                }).catch(err =>{
                    errorNotification(err.code, err.response.data.message);
                }).finally(() => {
                    setSubmitting(false);
                })
            }
            }>

            {({isValid, isSubmitting}) => (
                <Form>
                    <Stack spacing={15}>
                        <MyTextInput
                            label={"Email"}
                            name={"email"}
                            type={"email"}
                            placeholder={"marko@gmail.com"}
                        />
                        <MyTextInput
                            label={"Lozinka"}
                            name={"password"}
                            type={"password"}
                            placeholder={"Unesite lozinku"}
                        />

                        <Button
                            colorScheme='teal'
                            isDisabled={!isValid || isSubmitting}
                            type={"submit"}
                            >
                            Uloguj se
                        </Button>
                    </Stack>
                </Form>
            )}

        </Formik>
    )
}

const Login = () => {
    const {user} = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        if(user){
            navigate("/termini");
        }
    })

    return (
        <Stack minH={'100vh'} direction={{ base: 'column', md: 'row' }}>
            <Flex p={8} flex={1} align={'center'} justify={'center'}>
                <Stack spacing={4} w={'full'} maxW={'md'}>
                    <Heading fontSize={'2xl'} mb={15}>Prijava</Heading>
                    <LoginForm/>
                    <Link color={"blue.500"} href={"/registracija"}>
                        Nemate nalog? Registrujte se
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

export default Login;