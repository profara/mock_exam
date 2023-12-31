import {createContext, useContext, useEffect, useState} from "react";
import {getCandidate, getUserProfile, login as performLogin} from "../../services/client.js"
import jwtDecode from "jwt-decode";
import {useCard} from "./SelectedCardsContext.jsx";
import {ROLE_ADMIN} from "../payslip/config/constants.js";

const AuthContext = createContext({});

const AuthProvider = ({children}) => {
    const [user,setUser] = useState(null);
    const [candidate, setCandidate] = useState(null);
    const {selectedCards, setSelectedCards} = useCard();
    const [loadingAuth, setLoadingAuth] = useState(true);

    const isAdmin = () => {
        return user && user.userRole.name === ROLE_ADMIN;
    }

    const loadUser = async () => {
        let token = localStorage.getItem("access_token");
        if (token) {
            try {
                token = jwtDecode(token)
                const res = await getUserProfile(token.sub);
                setUser({
                    ...res.data
                });
            } catch(err){
                console.error(err);
                logOut();
            }

        }
    }

    const loadCandidate = async () => {
        let token = localStorage.getItem("access_token");
        if (token) {
            try {
                token = jwtDecode(token)
                if(!token.scopes.includes(ROLE_ADMIN)) {
                    const res = await getCandidate(token.sub);
                    setCandidate({
                        ...res.data
                    });
                }
            } catch(err){
                setCandidate(null);
            }

        }
    }

    useEffect(() => {
        async function fetchData(){
            await loadUser();
            if(!isAdmin()) {
                await loadCandidate();
            }
            setLoadingAuth(false);
        }
        fetchData();
    }, []);

    const login = async (emailAndPassword) => {
        return new Promise((resolve,reject) => {
            performLogin(emailAndPassword).then(res => {
                const jwtToken = res.headers["authorization"];
                localStorage.setItem("access_token", jwtToken);
                setUser({
                    ...res.data.userProfileDTO
                })
                loadCandidate()
                resolve(res);
            }).catch(err => {
                reject(err);
            })
        })
    }

    const logOut = () => {
        localStorage.removeItem("access_token")
        setUser(null)
        setCandidate(null)
        setSelectedCards([])
        localStorage.removeItem("selectedCurrency");
    }

    const isUserAuthenticated = () => {
        const token = localStorage.getItem("access_token");
        if(!token){
            return false;
        }
        const {exp: expiration} = jwtDecode(token);
        if(Date.now() > expiration * 1000){
            logOut()
            return false;
        }
        return true;
    }

    return (
        <AuthContext.Provider value={{
            user,
            setUser,
            candidate,
            setCandidate,
            loadCandidate,
            login,
            logOut,
            isUserAuthenticated,
            loadUser,
            loadingAuth,
            isAdmin
        }}>

            {children}
        </AuthContext.Provider>
    )
}

export const useAuth = () => useContext(AuthContext);

export default AuthProvider;