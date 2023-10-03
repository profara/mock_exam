import { Formik, Form, useField } from 'formik';
import * as Yup from 'yup';
import {
    Alert,
    AlertIcon,
    Box,
    Button,
    Checkbox,
    Container,
    FormLabel,
    Input,
    Select,
    Stack,
    Flex,
    Image,
    Spinner
} from "@chakra-ui/react";
import {getSchools, updateCandidate} from "../../services/client.js";
import {successNotification, errorNotification} from "../../services/notification.js";
import {useAuth} from "../context/AuthContext.jsx";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {useCard} from "../context/SelectedCardsContext.jsx";



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

const MyCheckbox = ({ children, ...props }) => {
    // React treats radios and checkbox inputs differently from other input types: select and textarea.
    // Formik does this too! When you specify `type` to useField(), it will
    // return the correct bag of props for you -- a `checked` prop will be included
    // in `field` alongside `name`, `value`, `onChange`, and `onBlur`
    const [field, meta] = useField({ ...props, type: 'checkbox' });
    return (
        <Box>
            <Checkbox isChecked={field.value} {...field} {...props}>
                {children}
            </Checkbox>
            {meta.touched && meta.error ? (
                <Alert status="error" mt={2}>
                    <AlertIcon/>
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};

const MySelect = ({ label, ...props }) => {
    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Select {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert className="error" status={"error"} mt={2}>
                    <AlertIcon/>
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};

const UpdateCandidateProfileForm = () => {
    const {user, candidate} = useAuth();
    const [schools, setSchools] = useState([]);
    const navigate = useNavigate();
    const {selectedCards} = useCard();

    const handleConfirmClick = () => {
        if(selectedCards.length === 0){
            navigate("/termini")
        } else{
            navigate("/uplatnica")
        }
    }


    useEffect(() => {
        getSchools()
            .then(res => {
                console.log(res);
                setSchools(res.data.content);
            }).catch(err => {
            console.error("Greska prilikom fecovanja skola", err);
        })
    }, [])

    if(candidate){
        console.log(candidate);
    }


    if(!user){
        return <Spinner
            thickness='4px'
            speed='0.65s'
            emptyColor='gray.200'
            color='blue.500'
            size='xl'
        />
    }

    return (
        <Flex
            direction="column"
            align="center"
            justify="center"
            style={{ minHeight: '100vh'}}
        >
            <Image
                src="/fon-logo-beli.png"
                alt="Logo"
            />
            <Container>
                <Formik
                    validateOnMount={true}
                    initialValues={{
                        name: candidate.name,
                        surname: candidate.surname,
                        attendedPreparation: candidate.attendedPreparation,
                        userProfile: user,
                        school: candidate.school

                    }}
                    validationSchema={Yup.object({
                        name: Yup.string()
                            .required("Ime je obavezno"),
                        surname: Yup.string()
                            .required("Prezime je obavezno"),
                        attendedPreparation: Yup.boolean()
                            .required("Morate oznaciti da li ste isli na pripremu"),
                        school: Yup.number()
                            .oneOf(schools.map(school => school.code), "Greska")
                            .required("Morate izabrati skolu")
                    })}
                    onSubmit={(candidateData, { setSubmitting }) => {
                        setSubmitting(true)
                        updateCandidate(candidate.id, candidateData)
                            .then(res => {
                                console.log(res)
                                successNotification(
                                    "Uspesna potvrdjen kandidat",
                                    ""
                                )
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
                                    label="Ime"
                                    name="name"
                                    type="text"
                                    placeholder="Marko"
                                />

                                <MyTextInput
                                    label={"Prezime"}
                                    name={"surname"}
                                    type={"text"}
                                    placeholder={"Markovic"}
                                />

                                <MyCheckbox label="Isao sam na pripremnu nastavu na fakultetu" name="attendedPreparation">
                                    Isao sam na pripremnu nastavu na fakultetu
                                </MyCheckbox>

                                <MySelect label="Skola" name="school">
                                    {schools.map((school) => (
                                        <option key={school.code} value={school.code}>
                                            {school.name}
                                        </option>
                                    ))}
                                </MySelect>

                                <Button type="submit" colorScheme='teal' onClick={handleConfirmClick}>
                                    Potvrdi
                                </Button>
                            </Stack>
                        </Form>
                    )}
                </Formik>
            </Container>
        </Flex>
    );
};

export default UpdateCandidateProfileForm;