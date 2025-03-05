import React, { useEffect, useState } from 'react';
import {
    AppBar,
    Toolbar,
    Typography,
    Button,
    Box,
    TextField,
    Select,
    MenuItem,
    Paper,
} from '@mui/material';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const AdaugareSpectacolPage = () => {
    const [titlu, setTitlu] = useState('');
    const [descriere, setDescriere] = useState('');
    const [gen, setGen] = useState('');
    const [dataSpectacol, setDataSpectacol] = useState('');
    const [oraSpectacol, setOraSpectacol] = useState('');
    const [nrBilete, setNrBilete] = useState('');
    const [durata, setDurata] = useState('');
    const [sala, setSala] = useState('');
    const [actorii, setActorii] = useState('');
    const [imagine, setImagine] = useState(null);
    const [sali, setSali] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {

        axios.get('/api/v1/sala')
            .then(response => {
                setSali(response.data);
            })
            .catch(error => console.error('Eroare la preluarea sălilor:', error));
    }, []);

    const handleAddSpectacol = () => {

        const formData = new FormData();
        formData.append('titlu', titlu);
        formData.append('descriere', descriere);
        formData.append('gen', gen);
        formData.append('dataSpectacol', dataSpectacol);
        formData.append('oraSpectacol', oraSpectacol);
        formData.append('nrBilete', parseInt(nrBilete, 10));
        formData.append('durata', durata);
        formData.append('idSala', parseInt(sala, 10));
        formData.append('actorii', actorii);
        if (imagine) {
            formData.append('imagine', imagine);
        }

        axios.post('/api/v1/spectacol', formData, {
            headers: {
                'Content-Type': 'multipart/form-data',
            },
        })
            .then(() => {
                alert('Spectacolul a fost adăugat cu succes!');
                navigate('/angajat');
            })
            .catch(error => {
                console.error('Eroare la adăugarea spectacolului:', error);
                alert('A apărut o eroare la adăugarea spectacolului.');
            });
    };

    const handleCancel = () => {
        navigate('/angajat');
    };

    return (
        <div style={{ backgroundColor: '#FFC0CB', minHeight: '100vh', padding: '0' }}>

            <AppBar position="static" sx={{ backgroundColor: '#8B0000' }}>
                <Toolbar>
                    <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                        Adaugare Spectacol
                    </Typography>
                    <Button color="inherit" onClick={handleCancel}>
                        Anulează
                    </Button>
                </Toolbar>
            </AppBar>


            <Box
                component={Paper}
                sx={{
                    maxWidth: '600px',
                    margin: '50px auto',
                    padding: '20px',
                }}
            >
                <Typography variant="h4" component="h2" gutterBottom>
                    Completează datele spectacolului
                </Typography>
                <TextField
                    label="Titlu"
                    value={titlu}
                    onChange={(e) => setTitlu(e.target.value)}
                    fullWidth
                    margin="normal"
                />
                <TextField
                    label="Descriere"
                    value={descriere}
                    onChange={(e) => setDescriere(e.target.value)}
                    fullWidth
                    margin="normal"
                />
                <TextField
                    label="Gen"
                    value={gen}
                    onChange={(e) => setGen(e.target.value)}
                    fullWidth
                    margin="normal"
                />
                <TextField
                    label="Data Spectacol"
                    type="date"
                    value={dataSpectacol}
                    onChange={(e) => setDataSpectacol(e.target.value)}
                    fullWidth
                    margin="normal"
                    InputLabelProps={{
                        shrink: true,
                    }}
                />
                <Select
                    value={oraSpectacol}
                    onChange={(e) => setOraSpectacol(e.target.value)}
                    displayEmpty
                    fullWidth
                    margin="normal"
                >
                    <MenuItem value="" disabled>
                        Selectează Ora
                    </MenuItem>
                    {Array.from({ length: 24 }, (_, i) => {
                        const hour = i.toString().padStart(2, '0');
                        return (
                            <MenuItem key={hour} value={`${hour}:00`}>
                                {`${hour}:00`}
                            </MenuItem>
                        );
                    })}
                </Select>

                <TextField
                    label="Număr de Bilete"
                    type="number"
                    value={nrBilete}
                    onChange={(e) => setNrBilete(e.target.value)}
                    fullWidth
                    margin="normal"
                />
                <TextField
                    label="Durată"
                    value={durata}
                    onChange={(e) => setDurata(e.target.value)}
                    fullWidth
                    margin="normal"
                />
                <Select
                    value={sala}
                    onChange={(e) => setSala(e.target.value)}
                    displayEmpty
                    fullWidth
                    margin="normal"
                >
                    <MenuItem value="" disabled>
                        Selectează Sala
                    </MenuItem>
                    {sali.map((sala) => (
                        <MenuItem key={sala.idSala} value={sala.idSala}>
                            {`Sala ${sala.idSala}`}
                        </MenuItem>
                    ))}
                </Select>
                <TextField
                    label="Actorii (separați prin virgulă)"
                    value={actorii}
                    onChange={(e) => setActorii(e.target.value)}
                    fullWidth
                    margin="normal"
                />
                {/* Input pentru imagine */}
                <TextField
                    type="file"
                    onChange={(e) => setImagine(e.target.files[0])}
                    fullWidth
                    margin="normal"
                />
                <Box sx={{ marginTop: '20px', display: 'flex', justifyContent: 'space-between' }}>
                    <Button
                        variant="contained"
                        color="primary"
                        onClick={handleAddSpectacol}
                    >
                        Adaugă Spectacol
                    </Button>
                    <Button
                        variant="outlined"
                        color="secondary"
                        onClick={handleCancel}
                    >
                        Anulează
                    </Button>
                </Box>
            </Box>
        </div>
    );
};

export default AdaugareSpectacolPage;
