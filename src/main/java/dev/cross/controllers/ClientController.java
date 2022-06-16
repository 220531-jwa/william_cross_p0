package dev.cross.controllers;

import java.util.List;

import dev.cross.models.Client;
import dev.cross.services.ClientService;

import io.javalin.http.Context;

public class ClientController {
	
	private static ClientService clientService = new ClientService();
	private static List<Client> clients = clientService.getAllClients();
	
	public static void createClient(Context ctx) {
		Client clientFromRequestBody = ctx.bodyAsClass(Client.class);
		Client client = clientService.createClient(clientFromRequestBody);
		
		if (client != null) {
			ctx.status(201);
			ctx.json(client);
		} else {
			ctx.status(404);
		}
	}
	
	public static void getAllClients(Context ctx) {
		if (clients.size() > 0) {
			ctx.status(200);
			ctx.json(clients);
		} else {
			ctx.status(404);
		}
	}
	
	public static void getClientById(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		try {
			Client client = clientService.getClientById(id);
			ctx.status(200);
			ctx.json(client);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void updateClient(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		Client updatedClient = ctx.bodyAsClass(Client.class);
		updatedClient.setID(id);
		if (clientService.updateClient(updatedClient)) {
			ctx.status(200);
		} else {
			ctx.status(404);
		}
		
	}
	
	public static void deleteClient(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		try {
			if (clientService.deleteClient(id)) {
				ctx.status(205);
			} else {
				ctx.status(404);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
