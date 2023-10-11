import axios from "axios";

const getAuthConfig = () => ({
    headers: {
        Authorization: `Bearer ${localStorage.getItem("access_token")}`
    }
})

export const getAppointments = async () => {
        return await axios.get(
            `${import.meta.env.VITE_API_BASE_URL}/api/appointments`,
            getAuthConfig()
        )
}

export const getUserProfile = async (email) => {
    return await axios.get(
        `${import.meta.env.VITE_API_BASE_URL}/api/userProfiles`,
        {
            params: email ? {email: email} : {},
            headers: {Authorization: `Bearer ${localStorage.getItem("access_token")}`}
        }
    );
}

export const getCandidate = async (email) => {
    return await axios.get(
        `${import.meta.env.VITE_API_BASE_URL}/api/candidates`,
        {
            params: email ? {email: email} : {},
            headers: {Authorization: `Bearer ${localStorage.getItem("access_token")}`}
        }
    );
}

export const getSchools = async () => {
    return await axios.get(
        `${import.meta.env.VITE_API_BASE_URL}/api/schools`,
        getAuthConfig()
        );
}

export const getCities = async () => {
    return await axios.get(
        `${import.meta.env.VITE_API_BASE_URL}/api/cities`,
        getAuthConfig()
    )
}

export const getCurrencies = async () => {
    return await axios.get(
        `${import.meta.env.VITE_API_BASE_URL}/api/currencies`,
        getAuthConfig()
    );
}

export const getCurrency = async (currencyId) => {
    return await axios.get(
        `${import.meta.env.VITE_API_BASE_URL}/api/currencies/${currencyId}`,
        getAuthConfig()
    );
}

export const saveUserProfile = async (user) => {
    return await axios.post(
        `${import.meta.env.VITE_API_BASE_URL}/api/userProfiles`,
        user
    )
}

export const saveCandidate = async (candidate) => {
    return await axios.post(
        `${import.meta.env.VITE_API_BASE_URL}/api/candidates`,
        candidate,
        getAuthConfig()
    )
}

export const saveApplication = async (application) => {
    return await axios.post(
        `${import.meta.env.VITE_API_BASE_URL}/api/applications`,
        application,
        getAuthConfig()
    )
}

export const savePayment = async (payment) => {
    return await axios.post(
        `${import.meta.env.VITE_API_BASE_URL}/api/payments`,
        payment,
        getAuthConfig()
    )
}

export const updateCandidate = async (id, candidate) => {
    return await axios.patch(
        `${import.meta.env.VITE_API_BASE_URL}/api/candidates/${id}`,
        candidate,
        getAuthConfig()
    )
}


export const login = async (emailAndPassword) => {
        return await axios.post(`${import.meta.env.VITE_API_BASE_URL}/api/auth/login`,
                emailAndPassword
        )

}
