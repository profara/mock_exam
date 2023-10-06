import {
    Box,
    Center,
    Select,
} from '@chakra-ui/react';


function Currency() {
    const handleCurrencyChange = (event) => {
        const selectedCurrency = event.target.value;
        console.log("Selected currency:", selectedCurrency);
        // You can perform any other operations based on the selected currency here
    };

    return (
            <Center h="70vh">
                <Box width="300px">
                    <Box marginBottom="1rem">Please select currency:</Box>
                    <Select onChange={handleCurrencyChange} placeholder="Select option">
                        <option value="USD">USD</option>
                        <option value="EUR">EUR</option>
                        <option value="RSD">RSD</option>
                        {/* Add more currencies as needed */}
                    </Select>
                </Box>
            </Center>

    );
}

export default Currency;