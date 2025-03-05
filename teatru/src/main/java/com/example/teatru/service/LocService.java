package com.example.teatru.service;

import com.example.teatru.model.Loc;
import com.example.teatru.repository.LocRepository;
import com.example.teatru.repository.SalaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LocService {
    private final LocRepository locRepository;
    private final SalaRepository salaRepository;

    public LocService(LocRepository locRepository, SalaRepository salaRepository) {
        this.locRepository = locRepository;
        this.salaRepository = salaRepository;
    }

    public List<Loc> getLocuri() {
        return locRepository.findAll();
    }

    public void addNewLoc(Loc loc) {
        boolean salaExists = salaRepository.existsById(loc.getSala().getIdSala());
        if (!salaExists) {
            throw new IllegalStateException("Sala with id " + loc.getSala().getIdSala() + " does not exist");
        }
        locRepository.save(loc);
    }

    public void deleteLoc(Integer locId) {
        boolean exists = locRepository.existsById(locId);
        if (!exists) {
            throw new IllegalStateException("Loc with id " + locId + " does not exist");
        }
        locRepository.deleteById(locId);
    }
}
