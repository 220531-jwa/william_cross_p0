package dev.cross.services;

import java.util.List;

import dev.cross.models.Client;
import dev.cross.repositories.ClientDAO;

public class ClientService {
	private static ClientDAO clientDao;
	
	public ClientService(ClientDAO client) {
		this.clientDao = client;
	}
	
	public Client createClient(Client c) {
		return clientDao.createClient(c);
	}
	
	public List<Client> getAllClients() {
		return clientDao.getAllClients();
	}
	
	public Client getClientById(int id) {
		return clientDao.getClientById(id);
	}
	
	public boolean updateClient(Client c) {
		return clientDao.updateClient(c);
	}
	
	public boolean deleteClient(int id) {
		return clientDao.deleteClient(id);
	}
}