package com.example.teatru.service;

import com.example.teatru.model.Loc;
import com.example.teatru.model.Sala;
import com.example.teatru.repository.LocRepository;
import com.example.teatru.repository.SalaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
public class SalaService {
    private final SalaRepository salaRepository;
    private final LocRepository locRepository;

    public SalaService(SalaRepository salaRepository, LocRepository locRepository) {
        this.salaRepository = salaRepository;
        this.locRepository = locRepository;
    }

    public List<Sala> getSala() {
        return salaRepository.findAll();
    }

    public void addNewSala(Sala sala) {
        if (sala.getNrLocuri() <= 0) {
            throw new IllegalArgumentException("Numărul de locuri trebuie să fie pozitiv");
        }
        salaRepository.save(sala);
    }

    public void deleteSala(Integer salaId) {
        boolean exists = salaRepository.existsById(salaId);
        if (!exists) {
            throw new IllegalStateException("Sala with id " + salaId + " does not exist");
        }
        salaRepository.deleteById(salaId);
    }

    public int getNrLocuriInSala(Integer salaId) {
        Sala sala = salaRepository.findById(salaId)
                .orElseThrow(() -> new IllegalStateException("Sala with id " + salaId + " does not exist"));
        return sala.getNrLocuri();
    }

    @Transactional
    public void updateSala(Integer salaId, List<Loc> nrLocuri) {
        Sala sala = salaRepository.findById(salaId)
                .orElseThrow(() -> new IllegalStateException(
                        "Sala with id " + salaId + " does not exist"
                ));

        if (sala.getNrLocuri() > 0 && !Objects.equals(sala.getNrLocuri(), nrLocuri)) {
            sala.setLocuri(nrLocuri);
        }
    }
}
