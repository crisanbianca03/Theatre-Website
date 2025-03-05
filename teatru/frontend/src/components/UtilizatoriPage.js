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
    Select,
    MenuItem,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
} from '@mui/material';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const UtilizatoriPage = () => {
    const [utilizatori, setUtilizatori] = useState([]);
    const [openDialog, setOpenDialog] = useState(false);
    const [utilizatorDeSters, setUtilizatorDeSters] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        axios.get('/api/v1/utilizator/non-admins')
            .then(response => {
                setUtilizatori(response.data);
            })
            .catch(error => console.error('Eroare la preluarea utilizatorilor:', error));
    }, []);

    const handleBack = () => {
        navigate('/admin');
    };

    const handleChangeTip = (idUtilizator, newTip) => {
        setUtilizatori(prevUtilizatori =>
            prevUtilizatori.map(utilizator =>
                utilizator.idUtilizator === idUtilizator
                    ? { ...utilizator, tip: newTip }
                    : utilizator
            )
        );
    };

    const handleSaveTip = (idUtilizator, newTip) => {
        axios.put(`/api/v1/utilizator/${idUtilizator}/update-tip`, null, {
            params: { tip: newTip },
        })
            .then(() => {
                alert('Tipul utilizatorului a fost actualizat cu succes!');
            })
            .catch(error => {
                console.error('Eroare la actualizarea tipului utilizatorului:', error);
                alert('A apărut o eroare la actualizarea tipului utilizatorului.');
            });
    };

    const handleOpenDeleteDialog = (idUtilizator) => {
        setUtilizatorDeSters(idUtilizator);
        setOpenDialog(true);
    };

    const handleCloseDeleteDialog = () => {
        setUtilizatorDeSters(null);
        setOpenDialog(false);
    };

    const handleDeleteUtilizator = () => {
        if (utilizatorDeSters) {
            axios.delete(`/api/v1/utilizator/${utilizatorDeSters}`)
                .then(() => {
                    setUtilizatori(prevUtilizatori =>
                        prevUtilizatori.filter(utilizator => utilizator.idUtilizator !== utilizatorDeSters)
                    );
                    alert('Utilizatorul a fost șters cu succes!');
                })
                .catch(error => {
                    console.error('Eroare la ștergerea utilizatorului:', error);
                    alert('A apărut o eroare la ștergerea utilizatorului.');
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
                        Utilizatori
                    </Typography>
                    <Button color="inherit" onClick={handleBack}>
                        Înapoi
                    </Button>
                </Toolbar>
            </AppBar>

            <Box sx={{ padding: '20px' }}>
                <Typography variant="h4" component="h2" gutterBottom>
                    Lista Utilizatorilor
                </Typography>
                <TableContainer component={Paper}>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell><strong>ID</strong></TableCell>
                                <TableCell><strong>Nume</strong></TableCell>
                                <TableCell><strong>Prenume</strong></TableCell>
                                <TableCell><strong>Email</strong></TableCell>
                                <TableCell><strong>Tip</strong></TableCell>
                                <TableCell><strong>Acțiuni</strong></TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {utilizatori.length > 0 ? (
                                utilizatori.map(utilizator => (
                                    <TableRow key={utilizator.idUtilizator}>
                                        <TableCell>{utilizator.idUtilizator}</TableCell>
                                        <TableCell>{utilizator.nume}</TableCell>
                                        <TableCell>{utilizator.prenume}</TableCell>
                                        <TableCell>{utilizator.email}</TableCell>
                                        <TableCell>
                                            <Select
                                                value={utilizator.tip}
                                                onChange={(e) =>
                                                    handleChangeTip(utilizator.idUtilizator, e.target.value)
                                                }
                                                fullWidth
                                            >
                                                <MenuItem value="client">Client</MenuItem>
                                                <MenuItem value="angajat">Angajat</MenuItem>
                                            </Select>
                                        </TableCell>
                                        <TableCell>
                                            <Button
                                                variant="contained"
                                                color="primary"
                                                onClick={() =>
                                                    handleSaveTip(utilizator.idUtilizator, utilizator.tip)
                                                }
                                            >
                                                Salvează
                                            </Button>
                                            <Button
                                                variant="contained"
                                                color="secondary"
                                                onClick={() => handleOpenDeleteDialog(utilizator.idUtilizator)}
                                                sx={{ marginLeft: '10px' }}
                                            >
                                                Șterge
                                            </Button>
                                        </TableCell>
                                    </TableRow>
                                ))
                            ) : (
                                <TableRow>
                                    <TableCell colSpan={6} align="center">
                                        Nu există utilizatori disponibili.
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
                        Sigur doriți să ștergeți acest utilizator? Această acțiune este ireversibilă.
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCloseDeleteDialog} color="secondary">
                        Anulează
                    </Button>
                    <Button onClick={handleDeleteUtilizator} color="primary" autoFocus>
                        Da
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );
};

export default UtilizatoriPage;
