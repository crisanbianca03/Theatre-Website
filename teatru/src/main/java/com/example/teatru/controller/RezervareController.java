package com.example.teatru.controller;

import com.example.teatru.model.Rezervare;
import com.example.teatru.service.RezervareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/rezervare")
@CrossOrigin(origins = "http://localhost:3000")
public class RezervareController {
    private final RezervareService rezervareService;

    @Autowired
    public RezervareController(RezervareService rezervareService) {
        this.rezervareService = rezervareService;
    }

    @GetMapping
    public List<Rezervare> getRezervari() {
        return rezervareService.getRezervari();
    }


    @PostMapping
    public void registerNewRezervare(@RequestBody Rezervare rezervare) {
        rezervareService.addNewRezervare(rezervare);
    }

    @DeleteMapping(path = "{rezervareId}")
    public void deleteRezervare(@PathVariable("rezervareId") Integer rezervareId) {
        rezervareService.deleteRezervare(rezervareId);
    }

    @GetMapping("/utilizator/{idUtilizator}")
    public List<Map<String, Object>> getRezervariByUtilizator(@PathVariable int idUtilizator) {
        return rezervareService.getRezervariByUtilizator(idUtilizator);
    }
    @GetMapping("/with-details")
    public List<Map<String, Object>> getReservationsWithDetails() {
        return rezervareService.getReservationsWithDetails();
    }

    @GetMapping("/rapoarte")
    public Map<String, Object> generateRaport(@RequestParam String criteriu) {
        return rezervareService.generateRaport(criteriu);
    }

    @GetMapping("/statistici")
    public Map<String, Long> getStatistici(@RequestParam("criteriu") String criteriu) {
        return rezervareService.generateStatistici(criteriu);
    }


}
