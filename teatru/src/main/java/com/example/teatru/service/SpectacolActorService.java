package com.example.teatru.service;

import com.example.teatru.model.SpectacolActor;
import com.example.teatru.repository.SpectacolActorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpectacolActorService {
    private final SpectacolActorRepository spectacolActorRepository;

    public SpectacolActorService(SpectacolActorRepository spectacolActorRepository) {
        this.spectacolActorRepository = spectacolActorRepository;
    }

    public List<SpectacolActor> getSpectacolActors() {
        return spectacolActorRepository.findAll();
    }

    public void addSpectacolActor(SpectacolActor spectacolActor) {
        spectacolActorRepository.save(spectacolActor);
    }
}
