package com.example.teatru.service;

import com.example.teatru.model.Administrator;
import com.example.teatru.repository.AdministratorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AdministratorService {
    private final AdministratorRepository administratorRepository;

    public AdministratorService(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }

    public List<Administrator> getAdministrator(){
        return administratorRepository.findAll();
    }

    public void addNewAdministrator(Administrator administrator) {
        if (administrator.getNume() == null || administrator.getNume().trim().isEmpty()) {
            throw new IllegalArgumentException("nume invalid");
        }
        if (administrator.getPrenume() == null || administrator.getPrenume().trim().isEmpty()) {
            throw new IllegalArgumentException("prenume invalid");
        }
        administratorRepository.save(administrator);
    }

    public void deleteAdministrator(Integer administratorId) {
        boolean exists=administratorRepository.existsById(administratorId);
        if(!exists){
            throw new IllegalStateException("client with id " + administratorId + "does not exist");
        }
        administratorRepository.deleteById(administratorId);
    }

    @Transactional
    public void updateAdministrator(Integer administratorId, String nume, String prenume) {
        Administrator administrator=administratorRepository.findById(administratorId)
                .orElseThrow(() -> new IllegalStateException(
                        "client with id" + administratorId + " does not exist"
                ));
        if((nume != null) && (nume.length() > 0) && !Objects.equals(administrator.getNume(), nume)){
            administrator.setNume(nume);
        }
        if( prenume != null && prenume.length() > 0 && !Objects.equals(administrator.getPrenume(),prenume)){
            administrator.setPrenume(prenume);
        }
    }
}
