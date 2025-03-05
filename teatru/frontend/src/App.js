import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import HomePage from './components/HomePage';
import LoginPage from './components/LoginPage';
import SignUpPage from './components/SignUpPage';
import AdminPage from './components/AdminPage';
import AngajatPage from './components/AngajatPage';
import ClientPage from './components/ClientPage';
import RezervariPage from './components/RezervariPage';
import AdaugareSpectacolPage from './components/AdaugareSpectacolPage';
import RezervariAngajatPage from './components/RezervariAngajatPage';
import UtilizatoriPage from './components/UtilizatoriPage';
function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<HomePage />} />
                <Route path="/login" element={<LoginPage />} />
                <Route path="/signup" element={<SignUpPage />} />
                <Route path="/admin" element={<AdminPage />} />
                <Route path="/angajat" element={<AngajatPage />} />
                <Route path="/client" element={<ClientPage />} />
                <Route path="/rezervari" element={<RezervariPage />} />
                <Route path="/adaugare-spectacol" element={<AdaugareSpectacolPage />} />
                <Route path="/rezervari-angajat" element={<RezervariAngajatPage />} />
                <Route path="/utilizatori" element={<UtilizatoriPage />} />

            </Routes>
        </Router>
    );
}

export default App;
