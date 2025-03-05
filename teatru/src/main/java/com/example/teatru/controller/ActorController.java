package com.example.teatru.controller;

import com.example.teatru.service.ActorService;
import com.example.teatru.model.Actor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/actor")
public class ActorController {
    private final ActorService actorService;

    @Autowired
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping
    public List<Actor> getAllActors() {
        return actorService.getAllActors();
    }

    @GetMapping("/with-spectacles")
    public List<Actor> getActorsWithSpectacles() {
        return actorService.getActorsWithSpectacles();
    }
}
