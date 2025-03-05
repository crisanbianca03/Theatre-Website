import React, { useEffect, useState } from 'react';
import {
    AppBar,
    Toolbar,
    Typography,
    Button,
    Box,
    Card,
    CardContent,
    CardMedia,
    Grid,
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    TextField,
} from '@mui/material';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const ClientPage = () => {
    const [spectacole, setSpectacole] = useState([]);
    const [user, setUser] = useState(null);
    const [selectedSpectacol, setSelectedSpectacol] = useState(null);
    const [dialogOpen, setDialogOpen] = useState(false);
    const [nrBilete, setNrBilete] = useState(1);
    const navigate = useNavigate();

    useEffect(() => {
        const loggedInUser = JSON.parse(localStorage.getItem('loggedInUser'));
        if (loggedInUser) {
            setUser(loggedInUser);
        }

        axios.get('/api/v1/spectacol/future-with-actors')
            .then(response => {
                setSpectacole(response.data);
            })
            .catch(error => console.error('Eroare la preluarea spectacolelor:', error));
    }, []);

    const handleLogout = () => {
        localStorage.removeItem('loggedInUser');
        navigate('/');
    };

    const handleViewRezervari = () => {
        navigate('/rezervari');
    };


    const handleOpenDialog = (spectacol) => {
        setSelectedSpectacol(spectacol);
        setDialogOpen(true);
    };


    const handleCloseDialog = () => {
        setDialogOpen(false);
        setSelectedSpectacol(null);
        setNrBilete(1);
    };


    const handleFinalizeReservation = () => {
        if (!user || !selectedSpectacol) {
            alert("Utilizatorul sau spectacolul nu este valid.");
            return;
        }
        console.log("Spectacol selectat:", selectedSpectacol);


        const rezervare = {
            utilizator: { idUtilizator: user.idUtilizator },
            spectacol: { idSpectacol: selectedSpectacol.idSpectacol },
            nrBilete: nrBilete,
        };
        console.log("Rezervare trimisă către server:", JSON.stringify(rezervare, null, 2));

        console.log("Utilizator logat:", user);
        console.log("Spectacol selectat:", selectedSpectacol);


        console.log("Rezervare trimisă către server:", JSON.stringify(rezervare, null, 2));

        axios.post('/api/v1/rezervare', rezervare)
            .then(() => {
                alert('Rezervarea a fost efectuată cu succes!');
                handleCloseDialog();
            })
            .catch(error => {
                console.error('Eroare la realizarea rezervării:', error);
                alert('A apărut o eroare la realizarea rezervării.');
            });
    };


    return (
        <div>
            <AppBar position="static" sx={{ backgroundColor: '#8B0000' }}>
                <Toolbar>
                    <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                        Bine ai venit, {user ? `${user.nume} ${user.prenume}` : 'Utilizator'}!
                    </Typography>
                    <Button color="inherit" onClick={handleViewRezervari}>Rezervările Mele</Button>
                    <Button color="inherit" onClick={handleLogout}>Delogare</Button>
                </Toolbar>
            </AppBar>

            <Box sx={{ padding: '20px' }}>
                <Typography variant="h4" component="h2" gutterBottom>
                    Toate spectacolele disponibile
                </Typography>
                <Grid container spacing={3}>
                    {spectacole.length > 0 ? (
                        spectacole.map(spectacol => (
                            <Grid item xs={12} sm={6} md={4} key={spectacol.idSpectacol}>
                                <Card>
                                    <CardMedia
                                        component="img"
                                        height="200"
                                        image={spectacol.imagine}
                                        alt={spectacol.titlu}
                                        onError={(e) => e.target.src = '/images/default-spectacol.jpg'}
                                    />
                                    <CardContent>
                                        <Typography variant="h5" component="div">
                                            {spectacol.titlu}
                                        </Typography>
                                        <Typography variant="body2" color="text.secondary">
                                            {spectacol.descriere}
                                        </Typography>
                                        <Typography variant="body2" color="text.secondary">
                                            Gen: {spectacol.gen}
                                        </Typography>
                                        <Typography variant="body2" color="text.secondary">
                                            Data: {new Date(spectacol.dataSpectacol).toLocaleDateString()} - Ora: {spectacol.ora ? spectacol.ora : 'Necunoscută'}
                                        </Typography>
                                        <Typography variant="body2" color="text.secondary">
                                            Durată: {spectacol.durata}
                                        </Typography>
                                        <Typography variant="body2" color="text.secondary">
                                            Nr. bilete disponibile: {spectacol.nrBilete}
                                        </Typography>
                                        <Typography variant="body2" color="text.secondary">
                                            Distributie: {spectacol.actori.join(', ')}
                                        </Typography>
                                        <Button
                                            variant="contained"
                                            color="primary"
                                            sx={{ marginTop: '10px' }}
                                            onClick={() => handleOpenDialog(spectacol)}
                                        >
                                            Rezervă Bilet
                                        </Button>
                                    </CardContent>
                                </Card>
                            </Grid>
                        ))
                    ) : (
                        <Typography variant="body1" color="text.secondary">
                            Nu există spectacole disponibile.
                        </Typography>
                    )}
                </Grid>
            </Box>

            <Dialog open={dialogOpen} onClose={handleCloseDialog}>
                <DialogTitle>Rezervă Bilet</DialogTitle>
                <DialogContent>
                    <Typography variant="body1">Spectacol: {selectedSpectacol?.titlu}</Typography>
                    <Typography variant="body2" color="text.secondary">
                        Data: {selectedSpectacol ? new Date(selectedSpectacol.dataSpectacol).toLocaleDateString() : ''}
                    </Typography>
                    <TextField
                        type="number"
                        label="Număr bilete"
                        value={nrBilete}
                        onChange={(e) => setNrBilete(Math.max(1, Math.min(selectedSpectacol.nrBilete, parseInt(e.target.value, 10))))}
                        fullWidth
                        sx={{ marginTop: '10px' }}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCloseDialog} color="secondary">Anulează</Button>
                    <Button onClick={handleFinalizeReservation} variant="contained" color="primary">Finalizare</Button>
                </DialogActions>
            </Dialog>
        </div>
    );
};

export default ClientPage;
