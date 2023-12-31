import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import {ChakraProvider, createStandaloneToast} from '@chakra-ui/react'
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import Login from "./components/login/Login.jsx";
import Signup from "./components/signup/Signup.jsx";
import AuthProvider from "./components/context/AuthContext.jsx";
import ProtectedRoute from "./components/shared/ProtectedRoute.jsx";
import './index.css'
import CreateCandidateProfileForm from "./components/candidate/CreateCandidateProfileForm.jsx";
import UpdateCandidateProfileForm from "./components/candidate/UpdateCandidateProfileForm.jsx";
import SelectedCardsProvider from "./components/context/SelectedCardsContext.jsx";
import Payslip from "./components/payslip/Payslip.jsx";
import ApplicationProvider from "./components/context/ApplicationContext.jsx";
import Candidates from "./components/candidate/Candidates.jsx";
import AdminOnly from "./components/shared/AdminOnly.jsx";
import EmailVerificationPage from "./components/signup/EmailVerificationPage.jsx";
import EmailConfirmed from "./components/signup/EmailConfirmed.jsx";
import CandidatesByAppointment from "./components/candidate/CandidatesByAppointment.jsx";
import AppointmentsByCandidate from "./components/appointment/AppointmentsByCandidate.jsx";
import AppointmentOrderProvider from "./components/context/AppointmentOrderContext.jsx";


const {ToastContainer} = createStandaloneToast()

const router = createBrowserRouter([
    {
        path: "/",
        element: <Login/>
    },
    {
        path: "/profil",
        element: <CreateCandidateProfileForm/>
    },
    {
        path: "/uplatnica",
        element:
            <ProtectedRoute>
                <Payslip/>
            </ProtectedRoute>
    },
    {
        path: "/updateProfil",
        element: <UpdateCandidateProfileForm/>
    },
    {
        path: "/registracija",
        element: <Signup/>
    },
    {
        path: "termini",
        element: <ProtectedRoute>
            <App/>
        </ProtectedRoute>
    },
    {
        path: "/kandidati",
        element:
            <AdminOnly>
                <Candidates/>
            </AdminOnly>
    },
    {
        path: "/potvrdaRegistracije",
        element:

            <EmailVerificationPage/>

    },
    {
        path: "/uspesnoPotvrdjen",
        element:

            <EmailConfirmed/>

    },
    {
        path: "/terminKandidati",
        element:
            <AdminOnly>
                <CandidatesByAppointment/>
            </AdminOnly>
    },
    {
        path: "/mojePrijave",
        element:
            <ProtectedRoute>
                <AppointmentsByCandidate/>
            </ProtectedRoute>
    }
]);

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <ChakraProvider>
            <ApplicationProvider>
                        <SelectedCardsProvider>
                            <AuthProvider>
                                <AppointmentOrderProvider>
                                <RouterProvider router={router}/>
                                </AppointmentOrderProvider>
                            </AuthProvider>
                                <ToastContainer/>
                        </SelectedCardsProvider>
                </ApplicationProvider>
        </ChakraProvider>
    </React.StrictMode>,
)
