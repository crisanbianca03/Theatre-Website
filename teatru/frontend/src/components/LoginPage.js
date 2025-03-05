import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { Box, TextField, Button, Typography, Alert } from '@mui/material';

const LoginPage = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleLogin = async () => {
        try {
            const response = await axios.post('/api/v1/utilizator/login', {
                email: email,
                parola: password
            }, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            const { tip, nume, prenume,idUtilizator } = response.data;


            localStorage.setItem('loggedInUser', JSON.stringify({ tip, nume, prenume,idUtilizator }));

            if (tip === 'admin') {
                navigate('/admin');
            } else if (tip === 'angajat') {
                navigate('/angajat');
            } else if (tip === 'client') {
                navigate('/client');
            } else {
                setError('Rol necunoscut! Contactați administratorul.');
            }
        } catch (error) {
            setError('Autentificare eșuată! Verificați credențialele.');
        }
    };

    return (
        <Box
            sx={{
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                justifyContent: 'center',
                height: '100vh',
                backgroundColor: '#FFE4E1',
            }}
        >
            <Typography variant="h4" sx={{ marginBottom: '20px' }}>
                Logare
            </Typography>
            {error && (
                <Alert severity="error" sx={{ marginBottom: '20px', width: '100%', maxWidth: '400px' }}>
                    {error}
                </Alert>
            )}
            <TextField
                label="Email"
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                sx={{ marginBottom: '20px', width: '100%', maxWidth: '400px' }}
            />
            <TextField
                label="Parola"
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                sx={{ marginBottom: '20px', width: '100%', maxWidth: '400px' }}
            />
            <Button
                variant="contained"
                onClick={handleLogin}
                sx={{
                    backgroundColor: '#8B0000',
                    color: '#fff',
                    '&:hover': {
                        backgroundColor: '#700000',
                    },
                    width: '100%',
                    maxWidth: '400px',
                }}
            >
                Logare
            </Button>
        </Box>
    );
};

export default LoginPage;
