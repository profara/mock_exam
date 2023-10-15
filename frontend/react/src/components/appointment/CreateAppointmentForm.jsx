import {Formik, Form, useField, useFormikContext} from 'formik';
import * as Yup from 'yup';
import {Alert, AlertIcon, Box, Button, FormLabel, Select, Stack} from "@chakra-ui/react";
import {useEffect, useState} from "react";
import {getExams, saveAppointment} from "../../services/client.js";
import {errorNotification, successNotification} from "../../services/notification.js";
import ReactDatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';


const MySelect = ({label, ...props}) => {
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

const MyDateInput = ({label, ...props}) => {
    const [field, meta] = useField(props);
    const {setFieldValue} = useFormikContext();

    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <ReactDatePicker
                selected={field.value}
                onChange={date => setFieldValue(field.name, date)}
                dateFormat="MMMM d, yyyy"
                isClearable
                {...props}
            />
            {meta.touched && meta.error ? (
                <Alert className="error" status={"error"} mt={2}>
                    <AlertIcon/>
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};
const CreateAppointmentForm = ({fetchAppointments}) => {
    const [exams, setExams] = useState([]);

    useEffect(() => {
        getExams()
            .then(res => {
                console.log(res)
                setExams(res.data.content);
            }).catch(err => {
            console.error("Greska prilikom fecovanja ispita", err);
        })
    }, [])


    return (
        <>
            <Formik
                validateOnMount={true}
                initialValues={{
                    examId: '',
                    appointmentDate: new Date()
                }}
                validationSchema={Yup.object({
                    examId: Yup.number()
                        .oneOf(exams.map(exam => exam.id), "Greska")
                        .required('Morate izabrati ispit'),
                    appointmentDate: Yup.date()
                        .required('Morate izabrati datum')
                })}
                onSubmit={(appointment, {setSubmitting}) => {
                    setSubmitting(true);
                    saveAppointment(appointment)
                        .then(async res => {
                            console.log(res)
                            successNotification(
                                "Uspesno sacuvan termin",
                                ""
                            )
                            fetchAppointments();
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
                            <MySelect label="Ispit" name="examId">
                                <option value="">Izaberite ispit</option>
                                {exams.map((exam) => (
                                    <option key={exam.id} value={exam.id}>
                                        {exam.name}
                                    </option>
                                ))}
                            </MySelect>

                            <MyDateInput
                                label="Datum termina"
                                name="appointmentDate"
                            />


                            <Button isDisabled={!isValid || isSubmitting} type="submit" colorScheme='teal'>
                                Potvrdi
                            </Button>
                        </Stack>
                    </Form>

                )}
            </Formik>
        </>
    )
        ;
};

export default CreateAppointmentForm;