import axios from "axios";
import html2canvas from 'html2canvas'

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

export const getAllSortedAppointments = async () => {
    return await axios.get(
        `${import.meta.env.VITE_API_BASE_URL}/api/appointments/all-sorted`,
        getAuthConfig()
    )
}

export const getAppointmentsByCandidate = async (id, page) => {
    return await axios.get(
        `${import.meta.env.VITE_API_BASE_URL}/api/appointments/by-candidate/${id}?page=${page}`,
        getAuthConfig()
    )
}

export const getAppointmentsByCandidateNotSigned = async (id, page) => {
    return await axios.get(
        `${import.meta.env.VITE_API_BASE_URL}/api/appointments/by-candidate/${id}/not-signed?page=${page}`,
        getAuthConfig()
    )
}

export const getCandidatesByAppointment = async (id, page) => {
    return await axios.get(
        `${import.meta.env.VITE_API_BASE_URL}/api/candidates/by-appointment/${id}?page=${page}`,
        getAuthConfig()
    )
}

export const sortCandidatesByColumnName = async (id, zipCode, schoolCode, attendedPreparation, page, pageSize, column, direction) => {
    let queryParams = `page=${page}&size=${pageSize}&sort=${column},${direction}`;

    if (zipCode !== "") {
        queryParams += `&zipCode=${zipCode}`;
    }

    if (schoolCode !== "") {
        queryParams += `&schoolCode=${schoolCode}`;
    }

    if (attendedPreparation !== '') {
        queryParams += `&attendedPreparation=${attendedPreparation}`;
    }

    return await axios.get(
        `${import.meta.env.VITE_API_BASE_URL}/api/candidates/by-appointment/${id}?${queryParams}`,
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

export const getAllCandidatesByAppointment = async (id, zipCode, schoolCode, attendedPreparation) => {
    let queryParams = [];

    if (zipCode !== "") {
        queryParams.push(`zipCode=${zipCode}`);
    }

    if (schoolCode !== "") {
        queryParams.push(`schoolCode=${schoolCode}`);
    }

    if (attendedPreparation !== '') {
        queryParams.push(`attendedPreparation=${attendedPreparation}`);
    }

    const queryString = queryParams.length > 0 ? '?' + queryParams.join('&') : '';

    return await axios.get(
        `${import.meta.env.VITE_API_BASE_URL}/api/candidates/by-appointment/${id}/all${queryString}`,
        getAuthConfig()
    )
}

export const filterCandidates = async (zipCode, schoolCode, attendedPreparation, page, size, column, direction) => {
    let queryParams = `page=${page}&size=${size}`;

    if(column && direction){
        queryParams += `&sort=${column},${direction}`
    }

    if (zipCode !== "") {
        queryParams += `&zipCode=${zipCode}`;
    }

    if (schoolCode !== "") {
        queryParams += `&schoolCode=${schoolCode}`;
    }

    if (attendedPreparation !== '') {
        queryParams += `&attendedPreparation=${attendedPreparation}`;
    }
    return await axios.get(
        `${import.meta.env.VITE_API_BASE_URL}/api/candidates/filter?${queryParams}`,
        getAuthConfig()
    )
}

export const filterCandidatesByAppointment = async (appointmentId, zipCode, schoolCode, attendedPreparation, page, size) => {
    let queryParams = `page=${page}&size=${size}`;

    if (zipCode !== "") {
        queryParams += `&zipCode=${zipCode}`;
    }

    if (schoolCode !== "") {
        queryParams += `&schoolCode=${schoolCode}`;
    }

    if (attendedPreparation !== '') {
        queryParams += `&attendedPreparation=${attendedPreparation}`;
    }
    return await axios.get(
        `${import.meta.env.VITE_API_BASE_URL}/api/candidates/by-appointment/${appointmentId}/filter?${queryParams}`,
        getAuthConfig()
    )
}

export const getSchools = async () => {
    return await axios.get(
        `${import.meta.env.VITE_API_BASE_URL}/api/schools`,
        getAuthConfig()
        );
}

export const getAllSchools = async () => {
    return await axios.get(
        `${import.meta.env.VITE_API_BASE_URL}/api/schools/all`,
        getAuthConfig()
    );
}

export const getCities = async () => {
    return await axios.get(
        `${import.meta.env.VITE_API_BASE_URL}/api/cities`,
        getAuthConfig()
    )
}

export const getAllCities = async () => {
    return await axios.get(
        `${import.meta.env.VITE_API_BASE_URL}/api/cities/all`,
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

export const signCandidateForAppointment = async (candidateId, appointmentId) => {
    return await axios.post(
        `${import.meta.env.VITE_API_BASE_URL}/api/applications/by-candidate/${candidateId}/appointments/${appointmentId}`,
        {},
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

export const cancelAppointment = async (candidateId, appointmentId) => {
    return await axios.delete(
        `${import.meta.env.VITE_API_BASE_URL}/api/applications/by-candidate/${candidateId}/appointments/${appointmentId}`,
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

export const capturePayslipAndSend = async () => {
    const payslipElement = document.getElementById('payslip');
    const canvas = await html2canvas(payslipElement);
    const imageBase64 = canvas.toDataURL('image/png');

    const config = {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('access_token')}`,
            'Content-Type': 'application/json'
        }
    };

    const data = {
        imageData: imageBase64
    };

    return await axios.post(`${import.meta.env.VITE_API_BASE_URL}/api/payments/send-payslip`,
        data,
        config
        )

};
