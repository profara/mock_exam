import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import { ChakraProvider, createStandaloneToast } from '@chakra-ui/react'
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import Login from "./components/login/Login.jsx";
import Signup from "./components/signup/Signup.jsx";
import AuthProvider from "./components/context/AuthContext.jsx";
import ProtectedRoute from "./components/shared/ProtectedRoute.jsx";
import './index.css'
import CreateCandidateProfileForm from "./components/candidate/CreateCandidateProfileForm.jsx";
import UpdateCandidateProfileForm from "./components/candidate/UpdateCandidateProfileForm.jsx";
import SelectedCardsProvider from "./components/context/SelectedCardsContext.jsx";
import Uplatnica from "./components/Uplatnica.jsx";




const { ToastContainer } = createStandaloneToast()

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
      element: <Uplatnica/>
    },
    {
        path: "/updateProfil",
        element:<UpdateCandidateProfileForm />
    },
    {
        path:"/registracija",
        element: <Signup/>
    },
    {
        path: "termini",
        element: <ProtectedRoute>
                     <App/>
                </ProtectedRoute>
    }
]);

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
      <ChakraProvider>
          <SelectedCardsProvider>
          <AuthProvider>

                <RouterProvider router={router}/>

          </AuthProvider>

          <ToastContainer/>
          </SelectedCardsProvider>
      </ChakraProvider>
  </React.StrictMode>,
)
