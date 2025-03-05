package com.example.teatru.controller;

import com.example.teatru.model.Utilizator;
import com.example.teatru.service.UtilizatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/utilizator")
public class UtilizatorController {
    private final UtilizatorService utilizatorService;

    @Autowired
    public UtilizatorController(UtilizatorService utilizatorService) {
        this.utilizatorService = utilizatorService;
    }

    @GetMapping("/getAll")
    public List<Utilizator> getUtilizatori() {
        return utilizatorService.getUtilizatori();
    }

    @PostMapping("/add")
    public void registerNewUtilizator(@RequestBody Utilizator utilizator) {
        System.out.println("Utilizator primit: " + utilizator);
        utilizatorService.addNewUtilizator(utilizator);
    }

    @DeleteMapping(path = "{utilizatorId}")
    public void deleteUtilizator(@PathVariable("utilizatorId") Integer utilizatorId) {
        utilizatorService.deleteUtilizator(utilizatorId);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Utilizator utilizator) {
        System.out.println("Email primit: " + utilizator.getEmail());
        System.out.println("Parola primită: " + utilizator.getParola());

        Optional<Utilizator> optionalUtilizator = utilizatorService.authenticate(utilizator.getEmail(), utilizator.getParola());
        if (optionalUtilizator.isPresent()) {
            return ResponseEntity.ok(optionalUtilizator.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credențiale invalide!");
        }
    }
    @GetMapping("/non-admins")
    public List<Utilizator> getNonAdminUsers() {
        return utilizatorService.getNonAdminUsers();
    }

    @PutMapping("/{idUtilizator}/update-tip")
    public void updateTipUtilizator(@PathVariable int idUtilizator, @RequestParam String tip) {
        utilizatorService.updateTip(idUtilizator, tip);
    }


}
