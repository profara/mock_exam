import S from 'styled-components'

import Label from './Label'

const Textarea = ({ label, id, help, helpText, value}) => (
    <Container>
        <Label label={label} for={id}/>
        <StyledTextarea  id={id}>{value}</StyledTextarea>
        <span hidden id={help}>{helpText}</span>
    </Container>
)

const Container = S.div`
   margin-bottom: 2px;
   text-align: left;
`

const StyledTextarea = S.div`
    background-color: white;
    border: solid 1px #000;
    font-family: Arial, Helvetica, sans-serif;
    font-size: 15px;
    height: 60px;
    width: 344px;
    resize: none;
    -moz-appearance: none;
    appearance: none;
    outline: none;
    
`
export default Textarea
