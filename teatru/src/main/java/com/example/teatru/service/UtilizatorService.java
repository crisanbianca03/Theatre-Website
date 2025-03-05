package com.example.teatru.service;

import com.example.teatru.model.Utilizator;
import com.example.teatru.repository.UtilizatorRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UtilizatorService {
    private final UtilizatorRepository utilizatorRepository;

    public UtilizatorService(UtilizatorRepository utilizatorRepository) {
        this.utilizatorRepository = utilizatorRepository;
    }

    public List<Utilizator> getUtilizatori() {
        return utilizatorRepository.findAll();
    }

    public void addNewUtilizator(Utilizator utilizator) {
        if (utilizator.getNume() == null || utilizator.getNume().trim().isEmpty()) {
            throw new IllegalArgumentException("Numele utilizatorului este necesar.");
        }
        if (utilizator.getPrenume() == null || utilizator.getPrenume().trim().isEmpty()) {
            throw new IllegalArgumentException("Prenumele utilizatorului este necesar.");
        }
        if (utilizator.getEmail() == null || utilizator.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email-ul utilizatorului este necesar.");
        }
        if (utilizator.getParola() == null || utilizator.getParola().trim().isEmpty()) {
            throw new IllegalArgumentException("Parola utilizatorului este necesară.");
        }

        utilizatorRepository.save(utilizator);
    }

    public void deleteUtilizator(Integer utilizatorId) {
        boolean exists = utilizatorRepository.existsById(utilizatorId);
        if (!exists) {
            throw new IllegalStateException("Utilizatorul cu ID-ul " + utilizatorId + " nu există.");
        }
        utilizatorRepository.deleteById(utilizatorId);
    }

    public Optional<Utilizator> authenticate(String email, String parola) {
        System.out.println("Email primit: " + email);
        System.out.println("Parola primită: " + parola);

        return utilizatorRepository.findAll().stream()
                .filter(utilizator -> utilizator.getEmail().equals(email) && utilizator.getParola().equals(parola))
                .findFirst();
    }

    public List<Utilizator> getNonAdminUsers() {
        return utilizatorRepository.findAll()
                .stream()
                .filter(utilizator -> !utilizator.getTip().equals("admin"))
                .collect(Collectors.toList());
    }

    public void updateTip(int idUtilizator, String tip) {
        Utilizator utilizator = utilizatorRepository.findById(idUtilizator)
                .orElseThrow(() -> new IllegalStateException("Utilizator with id " + idUtilizator + " does not exist"));
        utilizator.setTip(tip);
        utilizatorRepository.save(utilizator);
    }


}
