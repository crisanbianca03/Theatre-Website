package com.example.teatru.controller;

import com.example.teatru.model.Sala;
import com.example.teatru.model.Spectacol;
import com.example.teatru.service.SpectacolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/spectacol")
public class SpectacolController {
    private final SpectacolService spectacolService;

    @Autowired
    public SpectacolController(SpectacolService spectacolService) {
        this.spectacolService = spectacolService;
    }

    @GetMapping("/all")
    public List<Spectacol> getAllSpectacles() {
        return spectacolService.getAllSpectacles();
    }

    @GetMapping("/with-actors")
    public List<Spectacol> getSpectaclesWithActors() {
        return spectacolService.getSpectaclesWithActors();
    }

    @PostMapping
    public void registerNewSpectacol(@RequestBody Spectacol spectacol) {
        spectacolService.addNewSpectacol(spectacol);
    }

    @DeleteMapping(path = "{spectacolId}")
    public void deleteSpectacol(@PathVariable("spectacolId") Integer spectacolId) {
        spectacolService.deleteSpectacol(spectacolId);
    }

    @GetMapping("/future-with-actors")
    public List<Map<String, Object>> getFutureSpectaclesWithActors() {
        return spectacolService.getFutureSpectaclesWithActors();
    }
    @PutMapping("/{id}/update-bilete")
    public void updateNrBilete(@PathVariable("id") Integer idSpectacol, @RequestParam("nrBilete") Integer nrBilete) {
        spectacolService.updateNrBilete(idSpectacol, nrBilete);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> registerNewSpectacol(
            @RequestParam("titlu") String titlu,
            @RequestParam("descriere") String descriere,
            @RequestParam("gen") String gen,
            @RequestParam("dataSpectacol") String dataSpectacol,
            @RequestParam("oraSpectacol") String oraSpectacol,
            @RequestParam("nrBilete") Integer nrBilete,
            @RequestParam("durata") String durata,
            @RequestParam("idSala") Integer idSala,
            @RequestParam("actorii") String actorii,
            @RequestParam(value = "imagine", required = false) MultipartFile imagine) {


        Spectacol spectacol = new Spectacol();
        spectacol.setTitlu(titlu);
        spectacol.setDescriere(descriere);
        spectacol.setGen(gen);
        spectacol.setDataSpectacol(LocalDateTime.parse(dataSpectacol + "T" + oraSpectacol));
        spectacol.setNrBilete(nrBilete);
        spectacol.setDurata(durata);

        Sala sala = new Sala();
        sala.setIdSala(idSala);
        spectacol.setSala(sala);


        List<String> actorList = List.of(actorii.split("\\s*,\\s*"));

        spectacolService.addNewSpectacol(spectacol, imagine, actorList);

        return ResponseEntity.ok().build();
    }





}
