package com.example.teatru.config;

import com.example.teatru.repository.AdministratorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdministratorConfig {
    @Bean(name = "adminRun")
    CommandLineRunner commandLineRunner(AdministratorRepository repository){
        return args ->{

        };
    }
}
