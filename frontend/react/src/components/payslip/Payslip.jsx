import styled from 'styled-components'
import Input from "./Input.jsx";
import Textarea from "./Textarea.jsx";
import { deviceBrakepoints } from "./config/device-brakepoints.jsx"
import {useAuth} from "../context/AuthContext.jsx";




function Payslip() {

    const {candidate} = useAuth();
    const printPayslip = () => {
        window.print()
    }

    return (
        <StyledWrapper><Container>
            <BankSlipTitle>Nalog Za Uplatu</BankSlipTitle>
            <LeftSide >
                <Textarea label='Platilac'
                          id='payer'
                          help='payerHelp'
                          helpText='U ovo polje upišite podatke osobe koja je Platilac.'
                          readOnly
                          disabled={true}
                          value={`${candidate.name} ${candidate.surname}`}
                />
                <Textarea
                    label='Svrha uplate'
                    id='paymentDescription'
                    help='paymentDescriptionHelp'
                    helpText='U ovo polje upišite svrhu uplate.'
                    value={"Probni prijemni ispit"}
                    disabled={true}
                />
                <Textarea label='Primalac'
                          id='receiverDescription'
                          help='receiverDescriptionHelp'
                          helpText='U ovo polje upišite podatke osobe koja je Primalac.'
                          value={"Fakultet organizacionih nauka, Jove Ilića 154, 11000 Beograd"}
                          disabled={true}
                />
            </LeftSide>
            <RightSide>
                <Input
                    type='number'
                    width={23}
                    label='Sifra Pacanja'
                    id='payCode'
                    help='payCodeHelp'
                    helpText='Upisite sifru placanja'
                    value={189}
                    disabled={true}
                    readOnly

                />
                <Input
                    width={23}
                    disabled={true}
                    label='Valuta'
                    id='valuta'
                    help='valutaHelp'
                    helpText='Ovo polje je onemogućeno jer valuta mora biti RSD.'
                    value={"RSD"}
                    readOnly
                />
                <Input
                    type='number'
                    width={54}
                    label='Iznos'
                    id='totalAmount'
                    help='totalAmountHelp'
                    helpText='Ovde upišite brojevima ukupan iznos koji zelite da uplatite.'
                    value={10000}
                    readOnly
                    disabled={true}
                />
                <Input
                    type='text'
                    width={100}
                    label='Racun primaoca'
                    id='paymentNumber'
                    help='paymentNumberHelp'
                    helpText='Ovde upišite brojevima poziv na broj na koji zelite da uplatite.'
                    value={"840-0000001344666-69"}
                    readOnly
                    disabled={true}
                />

                <Input
                    type='number'
                    width={25}
                    label='Model'
                    id='modelCode'
                    help='modelCodeHelp'
                    helpText='Ovde upisite brojevima model'
                    value=""
                    disabled={true}
                    readOnly
                />
                <Input
                    type='text'
                    width={75}
                    label='Poziv na broj'
                    id='paymentNumber'
                    help='paymentNumberHelp'
                    helpText='Ovde upišite brojevima poziv na broj za ovu uplatnicu.'
                    value={"742121-500"}
                    disabled={true}
                    readOnly
                />

            </RightSide>
        </Container>
                <Button onClick={printPayslip}>
                    Odštampaj uplatnicu
                </Button>
        </StyledWrapper>
    )
}

const Container = styled.div`
    width: 100%;
    max-width: 800px;
    position: relative;
    line-height: 1.2em;
    font-family: Arial, Helvetica, sans-serif;
    font-size: 3mm;
    border: solid 1px var(--color-primary);
    padding: 6mm;
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
    font-size: 4.95mm;
    font-weight: 600;
    text-transform: uppercase;
    text-align: right;
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

export default Payslip

const Button = styled.button`
    @media print {
        display: none;
    }
`

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
    height: 100vh;
    display: flex;
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