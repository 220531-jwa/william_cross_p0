package dev.cross.services;

import java.util.List;

import dev.cross.models.Client;
import dev.cross.repositories.ClientDAO;

public class ClientService {
	private static ClientDAO clientDao = new ClientDAO();
	
	public Client createClient(Client c) {
		return clientDao.createClient(c);
	}
	
	public List<Client> getAllClients() {
		return clientDao.getAllClients();
	}
}