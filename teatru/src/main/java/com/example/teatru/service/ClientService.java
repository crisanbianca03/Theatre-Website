package com.example.teatru.service;

import com.example.teatru.model.Client;
import com.example.teatru.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getStudents(){
        return clientRepository.findAll();
    }

    public void addNewClient(Client client) {
        if (client.getNume() == null || client.getNume().trim().isEmpty()) {
            throw new IllegalArgumentException("nume invalid");
        }
        if (client.getPrenume() == null || client.getPrenume().trim().isEmpty()) {
            throw new IllegalArgumentException("prenume invalid");
        }
        clientRepository.save(client);
    }

    public void deleteClient(Integer clientId) {
        boolean exists=clientRepository.existsById(clientId);
        if(!exists){
            throw new IllegalStateException("client with id " + clientId + "does not exist");
        }
        clientRepository.deleteById(clientId);
    }

    @Transactional
    public void updateClient(Integer clientId, String nume, String prenume) {
        Client client=clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalStateException(
                        "client with id" + clientId + " does not exist"
                ));
        if((nume != null) && (nume.length() > 0) && !Objects.equals(client.getNume(), nume)){
            client.setNume(nume);
        }
        if( prenume != null && prenume.length() > 0 && !Objects.equals(client.getPrenume(),prenume)){
            client.setPrenume(prenume);
        }
    }
}
