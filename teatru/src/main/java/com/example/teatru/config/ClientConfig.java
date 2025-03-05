package com.example.teatru.config;

import com.example.teatru.repository.ClientRepository;
import com.example.teatru.model.Client;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ClientConfig {
    @Bean
    CommandLineRunner commandLineRunner(ClientRepository repository){
        return args ->{
           Client bianca= new Client("daniel","moldovan");
            Client diana= new Client("diana","zahan");
            repository.saveAll(List.of(bianca,diana));
        };
    }
}
