import React, { useEffect, useState } from 'react';
import {
    AppBar,
    Toolbar,
    Typography,
    Box,
    Button,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Paper,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
} from '@mui/material';
import axios from 'axios';

const RezervariPage = () => {
    const [rezervari, setRezervari] = useState([]);
    const [dialogOpen, setDialogOpen] = useState(false);
    const [selectedRezervare, setSelectedRezervare] = useState(null);
    const user = JSON.parse(localStorage.getItem('loggedInUser'));


    useEffect(() => {
        if (user && user.idUtilizator) {
            const fetchRezervari = async () => {
                try {
                    const response = await axios.get(`/api/v1/rezervare/utilizator/${user.idUtilizator}`);
                    setRezervari(response.data);
                } catch (error) {
                    console.error('Eroare la preluarea rezervărilor:', error);
                    alert('A apărut o eroare la încărcarea rezervărilor.');
                }
            };

            fetchRezervari();
        } else {
            console.error('ID-ul utilizatorului este undefined');
        }
    }, []);

    const handleDialogOpen = (rezervare) => {
        setSelectedRezervare(rezervare);
        setDialogOpen(true);
    };

    const handleDialogClose = () => {
        setDialogOpen(false);
        setSelectedRezervare(null);
    };

    const handleDelete = async () => {
        if (selectedRezervare && selectedRezervare.idRezervare) {
            try {
                await axios.delete(`/api/v1/rezervare/${selectedRezervare.idRezervare}`);
                setRezervari(prevRezervari =>
                    prevRezervari.filter(rezervare => rezervare.idRezervare !== selectedRezervare.idRezervare)
                );
                alert('Rezervarea a fost anulată cu succes.');
            } catch (error) {
                console.error('Eroare la anularea rezervării:', error);
                alert('A apărut o eroare la anularea rezervării.');
            } finally {
                handleDialogClose();
            }
        } else {
            alert('Rezervarea selectată este invalidă.');
        }
    };

    return (
        <Box sx={{ backgroundColor: '#FFD6D6', minHeight: '100vh' }}>
            <AppBar position="static" sx={{ backgroundColor: '#8B0000' }}>
                <Toolbar>
                    <Typography variant="h5" component="div" sx={{ flexGrow: 1, textAlign: 'center' }}>
                        Rezervările Mele
                    </Typography>
                </Toolbar>
            </AppBar>

            <Box sx={{ padding: '20px' }}>
                {rezervari.length > 0 ? (
                    <TableContainer component={Paper} sx={{ backgroundColor: '#FFF4F4', borderRadius: '8px' }}>
                        <Table>
                            <TableHead>
                                <TableRow>
                                    <TableCell align="center"><b>Spectacol</b></TableCell>
                                    <TableCell align="center"><b>Data</b></TableCell>
                                    <TableCell align="center"><b>Ora</b></TableCell>
                                    <TableCell align="center"><b>Nr. bilete</b></TableCell>
                                    <TableCell align="center"><b>Sala</b></TableCell>
                                    <TableCell align="center"><b>Acțiune</b></TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {rezervari.map((rezervare) => {
                                    const dataSpectacol = new Date(rezervare.dataSpectacol);
                                    const isPast = dataSpectacol < new Date(); // Verifică dacă data este trecută
                                    return (
                                        <TableRow key={rezervare.idRezervare} sx={{ '&:hover': { backgroundColor: '#FFEAEA' } }}>
                                            <TableCell align="center">{rezervare.spectacolTitlu}</TableCell>
                                            <TableCell align="center">{dataSpectacol.toLocaleDateString()}</TableCell>
                                            <TableCell align="center">{rezervare.oraSpectacol}</TableCell>
                                            <TableCell align="center">{rezervare.nrBilete}</TableCell>
                                            <TableCell align="center">{rezervare.sala}</TableCell>
                                            <TableCell align="center">
                                                {!isPast ? (
                                                    <Button
                                                        variant="contained"
                                                        sx={{
                                                            backgroundColor: '#8B0000',
                                                            color: 'white',
                                                            '&:hover': { backgroundColor: '#600000' },
                                                        }}
                                                        onClick={() => handleDialogOpen(rezervare)}
                                                    >
                                                        Anulează Rezervarea
                                                    </Button>
                                                ) : (
                                                    <Typography variant="body2" color="text.secondary">
                                                        Expirată
                                                    </Typography>
                                                )}
                                            </TableCell>
                                        </TableRow>
                                    );
                                })}
                            </TableBody>
                        </Table>
                    </TableContainer>
                ) : (
                    <Typography variant="body1" color="text.secondary" sx={{ textAlign: 'center', marginTop: '20px' }}>
                        Nu aveți rezervări efectuate.
                    </Typography>
                )}
            </Box>

            <Dialog open={dialogOpen} onClose={handleDialogClose}>
                <DialogTitle sx={{ fontWeight: 'bold', color: '#8B0000' }}>Confirmare Anulare</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        Sunteți sigur că doriți să anulați rezervarea la spectacolul "{selectedRezervare?.spectacolTitlu}"?
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleDialogClose} sx={{ color: '#8B0000' }}>Anulează</Button>
                    <Button onClick={handleDelete} variant="contained" sx={{ backgroundColor: '#8B0000', color: 'white' }}>Da</Button>
                </DialogActions>
            </Dialog>
        </Box>
    );
};

export default RezervariPage;
