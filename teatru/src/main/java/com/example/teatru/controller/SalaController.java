package com.example.teatru.controller;

import com.example.teatru.model.Loc;
import com.example.teatru.model.Sala;
import com.example.teatru.service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/sala")
public class SalaController {
    private final SalaService salaService;

    @Autowired
    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @GetMapping
    public List<Sala> getSala() {
        return salaService.getSala();
    }

    @GetMapping(path = "{salaId}/nrLocuri")
    public int getNrLocuriInSala(@PathVariable("salaId") Integer salaId) {
        return salaService.getNrLocuriInSala(salaId);
    }

    @PostMapping
    public void registerNewSala(@RequestBody Sala sala) {
        salaService.addNewSala(sala);
    }

    @DeleteMapping(path = "{salaId}")
    public void deleteSala(@PathVariable("salaId") Integer salaId) {
        salaService.deleteSala(salaId);
    }

    @PutMapping(path = "{salaId}")
    public void updateSala(@PathVariable("salaId") Integer salaId,
                           @RequestParam List<Loc> nrLocuri) {
        salaService.updateSala(salaId, nrLocuri);
    }

}
