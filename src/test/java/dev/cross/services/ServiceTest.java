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

import dev.cross.models.Account;
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
	public void deleteClientTest() {
		int test = 55;
		when(mockClientDao.deleteClient(test)).thenReturn(true);
		assertEquals(true, clientService.deleteClient(test));
	}
	
	@Test
	public void createAccountTest() {
		Account mockAccount = new Account(1, true, 100, 1);
		when(mockAccountDao.createAccount(mockAccount)).thenReturn(mockAccount);
		assertEquals(mockAccount, accountService.createAccount(mockAccount));
	}
	
	@Test
	public void getAllAccountsTest() {
		List<Account> mockAccounts = new ArrayList<>();
		mockAccounts.add(new Account(1, true, 100, 1));
		mockAccounts.add(new Account(2, false, 50, 1));
		//mockAccounts.add(new Account(3, true, 200, 2));
		//mockAccounts.add(new Account(4, true, 300, 3));
		when(mockAccountDao.getAllAccounts(1)).thenReturn(mockAccounts);
		assertEquals(mockAccounts, accountService.getAllAccounts(1));
	}
	
	@Test
	public void getAccountsParamTest() {
		List<Account> mockAccounts = new ArrayList<>();
		mockAccounts.add(new Account(1, true, 100, 1));
		//mockAccounts.add(new Account(2, false, 50, 1));
		//mockAccounts.add(new Account(3, true, 200, 2));
		//mockAccounts.add(new Account(4, true, 300, 3));
		when(mockAccountDao.getAllAccountsWithParams(1,75,1000)).thenReturn(mockAccounts);
		assertEquals(mockAccounts, accountService.getAllAccountsWithParams(1,75,1000));
	}
	
	@Test
	public void getAccountByIdTest() {
		Account mockAccount = new Account(1, true, 100, 1);
		when(mockAccountDao.getAccountById(1)).thenReturn(mockAccount);
		assertEquals(mockAccount, accountService.getAccountById(1, 1));
	}
	
	@Test
	public void getAccountByIdWrongUserTest() {
		Account mockAccount = new Account(1, true, 100, 1);
		when(mockAccountDao.getAccountById(1)).thenReturn(mockAccount);
		assertEquals(null, accountService.getAccountById(2, 1));
	}
	
	@Test
	public void updateAccountTest() {
		Account mockAccount = new Account(1, true, 100, 1);
		when(mockAccountDao.getAccountById(1)).thenReturn(mockAccount);
		when(mockAccountDao.updateAccount(mockAccount)).thenReturn(true);
		assertEquals(true, accountService.updateAccount(mockAccount));
	}
	
	@Test
	public void updateAccountWrongUserTest() {
		Account mockAccount = new Account(1, true, 100, 1);
		Account updateMockAccount = new Account(1, true, 200, 2);
		when(mockAccountDao.getAccountById(1)).thenReturn(mockAccount);
		when(mockAccountDao.updateAccount(mockAccount)).thenReturn(true);
		assertEquals(false, accountService.updateAccount(updateMockAccount));
	}
	
	@Test
	public void deleteAccountTest() {
		Account mockAccount = new Account(1, true, 100, 1);
		when(mockAccountDao.getAccountById(1)).thenReturn(mockAccount);
		when(mockAccountDao.deleteAccount(1)).thenReturn(true);
		assertEquals(true, accountService.deleteAccount(1, 1));
	}
	
	@Test
	public void deleteAccountWrongUserTest() {
		Account mockAccount = new Account(1, true, 100, 1);
		when(mockAccountDao.getAccountById(1)).thenReturn(mockAccount);
		when(mockAccountDao.deleteAccount(1)).thenReturn(true);
		assertEquals(false, accountService.deleteAccount(2, 1));
	}
	
	@Test
	public void withdrawTest() {
		Account mockAccount = new Account(1, true, 100, 1);
		when(mockAccountDao.getAccountById(1)).thenReturn(mockAccount);
		when(mockAccountDao.updateAccount(mockAccount)).thenReturn(true);
		assertEquals(true, accountService.withdraw(1, 1, 50));
	}
	
	@Test
	public void withdrawWrongUserTest() {
		Account mockAccount = new Account(1, true, 100, 1);
		when(mockAccountDao.getAccountById(1)).thenReturn(mockAccount);
		when(mockAccountDao.updateAccount(mockAccount)).thenReturn(true);
		assertEquals(false, accountService.withdraw(2, 1, 50));
	}
	
	@Test
	public void withdrawInsufficientBalanceTest() {
		Account mockAccount = new Account(1, true, 100, 1);
		when(mockAccountDao.getAccountById(1)).thenReturn(mockAccount);
		when(mockAccountDao.updateAccount(mockAccount)).thenReturn(true);
		assertEquals(false, accountService.withdraw(1, 1, 200));
	}
	
	@Test
	public void depositTest() {
		Account mockAccount = new Account(1, true, 100, 1);
		when(mockAccountDao.getAccountById(1)).thenReturn(mockAccount);
		when(mockAccountDao.updateAccount(mockAccount)).thenReturn(true);
		assertEquals(true, accountService.deposit(1, 1, 50));
	}
	
	@Test
	public void depositTestWrongUser() throws Exception {
		Account mockAccount = new Account(1, true, 100, 1);
		when(mockAccountDao.getAccountById(1)).thenReturn(mockAccount);
		when(mockAccountDao.updateAccount(mockAccount)).thenReturn(true);
		assertEquals(false, accountService.deposit(2, 1, 50));
	}
	
	@Test
	public void transferTest() {
		Account mockAccount = new Account(1, true, 100, 1);
		Account mockRecipient = new Account(2, true, 50, 2);
		when(mockAccountDao.getAccountById(1)).thenReturn(mockAccount);
		when(mockAccountDao.getAccountById(2)).thenReturn(mockRecipient);
		when(mockAccountDao.updateAccount(mockAccount)).thenReturn(true);
		when(mockAccountDao.updateAccount(mockRecipient)).thenReturn(true);
		assertEquals(true, accountService.transfer(1, 1, 2, 50));
	}
	
	@Test
	public void transferWrongUserTest() {
		Account mockAccount = new Account(1, true, 100, 1);
		Account mockRecipient = new Account(2, true, 50, 2);
		when(mockAccountDao.getAccountById(1)).thenReturn(mockAccount);
		when(mockAccountDao.getAccountById(2)).thenReturn(mockRecipient);
		when(mockAccountDao.updateAccount(mockAccount)).thenReturn(true);
		when(mockAccountDao.updateAccount(mockRecipient)).thenReturn(true);
		assertEquals(false, accountService.transfer(2, 1, 2, 50));
	}
	
	@Test
	public void transferInsufficientBalanceTest() {
		Account mockAccount = new Account(1, true, 100, 1);
		Account mockRecipient = new Account(2, true, 50, 2);
		when(mockAccountDao.getAccountById(1)).thenReturn(mockAccount);
		when(mockAccountDao.getAccountById(2)).thenReturn(mockRecipient);
		when(mockAccountDao.updateAccount(mockAccount)).thenReturn(true);
		when(mockAccountDao.updateAccount(mockRecipient)).thenReturn(true);
		assertEquals(false, accountService.transfer(1, 1, 2, 200));
	}
	
}