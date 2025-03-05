package com.example.teatru.controller;


import com.example.teatru.service.AngajatService;
import com.example.teatru.model.Angajat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/angajat")
public class AngajatController {
    private final AngajatService angajatService;

    @Autowired
    public AngajatController(AngajatService angajatService){
        this.angajatService=angajatService;
    }

    @GetMapping
    public List<Angajat> getAngajat(){
        return angajatService.getAngajat();
    }

    @PostMapping
    public void registerNewAngajat(@RequestBody Angajat angajat){
        angajatService.addNewAngajat(angajat);
    }

    @DeleteMapping(path = "{angajatId}")
    public void deleteAngajat(@PathVariable("angajatId") Integer angajatId){
        angajatService.deleteAngajat(angajatId);
    }

    @PutMapping(path ="{angajatId}")
    public void updateAngajat(
            @PathVariable("angajatId") Integer angajatId,
            @RequestParam(required = false) String nume,
            @RequestParam(required = false) String prenume){
        angajatService.updateAngajat(angajatId,nume,prenume);
    }
}
