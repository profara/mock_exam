import {
    Box, Button,
    Center,
    Select, Spinner,
} from '@chakra-ui/react';
import {useEffect, useState} from "react";
import Simple from "../shared/NavBar.jsx";
import {getCurrencies} from "../../services/client.js";
import {errorNotification} from "../../services/notification.js";


const Currency = () => {
    const [currencies, setCurrencies] = useState([]);
    const [loading, setLoading] = useState(false);
    const [selectedCurrency, setSelectedCurrency] = useState(null);

    useEffect(() => {
        setLoading(true);
        getCurrencies().then(res => {
            setCurrencies(res.data.content);
            console.log(res.data.content)
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
        setSelectedCurrency(event.target.value);
    };

    const handleConfirmClick = () => {
        console.log("Potvrdjena valuta");
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
                    <Box display="flex" alignItems="center" justifyContent="center" marginBottom="1.5rem">Izberite željenu valutu:</Box>
                    <Select onChange={handleCurrencyChange} placeholder="Izaberite valutu">
                        {(currencies.map(currency =>
                            <option key={currency.id} value={currency.code}>
                                {currency.code}
                            </option>
                        ))}
                    </Select>
                    <Button
                        mt={4}
                        colorScheme="seal"
                        onClick={handleConfirmClick}
                        isDisabled={!selectedCurrency}
                    >
                        Potvrdi
                    </Button>
                </Box>
            </Center>
        </Simple>
    );
}

export default Currency;