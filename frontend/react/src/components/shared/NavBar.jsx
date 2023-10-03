'use client'

import {
    Box,
    Flex,
    Avatar,
    HStack,
    Text,
    IconButton,
    Button,
    Menu,
    MenuButton,
    MenuList,
    MenuItem,
    MenuDivider,
    useDisclosure,
    useColorModeValue,
    Stack,
    Image
} from '@chakra-ui/react'
import { HamburgerIcon, CloseIcon } from '@chakra-ui/icons'
import {useAuth} from "../context/AuthContext.jsx";
import {useNavigate} from "react-router-dom";


const Links = ['Dashboard', 'Projects', 'Team']

const NavLink = (props) => {
    const { children } = props

    return (
        <Box
            as="a"
            px={2}
            py={1}
            rounded={'md'}
            color={useColorModeValue('#ffffff', '#ffffff')}
            _hover={{
                textDecoration: 'none',
                bg: useColorModeValue('gray.200', 'gray.700'),
            }}
            href={'#'}>
            {children}
        </Box>
    )
}

export default function Simple(props) {
    const { isOpen, onOpen, onClose } = useDisclosure()
    const {logOut, candidate} = useAuth();
    const navigate = useNavigate();
    const handleProfileClick = (candidate) => {
        if(candidate){
            navigate("/updateProfil")
        } else{
            navigate("/profil")
        }
    }
    return (
        <>
            <Box bg={useColorModeValue('#084474', '#084474')} px={4}>
                <Flex h={16} alignItems={'center'} justifyContent={'space-between'}>
                    <IconButton
                        size={'md'}
                        icon={isOpen ? <CloseIcon /> : <HamburgerIcon />}
                        aria-label={'Open Menu'}
                        display={{ md: 'none' }}
                        onClick={isOpen ? onClose : onOpen}
                    />
                    <HStack spacing={8} alignItems={'center'}>
                        <Box>
                            <Image
                                boxSize='60px'
                                objectFit='cover'
                                src='http://pripremna-nastava.fon.bg.ac.rs/images/logo-fon-light-text.png'
                                alt='fon'
                            />
                        </Box>
                        <HStack as={'nav'} spacing={4} display={{ base: 'none', md: 'flex' }}>
                            {Links.map((link) => (
                                <NavLink key={link}>{link}</NavLink>
                            ))}
                        </HStack>
                    </HStack>
                    <Flex alignItems={'center'}>
                        <Menu>
                            <MenuButton
                                as={Button}
                                rounded={'full'}
                                variant={'link'}
                                cursor={'pointer'}
                                minW={0}>
                                <Avatar
                                    size={'sm'}
                                    src={
                                        'https://cdn-icons-png.flaticon.com/128/1177/1177568.png'
                                    }
                                />
                            </MenuButton>
                            <MenuList>
                                <MenuItem onClick={() => handleProfileClick(candidate)}>Profil</MenuItem>
                                <MenuDivider />
                                <MenuItem onClick={logOut}>
                                    Odjavi se
                                </MenuItem>
                            </MenuList>
                        </Menu>
                    </Flex>
                </Flex>

                {isOpen ? (
                    <Box pb={4} display={{ md: 'none' }}>
                        <Stack as={'nav'} spacing={4}>
                            {Links.map((link) => (
                                <NavLink key={link}>{link}</NavLink>
                            ))}
                        </Stack>
                    </Box>
                ) : null}
            </Box>

            <Box p={4}>{props.children}</Box>
        </>
    )

}


