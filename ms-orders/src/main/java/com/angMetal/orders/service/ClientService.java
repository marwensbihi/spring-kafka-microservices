package com.angMetal.orders.service;

import com.angMetal.orders.entity.Client;
import com.angMetal.orders.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    // Create or Update a Client
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    // Get all Clients
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    // Get a Client by ID
    public Optional<Client> getClientById(Long clientId) {
        return clientRepository.findById(clientId);
    }

    // Delete a Client by ID
    public void deleteClient(Long clientId) {
        clientRepository.deleteById(clientId);
    }
}
