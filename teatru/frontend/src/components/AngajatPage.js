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
    TextField,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
} from '@mui/material';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const AngajatPage = () => {
    const [spectacole, setSpectacole] = useState([]);
    const [openDialog, setOpenDialog] = useState(false);
    const [spectacolDeSters, setSpectacolDeSters] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {

        axios.get('/api/v1/spectacol/all')
            .then(response => {
                setSpectacole(response.data);
            })
            .catch(error => console.error('Eroare la preluarea spectacolelor:', error));
    }, []);

    const handleAddSpectacol = () => {
        navigate('/adaugare-spectacol');
    };

    const handleViewRezervari = () => {
        navigate('/rezervari-angajat');
    };

    const handleLogout = () => {
        localStorage.removeItem('loggedInUser');
        navigate('/');
    };


    const handleBileteChange = (idSpectacol, newNrBilete) => {
        setSpectacole(prevSpectacole =>
            prevSpectacole.map(spectacol =>
                spectacol.idSpectacol === idSpectacol
                    ? { ...spectacol, nrBilete: newNrBilete }
                    : spectacol
            )
        );
    };


    const handleSaveSpectacol = (idSpectacol) => {
        const spectacolDeSalvat = spectacole.find(spectacol => spectacol.idSpectacol === idSpectacol);

        axios.put(`/api/v1/spectacol/${idSpectacol}/update-bilete`, null, {
            params: { nrBilete: spectacolDeSalvat.nrBilete },
        })
            .then(() => {
                alert('Numărul de bilete a fost actualizat cu succes!');
            })
            .catch(error => {
                console.error('Eroare la actualizarea numărului de bilete:', error);
                alert('A apărut o eroare la actualizarea numărului de bilete.');
            });
    };
    const handleOpenDeleteDialog = (idSpectacol) => {
        setSpectacolDeSters(idSpectacol);
        setOpenDialog(true);
    };


    const handleCloseDeleteDialog = () => {
        setSpectacolDeSters(null);
        setOpenDialog(false);
    };

    const handleDeleteSpectacol = () => {
        if (spectacolDeSters) {
            axios.delete(`/api/v1/spectacol/${spectacolDeSters}`)
                .then(() => {
                    setSpectacole(prevSpectacole =>
                        prevSpectacole.filter(spectacol => spectacol.idSpectacol !== spectacolDeSters)
                    );
                    alert('Spectacolul a fost șters cu succes!');
                })
                .catch(error => {
                    console.error('Eroare la ștergerea spectacolului:', error);
                    alert('A apărut o eroare la ștergerea spectacolului.');
                })
                .finally(() => {
                    handleCloseDeleteDialog();
                });
        }
    };

    return (
        <div style={{ backgroundColor: '#FFC0CB', minHeight: '100vh', padding: '0' }}>
            {/* Bara de navigare */}
            <AppBar position="static" sx={{ backgroundColor: '#8B0000' }}>
                <Toolbar>
                    <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                        Administrează Spectacole
                    </Typography>
                    <Button color="inherit" onClick={handleAddSpectacol}>
                        Adăugare Spectacole
                    </Button>
                    <Button color="inherit" onClick={handleViewRezervari}>
                        Rezervări
                    </Button>
                    <Button color="inherit" onClick={handleLogout}>
                        Delogare
                    </Button>
                </Toolbar>
            </AppBar>


            <Box sx={{ padding: '20px' }}>
                <Typography variant="h4" component="h2" gutterBottom>
                    Lista Spectacolelor
                </Typography>
                <TableContainer component={Paper}>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell><strong>ID</strong></TableCell>
                                <TableCell><strong>Titlu</strong></TableCell>
                                <TableCell><strong>Descriere</strong></TableCell>
                                <TableCell><strong>Gen</strong></TableCell>
                                <TableCell><strong>Data</strong></TableCell>
                                <TableCell><strong>Nr. Bilete</strong></TableCell>
                                <TableCell><strong>Durată</strong></TableCell>
                                <TableCell><strong>Acțiuni</strong></TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {spectacole.length > 0 ? (
                                spectacole.map(spectacol => (
                                    <TableRow key={spectacol.idSpectacol}>
                                        <TableCell>{spectacol.idSpectacol}</TableCell>
                                        <TableCell>{spectacol.titlu}</TableCell>
                                        <TableCell>{spectacol.descriere}</TableCell>
                                        <TableCell>{spectacol.gen}</TableCell>
                                        <TableCell>{new Date(spectacol.dataSpectacol).toLocaleDateString()}</TableCell>
                                        <TableCell>
                                            <TextField
                                                type="number"
                                                value={spectacol.nrBilete}
                                                onChange={(e) =>
                                                    handleBileteChange(spectacol.idSpectacol, parseInt(e.target.value, 10))
                                                }
                                                fullWidth
                                            />
                                        </TableCell>
                                        <TableCell>{spectacol.durata}</TableCell>
                                        <TableCell>
                                            <Button
                                                variant="contained"
                                                color="primary"
                                                onClick={() => handleSaveSpectacol(spectacol.idSpectacol)}
                                            >
                                                Salvare
                                            </Button>
                                            <Button
                                                variant="contained"
                                                color="secondary"
                                                onClick={() => handleOpenDeleteDialog(spectacol.idSpectacol)}
                                                sx={{ marginLeft: '10px' }}
                                            >
                                                Ștergere
                                            </Button>
                                        </TableCell>
                                    </TableRow>
                                ))
                            ) : (
                                <TableRow>
                                    <TableCell colSpan={8} align="center">
                                        Nu există spectacole disponibile.
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
                        Sigur doriți să ștergeți acest spectacol? Această acțiune este ireversibilă.
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCloseDeleteDialog} color="secondary">
                        Anulează
                    </Button>
                    <Button onClick={handleDeleteSpectacol} color="primary" autoFocus>
                        Da
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );
};

export default AngajatPage;
