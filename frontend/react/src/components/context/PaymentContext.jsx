import {createContext, useContext, useEffect, useState} from "react";

const PaymentContext = createContext({});

export const usePayment = () => useContext(PaymentContext);

const PaymentProvider = ({children}) => {
    const [payment, setPayment] = useState(null);
    const [loadingPayment, setLoadingPayment] = useState(true);

    useEffect(() => {
        setTimeout(() => {
        const storedPayment = localStorage.getItem("payment");
        if(storedPayment && !payment){
            setPayment(JSON.parse(storedPayment));
        }

        setLoadingPayment(false);

        }, 50)
    }, []);

    return (
        <PaymentContext.Provider
            value={{payment, setPayment, loadingPayment}}>
            {children}
        </PaymentContext.Provider>
    );
}

export default PaymentProvider;