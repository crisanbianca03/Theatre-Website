import React, { useEffect, useState } from 'react';
import {
    AppBar,
    Toolbar,
    Typography,
    Box,
    Button,
    Grid,
    Card,
    CardContent,
    CardMedia,
    FormControl,
    InputLabel,
    MenuItem,
    Select,
} from '@mui/material';
import { Link } from 'react-router-dom';
import axios from 'axios';

const HomePage = () => {
    const [spectacole, setSpectacole] = useState([]);
    const [filteredSpectacole, setFilteredSpectacole] = useState([]);
    const [filters, setFilters] = useState({
        gen: '',
        titlu: '',
        data: '',
    });
    const [genuri, setGenuri] = useState([]);
    const [titluri, setTitluri] = useState([]);
    const [date, setDate] = useState([]);

    useEffect(() => {
        axios
            .get('/api/v1/spectacol/all')
            .then((response) => {
                const today = new Date();
                const spectacole = response.data.filter((spectacol) => {
                    const spectacolDate = new Date(spectacol.dataSpectacol);
                    return spectacolDate >= today;
                });

                setSpectacole(spectacole);
                setFilteredSpectacole(spectacole);

                const uniqueGenuri = [...new Set(spectacole.map((spectacol) => spectacol.gen))];
                const uniqueTitluri = [...new Set(spectacole.map((spectacol) => spectacol.titlu))];
                const uniqueDate = [
                    ...new Set(
                        spectacole.map((spectacol) =>
                            new Date(spectacol.dataSpectacol).toISOString().split('T')[0]
                        )
                    ),
                ];

                setGenuri(uniqueGenuri);
                setTitluri(uniqueTitluri);
                setDate(uniqueDate);
            })
            .catch((error) => console.error('Eroare la preluarea spectacolelor:', error));
    }, []);

    useEffect(() => {
        const filtered = spectacole.filter((spectacol) => {
            const matchGen = !filters.gen || spectacol.gen === filters.gen;
            const matchTitlu = !filters.titlu || spectacol.titlu === filters.titlu;
            const matchData =
                !filters.data ||
                new Date(spectacol.dataSpectacol).toISOString().split('T')[0] === filters.data;
            return matchGen && matchTitlu && matchData;
        });
        setFilteredSpectacole(filtered);
    }, [filters, spectacole]);

    const handleFilterChange = (e) => {
        const { name, value } = e.target;
        setFilters((prevFilters) => ({ ...prevFilters, [name]: value }));
    };

    return (
        <div>
            <AppBar position="static" sx={{ backgroundColor: '#8B0000', padding: '10px' }}>
                <Toolbar>
                    <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                        Teatru
                    </Typography>

                    <FormControl sx={{ minWidth: 150, marginRight: '10px' }} size="small">
                        <InputLabel id="gen-label">Gen</InputLabel>
                        <Select
                            labelId="gen-label"
                            name="gen"
                            value={filters.gen}
                            onChange={handleFilterChange}
                        >
                            <MenuItem value="">
                                <em>Toate</em>
                            </MenuItem>
                            {genuri.map((gen) => (
                                <MenuItem key={gen} value={gen}>
                                    {gen}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>


                    <FormControl sx={{ minWidth: 150, marginRight: '10px' }} size="small">
                        <InputLabel id="titlu-label">Titlu</InputLabel>
                        <Select
                            labelId="titlu-label"
                            name="titlu"
                            value={filters.titlu}
                            onChange={handleFilterChange}
                        >
                            <MenuItem value="">
                                <em>Toate</em>
                            </MenuItem>
                            {titluri.map((titlu) => (
                                <MenuItem key={titlu} value={titlu}>
                                    {titlu}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>

                    {/* Select pentru data */}
                    <FormControl sx={{ minWidth: 150, marginRight: '10px' }} size="small">
                        <InputLabel id="data-label">Data</InputLabel>
                        <Select
                            labelId="data-label"
                            name="data"
                            value={filters.data}
                            onChange={handleFilterChange}
                        >
                            <MenuItem value="">
                                <em>Toate</em>
                            </MenuItem>
                            {date.map((data) => (
                                <MenuItem key={data} value={data}>
                                    {data}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>

                    <Button color="inherit" component={Link} to="/login" sx={{ marginLeft: '10px' }}>
                        Logare
                    </Button>
                    <Button color="inherit" component={Link} to="/signup" sx={{ marginLeft: '10px' }}>
                        Creare Cont
                    </Button>
                </Toolbar>
            </AppBar>

            {/* Imaginea cu teatrul */}
            <Box sx={{ width: '100%', textAlign: 'center', margin: '20px 0' }}>
                <img
                    src="/images/teatru.jpg"
                    alt="Teatrul Național"
                    style={{ width: '100%', height: 'auto', borderRadius: '0' }}
                />
            </Box>

            <Box sx={{ padding: '20px' }}>
                <Typography variant="h4" component="h2" gutterBottom>
                    Spectacole
                </Typography>
                <Grid container spacing={3}>
                    {filteredSpectacole.length > 0 ? (
                        filteredSpectacole.map((spectacol) => (
                            <Grid item xs={12} sm={6} md={4} key={spectacol.idSpectacol}>
                                <Card>
                                    <CardMedia
                                        component="img"
                                        height="200"
                                        image={spectacol.imagine}
                                        alt={spectacol.titlu}
                                        onError={(e) => (e.target.src = '/images/default-spectacol.jpg')} // Imagine fallback
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
                                            Data: {new Date(spectacol.dataSpectacol).toLocaleDateString()} - Ora:{' '}
                                            {spectacol.ora ? spectacol.ora : 'Necunoscută'}
                                        </Typography>
                                        <Typography variant="body2" color="text.secondary">
                                            Durată: {spectacol.durata}
                                        </Typography>
                                        <Typography variant="body2" color="text.secondary">
                                            Nr. bilete disponibile: {spectacol.nrBilete}
                                        </Typography>
                                    </CardContent>
                                </Card>
                            </Grid>
                        ))
                    ) : (
                        <Typography variant="body1" color="text.secondary">
                            Nu există spectacole care să corespundă criteriilor de filtrare.
                        </Typography>
                    )}
                </Grid>
            </Box>
        </div>
    );
};

export default HomePage;
