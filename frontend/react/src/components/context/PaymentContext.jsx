import {createContext, useContext, useState} from "react";

const PaymentContext = createContext();

export const usePayment = () => useContext(PaymentContext);

const PaymentProvider = ({children}) => {
    const [payment, setPayment] = useState(null);

    return (
        <PaymentContext.Provider
            value={{payment, setPayment}}>
            {children}
        </PaymentContext.Provider>
    );
}

export default PaymentProvider;