package com.example.teatru.controller;

import com.example.teatru.service.ClientService;
import com.example.teatru.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/client")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService){
        this.clientService=clientService;
    }

    @GetMapping
    public List<Client> getStudents(){
        return clientService.getStudents();
    }

    @PostMapping
    public void registerNewClient(@RequestBody Client client){
        clientService.addNewClient(client);
    }

    @DeleteMapping(path = "{clientId}")
    public void deleteClient(@PathVariable("clientId") Integer clientId){
        clientService.deleteClient(clientId);
    }

    @PutMapping(path ="{clientId}")
    public void updateClient(
            @PathVariable("clientId") Integer clientId,
            @RequestParam(required = false) String nume,
            @RequestParam(required = false) String prenume){
        clientService.updateClient(clientId,nume,prenume);
    }

}
