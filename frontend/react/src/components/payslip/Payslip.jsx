import styled from 'styled-components'
import Input from "./Input.jsx";
import Textarea from "./Textarea.jsx";
import {deviceBrakepoints} from "./config/device-brakepoints.jsx"
import {useAuth} from "../context/AuthContext.jsx";
import {
    CREDITOR_ACCOUNT, DEFAULT_CURRENCY_CODE,
    PAY_CODE,
    PAYMENT_PURPOSE,
    RECEIVER_DESCRIPTION,
    REFERENCE_NUMBER
} from "./config/constants.js";
import Simple from "../shared/NavBar.jsx";
import {Button, Spinner, Text, Flex} from "@chakra-ui/react";
import {useEffect, useState} from "react";
import {useLocation} from "react-router-dom";
import {capturePayslipAndSend} from "../../services/client.js";
import {errorNotification, successNotification} from "../../services/notification.js";

function Payslip() {

    const {candidate, loadingAuth} = useAuth();
    const location = useLocation();
    const payment = location.state?.payment;
    const [showQuestion, setShowQuestion] = useState(true);

    const handleDaClick = () => {
        capturePayslipAndSend(candidate.userProfile.email)
            .then(res => {
                successNotification(
                    "Poslato",
                    "Uplatnica uspešno poslata na email"
                )
                setShowQuestion(false);
            }).catch(err => {
                errorNotification("Greška",
                    "Greška prilikom slanja uplatnice")
            console.error(err)
        })
    }

    const handleNeClick = () => {
        setShowQuestion(false);
    }

    useEffect(() => {
        window.scrollTo(0, 0);
    }, []);


    if (loadingAuth) {
        return <Spinner
            thickness='4px'
            speed='0.65s'
            emptyColor='gray.200'
            color='blue.500'
            size='xl'>

        </Spinner>
    }

    return (
        <Simple>
            <StyledWrapper>
                <Container id="payslip">
                    <BankSlipTitle>Nalog Za Uplatu</BankSlipTitle>
                    <LeftSide>
                        <Textarea label='Platilac'
                                  id='payer'
                                  value={`${candidate.name} ${candidate.surname}, ${candidate.address}, ${candidate.city.zipCode} ${candidate.city.name}`}
                        />
                        <Textarea
                            label='Svrha uplate'
                            id='paymentPurpose'
                            value={PAYMENT_PURPOSE}
                        />
                        <Textarea label='Primalac'
                                  id='receiverDescription'
                                  value={RECEIVER_DESCRIPTION}

                        />
                    </LeftSide>
                    <RightSide>
                        <Input
                            type='number'
                            width={23}
                            label='Sifra Placanja'
                            id='payCode'
                            readOnly
                            disabled={true}
                            value={PAY_CODE}
                        />
                        <Input
                            width={23}
                            disabled={true}
                            label='Valuta'
                            id='valuta'
                            value={DEFAULT_CURRENCY_CODE}
                            readOnly
                        />
                        <Input
                            type='number'
                            width={54}
                            label='Iznos'
                            id='totalAmount'
                            value={payment ? payment.amount : ""}
                            readOnly
                            disabled={true}
                        />
                        <Input
                            type='text'
                            width={100}
                            label='Racun primaoca'
                            id='creditorAccount'
                            value={CREDITOR_ACCOUNT}
                            readOnly
                            disabled={true}
                        />

                        <Input
                            type='number'
                            width={25}
                            label='Model'
                            id='modelCode'
                            value=""
                            disabled={true}
                            readOnly
                        />
                        <Input
                            type='text'
                            width={75}
                            label='Poziv na broj'
                            id='referenceNumber'
                            value={`${REFERENCE_NUMBER}-${candidate.id}`}
                            disabled={true}
                            readOnly
                        />

                    </RightSide>
                </Container>
                {showQuestion && (
                <Flex align="center" mt="4" justify="center">
                    <Text fontSize="lg" mr="4">Da li želite da Vam se uplatnica pošalje na email?</Text>
                    <Button
                        colorScheme="blue"
                        rounded={"full"}
                        color={'white'}
                        _hover={{
                            transform: 'translateY(-2px)',
                            boxShadow: 'lg'
                        }}
                        onClick={handleDaClick}
                    >
                        Da
                    </Button>
                    <Button
                        bg={'red'}
                        color={'white'}
                        rounded={"full"}
                        _hover={{
                            transform: 'translateY(-2px)',
                            boxShadow: 'lg'
                        }}
                        onClick={handleNeClick}
                    >
                        Ne
                    </Button>
                </Flex>
                )}

            </StyledWrapper>

        </Simple>
    )
}

const Container = styled.div`
  width: 100%;
  max-width: 800px;
  position: relative;
  line-height: 1.2em;
  font-family: Arial, Helvetica, sans-serif;
  font-size: 11.5px;
  border: solid 1px var(--color-primary);
  padding: 23px;
  margin-bottom: 1em;
  @media ${deviceBrakepoints.desktop} {
    &::before {
      border-right: solid 1px var(--color-primary);
      content: '';
      display: block;
      height: 70%;
      left: 47.7%;
      position: absolute;
      top: 10%;
    }
  }
`

const BankSlipTitle = styled.div`
  color: var(--color-primary);
  font-size: 19px;
  font-weight: 600;
  text-transform: uppercase;
  text-align: right;
  margin-bottom: 4px;
`

const LeftSide = styled.div`
  display: inline-block;
  vertical-align: top;
  width: 50%;
  @media ${deviceBrakepoints.mobile} {
    display: flex;
    flex-direction: column;
    width: 100%;
  }
`

const RightSide = styled.div`
  display: inline-block;
  vertical-align: top;
  width: 50%;
  @media ${deviceBrakepoints.mobile} {
    display: flex;
    flex-direction: column;
    width: 100%;
  }
`

export default Payslip;



const StyledWrapper = styled.div`
  width: 100vw;
  height: 100vh;
  font-family: Inter, Avenir, Helvetica, Arial, sans-serif;
  font-size: 16px;
  line-height: 24px;
  font-weight: 400;
  text-align: center;
  color: rgba(255, 255, 255, 0.87);
  background-color: #242424;
  font-synthesis: none;
  text-rendering: optimizeLegibility;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  -webkit-text-size-adjust: 100%;
  --color-primary: white;
  --color-secondary: black;

  max-width: 100vw;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin: 0 auto;


  button {
    padding: 0.5rem;

    margin-left: 0.5rem;

  }

  @media (prefers-color-scheme: light) {

    color: #213547;
    background-color: #ffffff;
    --color-primary: black;
    --color-secondary: white;

  }

`;
