package com.example.teatru.service;

import com.example.teatru.model.Actor;
import com.example.teatru.repository.ActorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorService {
    private final ActorRepository actorRepository;

    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    public List<Actor> getActorsWithSpectacles() {
        return actorRepository.findAll();
    }
}
