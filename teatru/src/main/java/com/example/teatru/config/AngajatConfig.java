package com.example.teatru.config;


import com.example.teatru.repository.AngajatRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AngajatConfig {
    @Bean(name = "angajatRun")
    CommandLineRunner commandLineRunner(AngajatRepository repository){
        return args ->{

        };
    }
}
