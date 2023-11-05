import Label from './Label.jsx'
import S from 'styled-components'
import { deviceBrakepoints } from "./config/device-brakepoints.jsx"
import React from "react";

const Input = ({ type = 'text', id, help, helpText, disabled, width, label, value}) => (
    <Container width={width} >
        <Label label={label} for={id}/>
        <InnerInput type={type} name={id} id={id} aria-describedby={help} disabled={disabled} value={value}  />
        <span hidden id={help}>{helpText}</span>
    </Container>
)

const Container = S.div`
   display: inline-block;
   text-align: left;
   margin-bottom: 7px;
   width: ${props => (props.width ? props.width : '100')}%;  
`
const InnerInput = S.input`
   width: calc(100% - 36px);
   background-color: white;
   border: solid 1px #000;
   font-family: Arial, Helvetica, sans-serif;
   line-height: 1.8em;
   font-size: 14px;
   padding: 2px 8px;
   -webkit-appearance: none;
   -moz-appearance: none;
   appearance: none;
   outline: none;
   border-width: 2.7px;
   
   @media ${deviceBrakepoints.mobile} {
      width: calc(100% - 26px);
   }
`

export default Input;


