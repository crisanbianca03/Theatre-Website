package com.example.teatru.service;

import com.example.teatru.repository.ClientRepository;
import com.example.teatru.model.Rezervare;
import com.example.teatru.model.Spectacol;
import com.example.teatru.repository.RezervareRepository;
import com.example.teatru.repository.SpectacolRepository;
import com.example.teatru.model.Utilizator;
import com.example.teatru.repository.UtilizatorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RezervareService {
    private final RezervareRepository rezervareRepository;
    private final SpectacolRepository spectacolRepository;
    private final UtilizatorRepository utilizatorRepository;

    public RezervareService(RezervareRepository rezervareRepository,
                            SpectacolRepository spectacolRepository,
                            ClientRepository clientRepository, UtilizatorRepository utilizatorRepository) {
        this.rezervareRepository = rezervareRepository;
        this.spectacolRepository = spectacolRepository;
        this.utilizatorRepository = utilizatorRepository;

    }

    public List<Rezervare> getRezervari() {
        return rezervareRepository.findAll();
    }

    public void addNewRezervare(Rezervare rezervare) {
        System.out.println("Rezervare primită: " + rezervare);
        System.out.println("Utilizator primit: " + rezervare.getUtilizator());
        System.out.println("Spectacol primit: " + rezervare.getSpectacol());

        if (rezervare.getSpectacol() == null || rezervare.getUtilizator() == null) {
            throw new IllegalStateException("Spectacol sau utilizator lipsă în cererea primită.");
        }


        Spectacol spectacol = spectacolRepository.findById(rezervare.getSpectacol().getIdSpectacol())
                .orElseThrow(() -> new IllegalStateException("Spectacol cu id " + rezervare.getSpectacol().getIdSpectacol() + " nu există"));

        if (spectacol.getNrBilete() < rezervare.getNrBilete()) {
            throw new IllegalStateException("Nu există suficiente bilete disponibile pentru acest spectacol.");
        }
        spectacol.setNrBilete(spectacol.getNrBilete() - rezervare.getNrBilete());

        Utilizator utilizator = utilizatorRepository.findById(rezervare.getUtilizator().getIdUtilizator())
                .orElseThrow(() -> new IllegalStateException("Utilizator cu id " + rezervare.getUtilizator().getIdUtilizator() + " nu există"));

        rezervare.setSpectacol(spectacol);
        rezervare.setUtilizator(utilizator);
        rezervare.setDataRezervarii(LocalDateTime.now());

        rezervareRepository.save(rezervare);
    }



    public void deleteRezervare(Integer rezervareId) {
        System.out.println("Verificăm existența rezervării cu ID: " + rezervareId);

        Rezervare rezervare = rezervareRepository.findById(rezervareId)
                .orElseThrow(() -> new IllegalStateException("Rezervare cu ID " + rezervareId + " nu există"));

        Spectacol spectacol = rezervare.getSpectacol();

        spectacol.setNrBilete(spectacol.getNrBilete() + rezervare.getNrBilete());

        System.out.println("Rezervarea există. Ștergem din baza de date.");
        rezervareRepository.deleteById(rezervareId);
        System.out.println("Rezervarea cu ID " + rezervareId + " a fost ștearsă cu succes.");
    }


    public List<Map<String, Object>> getRezervariByUtilizator(int idUtilizator) {
        List<Rezervare> rezervari = rezervareRepository.findByUtilizatorIdUtilizator(idUtilizator);

        return rezervari.stream()
                .map(rezervare -> {
                    Map<String, Object> rezervareMap = new HashMap<>();
                    rezervareMap.put("idRezervare", rezervare.getIdRezervare());
                    rezervareMap.put("spectacolTitlu", rezervare.getSpectacol().getTitlu());
                    rezervareMap.put("dataSpectacol", rezervare.getSpectacol().getDataSpectacol());
                    rezervareMap.put("oraSpectacol", rezervare.getSpectacol().getOra());
                    rezervareMap.put("nrBilete", rezervare.getNrBilete());
                    rezervareMap.put("sala", rezervare.getSpectacol().getSala().getIdSala());
                    return rezervareMap;
                })
                .toList();
    }

    public List<Map<String, Object>> getReservationsWithDetails() {
        List<Rezervare> rezervari = rezervareRepository.findAll();

        return rezervari.stream()
                .map(rezervare -> {
                    Map<String, Object> rezervareMap = new HashMap<>();
                    rezervareMap.put("idRezervare", rezervare.getIdRezervare());
                    rezervareMap.put("numeUtilizator", rezervare.getUtilizator().getNume());
                    rezervareMap.put("prenumeUtilizator", rezervare.getUtilizator().getPrenume());
                    rezervareMap.put("emailUtilizator", rezervare.getUtilizator().getEmail());
                    rezervareMap.put("numeSpectacol", rezervare.getSpectacol().getTitlu());
                    rezervareMap.put("dataSpectacol", rezervare.getSpectacol().getDataSpectacol());
                    rezervareMap.put("oraSpectacol", rezervare.getSpectacol().getOra());
                    rezervareMap.put("nrBilete", rezervare.getNrBilete());
                    rezervareMap.put("dataRezervarii", rezervare.getDataRezervarii());
                    return rezervareMap;
                })
                .toList();
    }
    public Map<String, Object> generateRaport(String criteriu) {
        Map<String, Object> raport = new HashMap<>();
        List<Rezervare> rezervari = rezervareRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        switch (criteriu) {
            case "total-bilete-spectacol":
                Map<String, Long> biletePerSpectacol = rezervari.stream()
                        .collect(Collectors.groupingBy(
                                rezervare -> rezervare.getSpectacol().getTitlu(),
                                Collectors.summingLong(Rezervare::getNrBilete)
                        ));
                raport.put("criteriu", "Numărul total de bilete rezervate pentru fiecare spectacol");
                raport.put("rezultate", biletePerSpectacol);
                break;

            case "bilete-saptamana":

                Map<String, Long> biletePerSpectacolSaptamana = rezervari.stream()
                        .filter(rezervare -> rezervare.getDataRezervarii().isAfter(now.minusWeeks(1)))
                        .collect(Collectors.groupingBy(
                                rezervare -> rezervare.getSpectacol().getTitlu(),
                                Collectors.summingLong(Rezervare::getNrBilete)
                        ));
                raport.put("criteriu", "Numărul total de bilete rezervate în ultima săptămână per spectacol");
                raport.put("rezultate", biletePerSpectacolSaptamana);
                break;

            case "bilete-luna":
                Map<String, Long> biletePerSpectacolLuna = rezervari.stream()
                        .filter(rezervare -> rezervare.getDataRezervarii().isAfter(now.minusMonths(1)))
                        .collect(Collectors.groupingBy(
                                rezervare -> rezervare.getSpectacol().getTitlu(),
                                Collectors.summingLong(Rezervare::getNrBilete)
                        ));
                raport.put("criteriu", "Numărul total de bilete rezervate în ultima lună");
                raport.put("rezultate", biletePerSpectacolLuna);
                break;

            default:
                throw new IllegalArgumentException("Criteriul selectat este invalid.");
        }

        return raport;
    }

    public Map<String, Long> generateStatistici(String criteriu) {
        List<Rezervare> rezervari = rezervareRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        switch (criteriu) {
            case "populare-total":
                return rezervari.stream()
                        .collect(Collectors.groupingBy(
                                rezervare -> rezervare.getSpectacol().getTitlu(),
                                Collectors.summingLong(Rezervare::getNrBilete)
                        ));
            case "populare-luna":
                return rezervari.stream()
                        .filter(rezervare -> rezervare.getDataRezervarii().isAfter(now.minusMonths(1)))
                        .collect(Collectors.groupingBy(
                                rezervare -> rezervare.getSpectacol().getTitlu(),
                                Collectors.summingLong(Rezervare::getNrBilete)
                        ));
            case "populare-saptamana":
                return rezervari.stream()
                        .filter(rezervare -> rezervare.getDataRezervarii().isAfter(now.minusWeeks(1)))
                        .collect(Collectors.groupingBy(
                                rezervare -> rezervare.getSpectacol().getTitlu(),
                                Collectors.summingLong(Rezervare::getNrBilete)
                        ));
            default:
                throw new IllegalArgumentException("Criteriul selectat este invalid.");
        }
    }





}
