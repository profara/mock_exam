import {createContext, useContext, useState} from "react";

const ApplicationContext = createContext({});

export const useApplication = () => useContext(ApplicationContext);

const ApplicationProvider = ({children}) => {
    const [application, setApplication] = useState(null);

    return (
        <ApplicationContext.Provider
            value={{application, setApplication}}>
            {children}
        </ApplicationContext.Provider>
    );
}

export default ApplicationProvider;