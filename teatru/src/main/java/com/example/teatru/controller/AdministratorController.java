package com.example.teatru.controller;

import com.example.teatru.service.AdministratorService;
import com.example.teatru.model.Administrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/administrator")
public class AdministratorController {
    private final AdministratorService administratorService;

    @Autowired
    public AdministratorController(AdministratorService administratorService){
        this.administratorService=administratorService;
    }

    @GetMapping
    public List<Administrator> getAdministrator(){
        return administratorService.getAdministrator();
    }

    @PostMapping
    public void registerNewAdministrator(@RequestBody Administrator administrator){
        administratorService.addNewAdministrator(administrator);
    }

    @DeleteMapping(path = "{administratorId}")
    public void deleteAdministrator(@PathVariable("administratorId") Integer administratorId){
        administratorService.deleteAdministrator(administratorId);
    }

    @PutMapping(path ="{administratorId}")
    public void updateAdministrator(
            @PathVariable("administratorId") Integer administratorId,
            @RequestParam(required = false) String nume,
            @RequestParam(required = false) String prenume){
        administratorService.updateAdministrator(administratorId,nume,prenume);
    }
}
