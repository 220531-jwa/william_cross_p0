package dev.cross.services;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import dev.cross.models.Client;
import dev.cross.repositories.AccountDAO;
import dev.cross.repositories.ClientDAO;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {
	
	private static ClientService clientService;
	private static AccountService accountService;
	
	private static ClientDAO mockClientDao;
	private static AccountDAO mockAccountDao;
	
	@BeforeAll
	public static void setUp() {
		mockClientDao = mock(ClientDAO.class);
		clientService = new ClientService(mockClientDao);
		mockAccountDao = mock(AccountDAO.class);
		accountService = new AccountService(mockAccountDao);
	}
	
	@Test
	public void sanityTest() {
		
	}
	
	@Test
	public void createClientTest() {
		Client mockClient = new Client(14, "Carey", "Cross");
		when(mockClientDao.createClient(mockClient)).thenReturn(mockClient);
		assertEquals(mockClient, clientService.createClient(mockClient));
	}
	
	@Test
	public void getAllClientsTest() {
		List<Client> mockClients = new ArrayList<>();
		mockClients.add(new Client(1, "William", "Cross"));
		mockClients.add(new Client(2, "Chip", "Cross"));
		mockClients.add(new Client(3, "George", "Cross"));
		when(mockClientDao.getAllClients()).thenReturn(mockClients);
		assertEquals(mockClients, clientService.getAllClients());
	}
	
	@Test
	public void getClientTest() {
		Client mockClient = new Client(6, "Patrick", "Brannigan");
		when(mockClientDao.getClientById(6)).thenReturn(mockClient);
		assertEquals(mockClient, clientService.getClientById(6));
	}
	
	@Test
	public void updateClientTest() {
		Client mockClient = new Client(23, "Devin", "Miller");
		when(mockClientDao.updateClient(mockClient)).thenReturn(true);
		assertEquals(true, clientService.updateClient(mockClient));
	}
	
	@Test
	public void deleteClient() {
		int test = 55;
		when(mockClientDao.deleteClient(test)).thenReturn(true);
		assertEquals(true, clientService.deleteClient(test));
	}
	
}