import {Wrap, WrapItem, Spinner, Text, Button, Center} from '@chakra-ui/react';
import Simple from "./components/shared/NavBar.jsx";
import {useEffect, useState} from "react";
import {getAppointments, saveApplication} from "./services/client.js";
import Card from "./components/Card.jsx";
import {errorNotification, successNotification} from "./services/notification.js";
import {useNavigate} from "react-router-dom";
import {useAuth} from "./components/context/AuthContext.jsx";
import {useCard} from "./components/context/SelectedCardsContext.jsx";

const App = () => {

    const [appointments, setAppointments] = useState([]);
    const [loading, setLoading] = useState(false);
    const {selectedCards, setSelectedCards} = useCard();
    const [err, setError] = useState("");
    const navigate = useNavigate();
    const {candidate} = useAuth();
    const serbiaDate = getCurrentDateInSerbiaTimeZone();

    let matematikaCount = 0;
    let opstaInformisanostCount = 0;

    function getCurrentDateInSerbiaTimeZone() {
        const now = new Date();

        const serbiaDate = new Intl.DateTimeFormat('en-US', {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            timeZone: 'Europe/Belgrade'
        }).format(now);

        const [month, day, year] = serbiaDate.split('/');
        const isoString = `${year}-${month}-${day}`;

        return isoString;
    }



    const handlePrijaviClick = (candidate) => {
        if(candidate){
            const application = {
                applicationDate: serbiaDate,
                privileged: candidate.attendedPreparation,
                candidate: candidate.id,
                appointmentIds: [...selectedCards]
            }
            saveApplication(application)
                .then(res => {
                    console.log(res)
                    successNotification(
                        "Uspesno sacuvana prijava",
                        ""
                    )
                    navigate("/uplatnica");
                }).catch(err => {
                console.log(err)
                errorNotification(
                    err.code,
                    err?.response.data.violations[0].error
                )
            })
        } else{
            navigate("/profil")
        }

    }
    const toogleCardSelection = (id) => {
        setSelectedCards(prevState => {
            if(prevState.includes(id)){
                return prevState.filter(cardId => cardId !== id);
            } else{
                return [...prevState, id];
            }
        });
    }


    useEffect(() =>{
        setLoading(true);
        getAppointments().then(res => {
            setAppointments(res.data.content);
            console.log(res.data.content)
        }).catch(err => {
            setError(err.response.data.message)
            errorNotification(
                err.code,
                err.response.data.message
            )
        }).finally( () =>{
            setLoading(false)
        })
    }, [])

    if(loading){
        return (
            <Simple>
                <Spinner
                thickness='4px'
                speed='0.65s'
                emptyColor='gray.200'
                color='blue.500'
                size='xl'
                />
            </Simple>
            )
    }

    if(err){
        return (
            <Simple>
                <Text>Doslo je do greske</Text>
            </Simple>
        )
    }

    if(appointments.length <= 0){
        return (
            <Simple>
                <Text>Nema dostupnih termina</Text>
            </Simple>
        )
    }

    return (
        <Simple>
            <Wrap justify={"center"} spacing={"30px"}>
                {appointments.map((appointment, index) => {
                    const examName = appointment.exam.name;
                    const count =
                        examName === 'Matematika'
                            ? ++matematikaCount
                            : examName === 'Opsta informisanost'
                                ? ++opstaInformisanostCount
                                : null;
                    return (
                        <WrapItem key={index}>

                            <Card
                                {...appointment} count={count} toogleCardSelection={() => toogleCardSelection(appointment.id)}
                            />
                        </WrapItem>
                    );
                })}

            </Wrap>
            <Center mt={10}>
                <Button colorScheme="green" size="lg" isDisabled={selectedCards.length === 0} onClick={() => handlePrijaviClick(candidate)}>
                    Prijavi
                </Button>
            </Center>
        </Simple>
    )
}

export default App;