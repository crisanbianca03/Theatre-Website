package com.example.teatru.service;

import com.example.teatru.model.Actor;
import com.example.teatru.repository.ActorRepository;
import com.example.teatru.model.Sala;
import com.example.teatru.model.Spectacol;
import com.example.teatru.repository.SalaRepository;
import com.example.teatru.model.SpectacolActor;
import com.example.teatru.repository.SpectacolRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SpectacolService {
    private final SpectacolRepository spectacolRepository;
    private final SalaRepository salaRepository;
    private final ActorRepository actorRepository;

    public SpectacolService(SpectacolRepository spectacolRepository, SalaRepository salaRepository, ActorRepository actorRepository) {
        this.spectacolRepository = spectacolRepository;
        this.salaRepository = salaRepository;
        this.actorRepository = actorRepository;
    }

    public List<Spectacol> getAllSpectacles() {
        return spectacolRepository.findAll();
    }

    public void addNewSpectacol(Spectacol spectacol) {
        Sala sala = salaRepository.findById(spectacol.getSala().getIdSala())
                .orElseThrow(() -> new IllegalStateException("Sala with id " + spectacol.getSala().getIdSala() + " does not exist"));
        spectacol.setSala(sala);
        spectacolRepository.save(spectacol);
    }

    public void deleteSpectacol(Integer spectacolId) {
        boolean exists = spectacolRepository.existsById(spectacolId);
        if (!exists) {
            throw new IllegalStateException("Spectacol with id " + spectacolId + " does not exist");
        }
        spectacolRepository.deleteById(spectacolId);
    }
    public List<Spectacol> getSpectaclesWithActors() {
        return spectacolRepository.findAll();
    }

    public List<Map<String, Object>> getFutureSpectaclesWithActors() {
        LocalDateTime now = LocalDateTime.now();

        return spectacolRepository.findAll().stream()
                .filter(spectacol -> spectacol.getDataSpectacol().isAfter(now))
                .map(spectacol -> Map.of(
                        "idSpectacol", spectacol.getIdSpectacol(),
                        "titlu", spectacol.getTitlu(),
                        "descriere", spectacol.getDescriere(),
                        "gen", spectacol.getGen(),
                        "nrBilete", spectacol.getNrBilete(),
                        "durata", spectacol.getDurata(),
                        "imagine", spectacol.getImagine(),
                        "dataSpectacol", spectacol.getDataSpectacol(),
                        "actori", spectacol.getSpectacolActors().stream()
                                .map(sa -> sa.getActor().getNume() + " " + sa.getActor().getPrenume())
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }
    public void updateNrBilete(Integer idSpectacol, Integer nrBilete) {
        Spectacol spectacol = spectacolRepository.findById(idSpectacol)
                .orElseThrow(() -> new IllegalStateException("Spectacol with id " + idSpectacol + " does not exist"));

        spectacol.setNrBilete(nrBilete);
        spectacolRepository.save(spectacol);
    }

    public void addNewSpectacol(Spectacol spectacol, MultipartFile imagine, List<String> actorii) {

        Sala sala = salaRepository.findById(spectacol.getSala().getIdSala())
                .orElseThrow(() -> new IllegalStateException("Sala cu id " + spectacol.getSala().getIdSala() + " nu există"));


        String imaginePath = null;
        if (imagine != null && !imagine.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "_" + imagine.getOriginalFilename();
                Path filePath = Paths.get("frontend/public/images/" + fileName);
                Files.createDirectories(filePath.getParent());
                Files.copy(imagine.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                imaginePath = "/images/" + fileName;
            } catch (IOException e) {
                throw new RuntimeException("Eroare la salvarea imaginii", e);
            }
        }

        spectacol.setSala(sala);
        spectacol.setImagine(imaginePath);

        List<SpectacolActor> spectacolActors = actorii.stream()
                .map(numeComplet -> {
                    String[] numePrenume = numeComplet.split("\\s+");
                    if (numePrenume.length < 2) {
                        throw new IllegalArgumentException("Numele complet al actorului trebuie să conțină prenume și nume: " + numeComplet);
                    }
                    String nume = numePrenume[0];
                    String prenume = numePrenume[1];


                    Actor actor = actorRepository.findByNumeAndPrenume(nume, prenume)
                            .orElseGet(() -> {

                                Actor newActor = new Actor();
                                newActor.setNume(nume);
                                newActor.setPrenume(prenume);
                                return actorRepository.save(newActor);
                            });

                    SpectacolActor spectacolActor = new SpectacolActor();
                    spectacolActor.setSpectacol(spectacol);
                    spectacolActor.setActor(actor);
                    return spectacolActor;
                })
                .collect(Collectors.toList());

        spectacol.setSpectacolActors(spectacolActors);


        spectacolRepository.save(spectacol);
    }





}
