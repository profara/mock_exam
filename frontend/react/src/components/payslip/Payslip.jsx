import styled from 'styled-components'

import Input from "./Input.jsx";
import Textarea from "./Textarea.jsx";
import { deviceBrakepoints } from "./config/device-brakepoints.jsx"
import { useReducer } from 'react'
import { useLocation } from 'react-router-dom'
import {Button} from "@chakra-ui/react";


//TODO: change value param to string or vice versa


const initialState = {
    payer: '',
    paymentDescription: '',
    receiver: '',
    payCode: '',
    currencyCode: 'RSD',
    totalAmount: '',
    bankNumber: '123',
    accountNumber: '0000000002341',
    controlNumber: '99',
    accountReceivable: '',
    modelCode: '',
    paymentNumber: '',
    currentTemplate: {},
    modalIsOpen: false,
    saveCurrentTemplateModalContent: false,
    allTemplatesModalIsContent: false,
}

const ACTIONS = {
    PAYER_CHANGED: 'payer-change',
    PAYMENT_DESCRIPTION: 'payment-description-change',
    RECEIVER_CHANGED: 'receiver-change',
    PAYCODE_CHANGED: 'pay-code-change',
    CURRENCY_CHANGED: 'currency-change',
    TOTAL_AMOUNT: 'total-amount-change',
    BANK_NUMBER_CHANGE: 'bank-number-change',
    ACCOUNT_NUMBER_CHANGE: 'account-number-change',
    CONTROL_NUMBER_CHANGE: 'control-number-change',
    MODEL_CODE: 'model-code-change',
    PAYMENT_NUMBER: 'payment-number-change',
    RESET_VALUES: 'reset-values',
    USE_SELECTED_TEMPLATE: 'use-selected-template',
    CURRENT_TEMPLATE: 'current-template',
    MODAL_IS_OPEN: 'modal-is-open',
    SAVE_CURRENT_TEMPLATE_MODAL_CONTENT: 'save-template-modal-is-open',
    ALL_TEMPLATES_MODAL_CONTENT: 'all-templates-modal-is-open',
}

const init = () => initialState

const reducer = (state, action) => {
    switch (action.type) {
        case ACTIONS.PAYER_CHANGED:
            return {
                ...state,
                payer: action.payload
            }
        case ACTIONS.PAYMENT_DESCRIPTION:
            return {
                ...state,
                paymentDescription: action.payload
            }
        case ACTIONS.RECEIVER_CHANGED:
            return {
                ...state,
                receiver: action.payload
            }
        case ACTIONS.PAYCODE_CHANGED:
            return {
                ...state,
                payCode: action.payload
            }
        case ACTIONS.CURRENCY_CHANGED:
            return {
                ...state,
                currencyCode: action.payload
            }
        case ACTIONS.TOTAL_AMOUNT:
            return {
                ...state,
                totalAmount: action.payload
            }
        case ACTIONS.BANK_NUMBER_CHANGE:
            return {
                ...state,
                bankNumber: action.payload,
                accountReceivable: state.bankNumber + state.accountNumber + state.controlNumber
            }
        case ACTIONS.ACCOUNT_NUMBER_CHANGE:
            return {
                ...state,
                accountNumber: action.payload,
                accountReceivable: state.bankNumber + state.accountNumber + state.controlNumber
            }
        case ACTIONS.CONTROL_NUMBER_CHANGE:
            return {
                ...state,
                controlNumber: action.payload,
                accountReceivable: state.bankNumber + state.accountNumber + state.controlNumber
            }
        case ACTIONS.MODEL_CODE:
            return {
                ...state,
                modelCode: action.payload
            }
        case ACTIONS.PAYMENT_NUMBER:
            return {
                ...state,
                paymentNumber: action.payload
            }
        case ACTIONS.CURRENT_TEMPLATE:
            return {
                ...state,
                currentTemplate: action.payload,
            }
        case ACTIONS.MODAL_IS_OPEN:
            return {
                ...state,
                modalIsOpen: action.payload,
            }
        case ACTIONS.SAVE_CURRENT_TEMPLATE_MODAL_CONTENT:
            return {
                ...state,
                saveCurrentTemplateModalContent: action.payload,
            }
        case ACTIONS.ALL_TEMPLATES_MODAL_CONTENT:
            return {
                ...state,
                allTemplatesModalIsContent: action.payload,
            }
        case ACTIONS.USE_SELECTED_TEMPLATE:
            return {
                ...state,
                ...action.payload,
            }
        case ACTIONS.RESET_VALUES:
            return init()
        default:
            console.error('Action.type must be ' + action.type)
            return state
    }
}

function Payslip() {
    const [state, dispatch] = useReducer(reducer, initialState, init)
    const params = new URLSearchParams(useLocation().search)


    const printPayslip = () => {
        window.print()
    }


    return (
        <><Container>
            <BankSlipTitle>Nalog Za Uplatu</BankSlipTitle>
            <LeftSide >
                <Textarea label='Platilac'
                          id='payer'
                          help='payerHelp'
                          helpText='U ovo polje upišite podatke osobe koja je Platilac.'
                          readOnly
                          disabled={true}
                          value={state.payer}

                />
                <Textarea
                    label='Svrha uplate'
                    id='paymentDescription'
                    help='paymentDescriptionHelp'
                    helpText='U ovo polje upišite svrhu uplate.'
                    value={state.paymentDescription}
                    disabled={true}
                />
                <Textarea label='Primalac'
                          id='receiverDescription'
                          help='receiverDescriptionHelp'
                          helpText='U ovo polje upišite podatke osobe koja je Primalac.'
                          value={state.receiver}
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
                    value={state.payCode}
                    disabled={true}

                />
                <Input
                    width={23}
                    disabled={true}
                    label='Valuta'
                    id='valuta'
                    help='valutaHelp'
                    helpText='Ovo polje je onemogućeno jer valuta mora biti RSD.'
                    value={state.currencyCode}
                    readOnly
                />
                <Input
                    type='number'
                    width={54}
                    label='Iznos'
                    id='totalAmount'
                    help='totalAmountHelp'
                    helpText='Ovde upišite brojevima ukupan iznos koji zelite da uplatite.'
                    value={state.totalAmount}
                    readOnly
                    disabled={true}
                />
                <Input
                    type='text'
                    width={100}
                    label='Poziv na broj'
                    id='paymentNumber'
                    help='paymentNumberHelp'
                    helpText='Ovde upišite brojevima poziv na broj na koji zelite da uplatite.'
                    value={state.totalAmount}
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
                    value={state.modelCode}
                    disabled={true}
                />
                <Input
                    type='number'
                    width={75}
                    label='Poziv na broj'
                    id='paymentNumber'
                    help='paymentNumberHelp'
                    helpText='Ovde upišite brojevima poziv na broj za ovu uplatnicu.'
                    value={state.paymentNumber}
                    disabled={true}
                />

            </RightSide>
        </Container>
                <Button onClick={printPayslip}>
                    Odstampaj uplatnicu
                </Button>
        </>
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
