package com.example.teatru.config;

import com.example.teatru.model.Utilizator;
import com.example.teatru.repository.UtilizatorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilizatorConfig {

    @Bean(name = "utilizatorRun")
    CommandLineRunner commandLineRunner(UtilizatorRepository utilizatorRepository) {
        return args -> {

            Utilizator admin = new Utilizator(
                    "Admin",
                    "PrenumeAdmin",
                    "admin@yahoo.com",
                    "admin",
                    "admin"
            );
            utilizatorRepository.save(admin);


            Utilizator angajat = new Utilizator(
                    "Angajat",
                    "PrenumeAngajat",
                    "angajat@yahoo.com",
                    "angajat",
                    "angajat"
            );
            utilizatorRepository.save(angajat);

        };
    }
}
