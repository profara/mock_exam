import S from 'styled-components'

const Label = ({ label }) => <StyledLabel htmlFor={label}>{label}</StyledLabel>

const StyledLabel = S.label`
    display: block;
    line-height: 1.2em;
    font-size: 12px;
    color: #000;
    margin-bottom: 0.4em;
`

export default Label;
