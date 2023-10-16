import {useEffect} from "react";
import {useNavigate} from "react-router-dom";
import {useAuth} from "../context/AuthContext.jsx";

const AdminOnly = ({children}) => {


    const {isAdmin} = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        if(!isAdmin()){
            navigate("/")
        }
    })

    return isAdmin() ? children : "";
}

export default AdminOnly;