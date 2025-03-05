import React, { useState } from 'react';
import {
    AppBar,
    Toolbar,
    Typography,
    Button,
    Box,
    Stack,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    Select,
    MenuItem,
    FormControl,
    InputLabel,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Paper,
} from '@mui/material';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const AdminPage = () => {
    const [openDialog, setOpenDialog] = useState(false);
    const [openStatDialog, setOpenStatDialog] = useState(false);
    const [criteriu, setCriteriu] = useState('');
    const [statCriteriu, setStatCriteriu] = useState('');
    const [reportData, setReportData] = useState(null);
    const [statData, setStatData] = useState(null);
    const navigate = useNavigate();

    const handleLogout = () => {
        localStorage.removeItem('loggedInUser');
        navigate('/');
    };

    const handleNavigate = (path) => {
        if (path === '/rapoarte') {
            setOpenDialog(true);
        } else if (path === '/statistici') {
            setOpenStatDialog(true);
        } else {
            navigate(path);
        }
    };

    const handleCloseDialog = () => {
        setOpenDialog(false);
        setCriteriu('');
    };

    const handleCloseStatDialog = () => {
        setOpenStatDialog(false);
        setStatCriteriu('');
    };

    const handleGenerateReport = () => {
        axios
            .get(`/api/v1/rezervare/rapoarte`, { params: { criteriu } })
            .then((response) => {
                setReportData(response.data);
            })
            .catch((error) => {
                console.error('Eroare la generarea raportului:', error);
                alert('A apărut o eroare la generarea raportului.');
            })
            .finally(() => {
                handleCloseDialog();
            });
    };

    const handleGenerateStatistics = () => {
        axios
            .get(`/api/v1/rezervare/statistici`, { params: { criteriu: statCriteriu } })
            .then((response) => {
                setStatData(response.data);
            })
            .catch((error) => {
                console.error('Eroare la generarea statisticii:', error);
                alert('A apărut o eroare la generarea statisticii.');
            })
            .finally(() => {
                handleCloseStatDialog();
            });
    };

    const renderBarChart = (data) => {
        const maxValue = Math.max(...Object.values(data));
        return (
            <Box
                sx={{
                    padding: '20px',
                    backgroundColor: '#FFFFFF',
                    border: '2px solid black',
                    borderRadius: '8px',
                    width: '80%',
                    margin: '0 auto',
                }}
            >
                <Typography variant="h6" align="center" gutterBottom>
                    Statistici
                </Typography>
                <Box
                    sx={{
                        display: 'flex',
                        alignItems: 'flex-end',
                        justifyContent: 'space-evenly',
                        height: '300px',
                        margin: '20px 0',
                        position: 'relative',
                        borderLeft: '2px solid black',
                        borderBottom: '2px solid black',
                    }}
                >
                    {Object.entries(data).map(([spectacol, rezervari], index) => (
                        <Box
                            key={spectacol}
                            sx={{
                                width: '80px',
                                margin: '0 10px',
                                backgroundColor: `hsl(${(index / Object.keys(data).length) * 360}, 70%, 50%)`,
                                height: `${(rezervari / maxValue) * 100}%`,
                                display: 'flex',
                                alignItems: 'flex-end',
                                justifyContent: 'center',
                                position: 'relative',
                            }}
                        >
                            <Typography
                                sx={{
                                    position: 'absolute',
                                    top: '-20px',
                                    fontSize: '14px',
                                    fontWeight: 'bold',
                                    color: 'black',
                                }}
                            >
                                {rezervari}
                            </Typography>
                        </Box>
                    ))}
                </Box>
                <Box
                    sx={{
                        display: 'flex',
                        justifyContent: 'space-evenly',
                        alignItems: 'center',
                        marginTop: '10px',
                    }}
                >
                    {Object.keys(data).map((spectacol, index) => (
                        <Typography
                            key={index}
                            sx={{
                                fontSize: '12px',
                                textAlign: 'center',
                                maxWidth: '80px',
                            }}
                        >
                            {spectacol}
                        </Typography>
                    ))}
                </Box>
            </Box>
        );
    };

    return (
        <div style={{ backgroundColor: '#FFC0CB', minHeight: '100vh', padding: '0' }}>
            {/* Navigation Bar */}
            <AppBar position="static" sx={{ backgroundColor: '#8B0000' }}>
                <Toolbar>
                    <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                        Admin Dashboard
                    </Typography>
                    <Button color="inherit" onClick={handleLogout}>
                        Delogare
                    </Button>
                </Toolbar>
            </AppBar>

            <Box
                sx={{
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center',
                    minHeight: '60vh',
                    gap: '40px',
                }}
            >
                <Button
                    variant="contained"
                    sx={{
                        backgroundColor: '#8B0000',
                        color: 'white',
                        fontSize: '20px',
                        padding: '20px 60px',
                        '&:hover': {
                            backgroundColor: '#6A0000',
                        },
                    }}
                    onClick={() => handleNavigate('/utilizatori')}
                >
                    Utilizatori
                </Button>
                <Button
                    variant="contained"
                    sx={{
                        backgroundColor: '#8B0000',
                        color: 'white',
                        fontSize: '20px',
                        padding: '20px 60px',
                        '&:hover': {
                            backgroundColor: '#6A0000',
                        },
                    }}
                    onClick={() => handleNavigate('/statistici')}
                >
                    Statistici
                </Button>
                <Button
                    variant="contained"
                    sx={{
                        backgroundColor: '#8B0000',
                        color: 'white',
                        fontSize: '20px',
                        padding: '20px 60px',
                        '&:hover': {
                            backgroundColor: '#6A0000',
                        },
                    }}
                    onClick={() => handleNavigate('/rapoarte')}
                >
                    Rapoarte
                </Button>
            </Box>


            <Dialog open={openDialog} onClose={handleCloseDialog}>
                <DialogTitle>Generare Raport</DialogTitle>
                <DialogContent>
                    <FormControl fullWidth>
                        <InputLabel id="criteriu-label">Selectați Criteriul</InputLabel>
                        <Select
                            labelId="criteriu-label"
                            value={criteriu}
                            onChange={(e) => setCriteriu(e.target.value)}
                        >
                            <MenuItem value="total-bilete-spectacol">
                                Numărul total de bilete rezervate pentru fiecare spectacol
                            </MenuItem>
                            <MenuItem value="bilete-saptamana">
                                Numărul total de bilete rezervate în ultima săptămână per spectacol
                            </MenuItem>
                            <MenuItem value="bilete-luna">
                                Numărul total de bilete rezervate în ultima lună
                            </MenuItem>
                        </Select>
                    </FormControl>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCloseDialog} color="secondary">
                        Anulează
                    </Button>
                    <Button onClick={handleGenerateReport} color="primary" disabled={!criteriu}>
                        Generează
                    </Button>
                </DialogActions>
            </Dialog>

            {/* Dialog for Statistics */}
            <Dialog open={openStatDialog} onClose={handleCloseStatDialog}>
                <DialogTitle>Generare Statistici</DialogTitle>
                <DialogContent>
                    <FormControl fullWidth>
                        <InputLabel id="stat-criteriu-label">Selectați Criteriul</InputLabel>
                        <Select
                            labelId="stat-criteriu-label"
                            value={statCriteriu}
                            onChange={(e) => setStatCriteriu(e.target.value)}
                        >
                            <MenuItem value="populare-total">
                                Cele mai populare spectacole după rezervări totale
                            </MenuItem>
                            <MenuItem value="populare-luna">
                                Cele mai populare spectacole în ultima lună
                            </MenuItem>
                            <MenuItem value="populare-saptamana">
                                Cele mai populare spectacole în ultima săptămână
                            </MenuItem>
                        </Select>
                    </FormControl>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCloseStatDialog} color="secondary">
                        Anulează
                    </Button>
                    <Button onClick={handleGenerateStatistics} color="primary" disabled={!statCriteriu}>
                        Generează
                    </Button>
                </DialogActions>
            </Dialog>

            {reportData && (
                <Box sx={{ padding: '20px' }}>
                    <Typography variant="h5" component="h2" gutterBottom>
                        Raport: {reportData.criteriu}
                    </Typography>
                    <TableContainer component={Paper}>
                        <Table>
                            <TableHead>
                                <TableRow>
                                    <TableCell><strong>Spectacol</strong></TableCell>
                                    <TableCell><strong>Bilete Rezervate</strong></TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {Object.entries(reportData.rezultate).map(([spectacol, bilete]) => (
                                    <TableRow key={spectacol}>
                                        <TableCell>{spectacol}</TableCell>
                                        <TableCell>{bilete}</TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                </Box>
            )}

            {statData && renderBarChart(statData)}
        </div>
    );
};

export default AdminPage;
