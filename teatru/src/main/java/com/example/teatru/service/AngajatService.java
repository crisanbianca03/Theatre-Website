package com.example.teatru.service;

import com.example.teatru.model.Angajat;
import com.example.teatru.repository.AngajatRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AngajatService {
    private final AngajatRepository angajatRepository;

    public AngajatService(AngajatRepository angajatRepository) {
        this.angajatRepository = angajatRepository;
    }

    public List<Angajat> getAngajat(){
        return angajatRepository.findAll();
    }

    public void addNewAngajat(Angajat angajat) {
        if (angajat.getNume() == null || angajat.getNume().trim().isEmpty()) {
            throw new IllegalArgumentException("nume invalid");
        }
        if (angajat.getPrenume() == null || angajat.getPrenume().trim().isEmpty()) {
            throw new IllegalArgumentException("prenume invalid");
        }
        angajatRepository.save(angajat);
    }

    public void deleteAngajat(Integer angajatId) {
        boolean exists=angajatRepository.existsById(angajatId);
        if(!exists){
            throw new IllegalStateException("angajat with id " + angajatId + "does not exist");
        }
        angajatRepository.deleteById(angajatId);
    }

    @Transactional
    public void updateAngajat(Integer angajatId, String nume, String prenume) {
        Angajat angajat=angajatRepository.findById(angajatId)
                .orElseThrow(() -> new IllegalStateException(
                        "angajat with id" + angajatId + " does not exist"
                ));
        if((nume != null) && (nume.length() > 0) && !Objects.equals(angajat.getNume(), nume)){
            angajat.setNume(nume);
        }
        if( prenume != null && prenume.length() > 0 && !Objects.equals(angajat.getPrenume(),prenume)){
            angajat.setPrenume(prenume);
        }
    }
}
