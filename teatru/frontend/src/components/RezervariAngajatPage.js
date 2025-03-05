import React, { useEffect, useState } from 'react';
import {
    AppBar,
    Toolbar,
    Typography,
    Button,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Paper,
    Box,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
} from '@mui/material';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const RezervariAngajatPage = () => {
    const [rezervari, setRezervari] = useState([]);
    const [rezervareDeSters, setRezervareDeSters] = useState(null);
    const [openDialog, setOpenDialog] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        axios.get('/api/v1/rezervare/with-details')
            .then(response => {
                setRezervari(response.data);
            })
            .catch(error => console.error('Eroare la preluarea rezervărilor:', error));
    }, []);

    const handleBack = () => {
        navigate('/angajat');
    };

    const handleOpenDeleteDialog = (idRezervare) => {
        setRezervareDeSters(idRezervare);
        setOpenDialog(true);
    };

    const handleCloseDeleteDialog = () => {
        setRezervareDeSters(null);
        setOpenDialog(false);
    };

    const handleDeleteRezervare = () => {
        if (rezervareDeSters) {
            axios.delete(`/api/v1/rezervare/${rezervareDeSters}`)
                .then(() => {
                    setRezervari((prevRezervari) =>
                        prevRezervari.filter((rezervare) => rezervare.idRezervare !== rezervareDeSters)
                    );
                    alert('Rezervarea a fost ștearsă cu succes!');
                })
                .catch((error) => {
                    console.error('Eroare la ștergerea rezervării:', error);
                    alert('A apărut o eroare la ștergerea rezervării.');
                })
                .finally(() => {
                    handleCloseDeleteDialog();
                });
        }
    };

    return (
        <div style={{ backgroundColor: '#FFC0CB', minHeight: '100vh', padding: '0' }}> {/* Fundal roșu deschis */}

            <AppBar position="static" sx={{ backgroundColor: '#8B0000' }}>
                <Toolbar>
                    <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                        Rezervări
                    </Typography>
                    <Button color="inherit" onClick={handleBack}>
                        Înapoi
                    </Button>
                </Toolbar>
            </AppBar>

            <Box sx={{ padding: '20px' }}>
                <Typography variant="h4" component="h2" gutterBottom>
                    Lista Rezervărilor
                </Typography>
                <TableContainer component={Paper}>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell><strong>Nume Utilizator</strong></TableCell>
                                <TableCell><strong>Prenume Utilizator</strong></TableCell>
                                <TableCell><strong>Email Utilizator</strong></TableCell>
                                <TableCell><strong>Nume Spectacol</strong></TableCell>
                                <TableCell><strong>Data Spectacolului</strong></TableCell>
                                <TableCell><strong>Ora Spectacolului</strong></TableCell>
                                <TableCell><strong>Număr Bilete</strong></TableCell>
                                <TableCell><strong>Data Rezervării</strong></TableCell>
                                <TableCell><strong>Acțiuni</strong></TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {rezervari.length > 0 ? (
                                rezervari.map((rezervare, index) => (
                                    <TableRow key={index}>
                                        <TableCell>{rezervare.numeUtilizator}</TableCell>
                                        <TableCell>{rezervare.prenumeUtilizator}</TableCell>
                                        <TableCell>{rezervare.emailUtilizator}</TableCell>
                                        <TableCell>{rezervare.numeSpectacol}</TableCell>
                                        <TableCell>{new Date(rezervare.dataSpectacol).toLocaleDateString()}</TableCell>
                                        <TableCell>{rezervare.oraSpectacol}</TableCell>
                                        <TableCell>{rezervare.nrBilete}</TableCell>
                                        <TableCell>{new Date(rezervare.dataRezervarii).toLocaleDateString()}</TableCell>
                                        <TableCell>
                                            <Button
                                                variant="contained"
                                                color="secondary"
                                                onClick={() => handleOpenDeleteDialog(rezervare.idRezervare)}
                                            >
                                                Ștergere
                                            </Button>
                                        </TableCell>
                                    </TableRow>
                                ))
                            ) : (
                                <TableRow>
                                    <TableCell colSpan={9} align="center">
                                        Nu există rezervări disponibile.
                                    </TableCell>
                                </TableRow>
                            )}
                        </TableBody>
                    </Table>
                </TableContainer>
            </Box>


            <Dialog open={openDialog} onClose={handleCloseDeleteDialog}>
                <DialogTitle>Confirmare Ștergere</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        Sigur doriți să ștergeți această rezervare? Această acțiune este ireversibilă.
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCloseDeleteDialog} color="secondary">
                        Anulează
                    </Button>
                    <Button onClick={handleDeleteRezervare} color="primary" autoFocus>
                        Da
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );
};

export default RezervariAngajatPage;
