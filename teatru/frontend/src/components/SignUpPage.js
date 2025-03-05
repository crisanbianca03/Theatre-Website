import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { Box, TextField, Button, Typography, Alert } from '@mui/material';

const SignUpPage = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [nume, setNume] = useState('');
    const [prenume, setPrenume] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const navigate = useNavigate();

    const handleSignUp = async () => {
        if (password !== confirmPassword) {
            setError('Parolele nu se potrivesc!');
            return;
        }

        try {
            await axios.post('/api/v1/utilizator/add', {
                email: email,
                parola: password,
                tip: 'client',
                nume: nume,
                prenume: prenume,
            }, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            setSuccess('Contul a fost creat cu succes!');
            setError('');
            setTimeout(() => navigate('/'), 2000);
        } catch (error) {
            setError('Eroare la crearea contului! Încercați din nou.');
            setSuccess('');
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
                backgroundColor: '#FDEDEC',
            }}
        >
            <Typography variant="h4" sx={{ marginBottom: '20px' }}>
                Creare cont
            </Typography>
            {error && (
                <Alert severity="error" sx={{ marginBottom: '20px', width: '100%', maxWidth: '400px' }}>
                    {error}
                </Alert>
            )}
            {success && (
                <Alert severity="success" sx={{ marginBottom: '20px', width: '100%', maxWidth: '400px' }}>
                    {success}
                </Alert>
            )}
            <TextField
                label="Nume"
                value={nume}
                onChange={(e) => setNume(e.target.value)}
                sx={{ marginBottom: '20px', width: '100%', maxWidth: '400px' }}
            />
            <TextField
                label="Prenume"
                value={prenume}
                onChange={(e) => setPrenume(e.target.value)}
                sx={{ marginBottom: '20px', width: '100%', maxWidth: '400px' }}
            />
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
            <TextField
                label="Confirmă Parola"
                type="password"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                sx={{ marginBottom: '20px', width: '100%', maxWidth: '400px' }}
            />
            <Button
                variant="contained"
                onClick={handleSignUp}
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
                Creare cont
            </Button>
        </Box>
    );
};

export default SignUpPage;
