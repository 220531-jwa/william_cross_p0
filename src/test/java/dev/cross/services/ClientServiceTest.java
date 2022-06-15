package dev.cross.services;

import static org.mockito.Mockito.mock;

import java.util.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.cross.repositories.ClientDAO;
import dev.cross.models.Client;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

	private static ClientService clientService;
	
	@Mock
	private static ClientDAO mockClientDAO;
	
	@Test
	public void should_ReturnAllUsers() {
		List<Client> clientsMock = new ArrayList();
		
	}
}
