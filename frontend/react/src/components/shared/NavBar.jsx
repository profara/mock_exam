'use client'

import {
    Box,
    Flex,
    Avatar,
    HStack,
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


const NavLink = (props) => {
    const { children, href } = props
    const navigate = useNavigate();

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
            onClick={() => href && navigate(href)}
            cursor="pointer"
        >
            {children}
        </Box>
    )
}

export default function Simple(props) {
    const { isOpen, onOpen, onClose } = useDisclosure()
    const {logOut, candidate, isAdmin} = useAuth();
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
            <Box bg={useColorModeValue('#084474', '#084474')} px={4} sx={{ "@media print": {display: "none"}}}>
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
                                src='/logo-fon-light-text.png'
                                alt='fon'
                            />
                        </Box>
                        {isAdmin() && (
                        <HStack as={'nav'} spacing={4} display={{ base: 'none', md: 'flex' }}>
                            <NavLink href="/kandidati">Kandidati</NavLink>
                        </HStack>
                        )}
                        <HStack as={'nav'} spacing={4} display={{ base: 'none', md: 'flex' }}>
                            <NavLink href="/termini">Termini</NavLink>
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
                                        '/avatar.png'
                                    }
                                />
                            </MenuButton>
                            <MenuList>
                                {!isAdmin() && (
                                    <>
                                <MenuItem onClick={() => handleProfileClick(candidate)}>Profil</MenuItem>
                                <MenuDivider />
                                    </>
                                    )}
                                <MenuItem onClick={logOut}>
                                    Odjavi se
                                </MenuItem>
                            </MenuList>
                        </Menu>
                    </Flex>
                </Flex>


            </Box>

            <Box p={4}>{props.children}</Box>
        </>
    )

}


