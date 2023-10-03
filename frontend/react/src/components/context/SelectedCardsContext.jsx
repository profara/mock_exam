import {createContext, useContext, useState} from "react";

const SelectedCardsContext = createContext({});

const SelectedCardsProvider = ({children}) => {
    const [selectedCards, setSelectedCards] = useState([]);



    return (
        <SelectedCardsContext.Provider value={{
           selectedCards,
            setSelectedCards
        }}>

            {children}
        </SelectedCardsContext.Provider>
    )
}

export const useCard = () => useContext(SelectedCardsContext);

export default SelectedCardsProvider;