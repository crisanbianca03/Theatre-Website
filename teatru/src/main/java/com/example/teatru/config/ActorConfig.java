package com.example.teatru.config;

import com.example.teatru.repository.ActorRepository;
import com.example.teatru.model.Actor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ActorConfig {
    @Bean(name = "actorRun")
    CommandLineRunner commandLineRunner(ActorRepository repository){
        return args ->{

        };
    }
}
