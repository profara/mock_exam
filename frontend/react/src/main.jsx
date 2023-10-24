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
import Currency from "./components/currency/Currency.jsx";
import ApplicationProvider from "./components/context/ApplicationContext.jsx";
import CurrencyProvider from "./components/context/CurrencyContext.jsx";
import PaymentProvider from "./components/context/PaymentContext.jsx";
import Candidates from "./components/candidate/Candidates.jsx";
import AdminOnly from "./components/shared/AdminOnly.jsx";
import EmailVerificationPage from "./components/signup/EmailVerificationPage.jsx";
import EmailConfirmed from "./components/signup/EmailConfirmed.jsx";


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
        path: "/valuta",
        element:
            <ProtectedRoute>
                <Currency/>
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

    }
]);

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <ChakraProvider>
            <PaymentProvider>
                <ApplicationProvider>
                    <CurrencyProvider>
                        <SelectedCardsProvider>
                            <AuthProvider>

                                <RouterProvider router={router}/>

                            </AuthProvider>

                            <ToastContainer/>
                        </SelectedCardsProvider>
                    </CurrencyProvider>
                </ApplicationProvider>
            </PaymentProvider>
        </ChakraProvider>
    </React.StrictMode>,
)
