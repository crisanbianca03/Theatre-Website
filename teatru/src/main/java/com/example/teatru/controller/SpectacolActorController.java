package com.example.teatru.controller;

import com.example.teatru.model.SpectacolActor;
import com.example.teatru.service.SpectacolActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/spectacolactor")
public class SpectacolActorController {
    private final SpectacolActorService spectacolActorService;

    @Autowired
    public SpectacolActorController(SpectacolActorService spectacolActorService) {
        this.spectacolActorService = spectacolActorService;
    }

    @GetMapping
    public List<SpectacolActor> getSpectacolActors() {
        return spectacolActorService.getSpectacolActors();
    }

    @PostMapping
    public void addSpectacolActor(@RequestBody SpectacolActor spectacolActor) {
        spectacolActorService.addSpectacolActor(spectacolActor);
    }
}
