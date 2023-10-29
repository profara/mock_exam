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

export const getCandidatesByAppointment = async (id, page) => {
    return await axios.get(
        `${import.meta.env.VITE_API_BASE_URL}/api/appointments/${id}/candidates?page=${page}`,
        getAuthConfig()
    )
}

export const getExams = async () => {
    return await axios.get(
        `${import.meta.env.VITE_API_BASE_URL}/api/exams`,
        getAuthConfig()
    )
}

export const getUserProfile = async (email) => {
    return await axios.get(
        `${import.meta.env.VITE_API_BASE_URL}/api/userProfiles`,
        {
            params: email ? {email: email} : {},
            ...getAuthConfig()
        }
    );
}

export const getCandidate = async (email) => {
    return await axios.get(
        `${import.meta.env.VITE_API_BASE_URL}/api/candidates`,
        {
            params: email ? {email: email} : {},
            ...getAuthConfig()
        }
    );
}

export const getCandidates = async (page) => {
    return await axios.get(
        `${import.meta.env.VITE_API_BASE_URL}/api/candidates?page=${page}`,
        getAuthConfig()
    )
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

export const getPriceListItem = async (criteria) => {
    return await axios.get(
        `${import.meta.env.VITE_API_BASE_URL}/api/priceListItems/search`,
        {
            params: criteria,
            ...getAuthConfig()
        }
    );
}

export const getPriceListItems = async () => {
    return await axios.get(
        `${import.meta.env.VITE_API_BASE_URL}/api/priceListItems`,
        getAuthConfig()
    );
}

export const confirmUser = async (token) => {
    return await axios.get(
        `${import.meta.env.VITE_API_BASE_URL}/api/userProfiles/confirm?token=${token}`,
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

export const saveAppointment = async (appointment) => {
    return await axios.post(
        `${import.meta.env.VITE_API_BASE_URL}/api/appointments`,
        appointment,
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

export const deleteAppointment = async (id) => {
    return await axios.delete(
        `${import.meta.env.VITE_API_BASE_URL}/api/appointments/${id}`,
        getAuthConfig()
    )
}

export const deleteCandidate = async (id) => {
    return await axios.delete(
        `${import.meta.env.VITE_API_BASE_URL}/api/candidates/${id}`,
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

export const updateAppointment = async (id, appointment) => {
    return await axios.put(
        `${import.meta.env.VITE_API_BASE_URL}/api/appointments/${id}`,
        appointment,
        getAuthConfig()
    )
}


export const login = async (emailAndPassword) => {
        return await axios.post(`${import.meta.env.VITE_API_BASE_URL}/api/auth/login`,
                emailAndPassword
        )

}
