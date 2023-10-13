'use client'

import {
    Box,
    Center,
    Text,
    Stack,
    List,
    ListItem,
    useColorModeValue, Checkbox,
} from '@chakra-ui/react'


export default function Card({id,exam, appointmentDate, count, toogleCardSelection, isSelected, priceListItem}) {

    return (
        <Center py={6}>
            <Box
                position={'relative'}
                maxW={'330px'}
                minW={'300px'}
                w={'full'}
                m={2}
                bg={useColorModeValue('white', 'gray.800')}
                boxShadow={'2xl'}
                rounded={'md'}
                overflow={'hidden'}>
                <Checkbox
                    position={'absolute'}
                    top={2}
                    right={2}
                    size={'lg'}
                    colorScheme="green"
                    onChange={toogleCardSelection}
                    isChecked={isSelected}
                />
                <Stack
                    textAlign={'center'}
                    p={6}
                    color={useColorModeValue('gray.800', 'white')}
                    align={'center'}>
                    <Text
                        fontSize={'sm'}
                        fontWeight={500}
                        bg={useColorModeValue('green.50', 'green.900')}
                        p={2}
                        px={3}
                        color={'green.500'}
                        rounded={'full'}>
                        {count}. termin
                    </Text>
                    <Stack direction={'row'} align={'center'} justify={'center'}>
                        <Text fontSize={'4xl'} fontWeight={800} h={100} m={10}>
                            {exam.name}
                        </Text>
                    </Stack>
                </Stack>

                <Box bg={useColorModeValue('gray.50', 'gray.900')} px={6} py={10}>
                    <List spacing={3}>
                        <ListItem>
                            {(() => {
                                const date = new Date(appointmentDate);
                                const day = date.getDate();
                                const month = date.getMonth() + 1;
                                const year = date.getFullYear();
                                return `Datum: ${day}.${month}.${year}`;
                            })()}
                        </ListItem>

                        <ListItem>
                            Cena: {priceListItem.price} {priceListItem.currency.code}
                        </ListItem>
                    </List>
                </Box>
            </Box>
        </Center>
    )
}