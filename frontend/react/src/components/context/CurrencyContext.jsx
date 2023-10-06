import {createContext, useContext, useState} from "react";

const CurrencyContext = createContext();

export const useCurrency = () => useContext(CurrencyContext);

const CurrencyProvider = ({children}) => {
    const [currency, setCurrency] = useState(null);

    return (
        <CurrencyContext.Provider
            value={{currency, setCurrency}}>
            {children}
        </CurrencyContext.Provider>
    );
}

export default CurrencyProvider;