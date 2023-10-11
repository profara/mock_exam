import {
    Box, Button,
    Center, Flex,
    Select, Spinner,
} from '@chakra-ui/react';
import {useEffect, useState} from "react";
import Simple from "../shared/NavBar.jsx";
import {getCurrencies, savePayment} from "../../services/client.js";
import {errorNotification, successNotification} from "../../services/notification.js";
import {CREDITOR_ACCOUNT, PAYMENT_PURPOSE, REFERENCE_NUMBER} from "../payslip/config/constants.js";
import {useApplication} from "../context/ApplicationContext.jsx";
import {useNavigate} from "react-router-dom";
import {useCurrency} from "../context/CurrencyContext.jsx";
import {usePayment} from "../context/PaymentContext.jsx";


const Currency = () => {
    const [currencies, setCurrencies] = useState([]);
    const [loading, setLoading] = useState(false);
    const [selectedCurrency, setSelectedCurrency] = useState("");
    const {application} = useApplication();
    const navigate = useNavigate();
    const {setCurrency} = useCurrency();
    const {setPayment} = usePayment();

    const updateSelectedCurrency = (currencyId) => {
        console.log("Drugo")
        const selectedCurrencyObject = currencies.find(currency => currency.id === parseInt(currencyId));

        console.log(selectedCurrencyObject);

        setCurrency(selectedCurrencyObject);

        setSelectedCurrency(Number(currencyId));
    };

    useEffect(() => {
        console.log("Prvo")
        const savedCurrency = parseInt(localStorage.getItem('selectedCurrency'), 10);



        setLoading(true);
        getCurrencies().then(res => {
            setCurrencies(res.data.content);
            console.log(res.data.content)

            if(savedCurrency){
                setSelectedCurrency(savedCurrency);
                const savedCurrencyObject = res.data.content.find(currency => currency.id === parseInt(savedCurrency));
                if(savedCurrencyObject){
                    setCurrency(savedCurrencyObject);
                }
            }

        }).catch(err => {
            errorNotification(
                err.code,
                err.response.data.message
            )
        }).finally( () =>{
            setLoading(false)
        })
    }, [])

    const handleCurrencyChange = (event) => {
        console.log("trece")
        const selectedCurrencyId = event.target.value;
        updateSelectedCurrency(selectedCurrencyId);


        // const selectedCurrencyObject = currencies.find(currency => currency.id === parseInt(selectedCurrencyId));
        //
        // console.log(selectedCurrencyObject)
        // setCurrency(selectedCurrencyObject);

        localStorage.setItem('selectedCurrency', selectedCurrencyId);
    };

    const handleConfirmClick = () => {
        const payment = {
            referenceNumber: REFERENCE_NUMBER,
            creditorAccount: CREDITOR_ACCOUNT,
            paymentPurpose: PAYMENT_PURPOSE,
            applicationId: application.id,
            currencyId: selectedCurrency
        }
        savePayment(payment)
            .then(res => {
                console.log(res)
                successNotification(
                    "Uspesno kreirana uplatnica"
                )
                setPayment(res.data);
                navigate("/uplatnica");
            }).catch(err => {
            console.log(err)
            errorNotification(
                err.code,
                err?.response.data.violations[0].error
            )
        })
    }

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

    return (
        <Simple>
            <Center h="70vh">
                <Box width="300px">
                    <Flex flexDirection="column" alignItems="center">
                        <Box marginBottom="1.5rem">Izaberite
                            željenu valutu:</Box>
                        <Select value={selectedCurrency} onChange={handleCurrencyChange} placeholder="Izaberite valutu">
                            {(currencies.map(currency =>
                                <option key={currency.id} value={currency.id}>
                                    {currency.code}
                                </option>
                            ))}
                        </Select>
                        <Button
                            mt={8}
                            colorScheme="teal"
                            onClick={handleConfirmClick}
                            isDisabled={!selectedCurrency}
                        >
                            Potvrdi
                        </Button>
                    </Flex>
                </Box>
            </Center>
        </Simple>
    );
}

export default Currency;