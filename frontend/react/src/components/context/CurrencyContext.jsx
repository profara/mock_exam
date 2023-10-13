import {createContext, useContext, useEffect, useState} from "react";
import {getCurrency} from "../../services/client.js";
import {errorNotification} from "../../services/notification.js";

const CurrencyContext = createContext();

export const useCurrency = () => useContext(CurrencyContext);

const CurrencyProvider = ({children}) => {
    const [currency, setCurrency] = useState(null);
    const [loadingCurrency, setLoadingCurrency] = useState(true);



    useEffect(() => {

        const fetchCurrency = async () => {
            let currencyId = parseInt(localStorage.getItem("selectedCurrency"));

            if (currencyId && !currency) {
                await getCurrency(currencyId)
                    .then(res => {
                        setCurrency(res.data);
                    }).catch(err => {
                    errorNotification(
                        err.code,
                        err.response.data.message
                    )
                }).finally(() => {
                    setLoadingCurrency(false)
                })
            } else{
                setLoadingCurrency(false);
            }
        }
        fetchCurrency();
    }, [])

    return (
        <CurrencyContext.Provider
            value={{currency, setCurrency, loadingCurrency}}>
            {children}
        </CurrencyContext.Provider>
    );
}

export default CurrencyProvider;