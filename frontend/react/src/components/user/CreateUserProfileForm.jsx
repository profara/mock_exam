import { Formik, Form, useField } from 'formik';
import * as Yup from 'yup';
import {Alert, AlertIcon, Box, Button, FormLabel, Input, Stack} from "@chakra-ui/react";
import {saveUserProfile} from "../../services/client.js";
import {successNotification, errorNotification} from "../../services/notification.js";

const MyTextInput = ({ label, ...props }) => {
    // useField() returns [formik.getFieldProps(), formik.getFieldMeta()]
    // which we can spread on <input>. We can use field meta to show an error
    // message if the field is invalid and it has been touched (i.e. visited)
    const [field, meta] = useField(props);
    return (
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

const CreateUserProfileForm = ({onSuccess}) => {
    return (
        <>
            <Formik
                validateOnMount={true}
                initialValues={{
                    email: '',
                    password: ''
                }}
                validationSchema={Yup.object({
                    email: Yup.string()
                        .email("Mora biti validan email")
                        .required("Email je obavezan"),
                    password: Yup.string()
                        .min(8, "Lozinka mora imati bar 8 karaktera")
                        .required("Lozinka je obavezna")
                })}
                onSubmit={(user, { setSubmitting }) => {
                    setSubmitting(true)
                    saveUserProfile(user)
                        .then(res => {
                            console.log(res)
                            successNotification(
                                "Uspesna registracija",
                                ""
                            )
                            onSuccess(res.headers["authorization"]);
                        }).catch(err => {
                        console.log(err)
                        errorNotification(
                            err.code,
                            err?.response.data.violations[0].error
                        )
                        }).finally(() => {
                            setSubmitting(false);
                        })
                }}
            >
                {({isValid, isSubmitting}) => (
                    <Form>
                        <Stack spacing={"24px"}>
                            <MyTextInput
                                label="Email"
                                name="email"
                                type="email"
                                placeholder="marko@gmail.com"
                            />

                            <MyTextInput
                                label={"Lozinka"}
                                name={"password"}
                                type={"password"}
                                placeholder={"Unesite lozinku"}
                            />

                            <Button isDisabled={!isValid || isSubmitting} type="submit" colorScheme='teal'>
                                Potvrdi
                            </Button>
                        </Stack>
                    </Form>
                )}
            </Formik>
        </>
    );
};

export default CreateUserProfileForm;