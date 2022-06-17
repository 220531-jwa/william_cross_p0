package dev.cross.services;

import java.util.List;

import dev.cross.models.Account;
import dev.cross.repositories.AccountDAO;

public class AccountService {
	private static AccountDAO accountDao; 
	
	public AccountService(AccountDAO a) {
		accountDao = a;
	}
	
	public Account createAccount(Account a) {
		return accountDao.createAccount(a);
	}
	
	public List<Account> getAllAccounts(int id) {
		return accountDao.getAllAccounts(id);
	}
	
	public List<Account> getAllAccountsWithParams(int id, int floor, int ceiling) {
		return accountDao.getAllAccountsWithParams(id, floor, ceiling);
	}
	
	public Account getAccountById(int client, int id) {
		Account account = accountDao.getAccountById(id);
		if (account.getUserID() == client) return account;
		else return null;
	}
	
	public boolean updateAccount(Account a) {
		if (getAccountById(a.getUserID(), a.getId()) != null) return accountDao.updateAccount(a);
		else return false;
	}
	
	public boolean deleteAccount(int client, int id) {
		if (getAccountById(client, id) != null) return accountDao.deleteAccount(id);
		else return false;
	}
	
	public boolean withdraw (int client, int id, int amount) {
		Account a = getAccountById(client, id);
		if (a == null) return false;
		int balance = a.getBalance();
		if (balance < amount) return false;
		a.setBalance(balance - amount);
		return accountDao.updateAccount(a);
	}
	
	public boolean deposit (int client, int id, int amount) {
		Account a = getAccountById(client, id);
		if (a == null) return false;
		a.setBalance(a.getBalance() + amount);
		return accountDao.updateAccount(a);
	}
	
	public boolean transfer(int client, int sendId, int recieveId, int amount) {
		Account a = getAccountById(client, sendId);
		Account b = accountDao.getAccountById(recieveId);
		if (a == null || b == null) return false;
		int balance = a.getBalance();
		if (balance < amount) return false;
		a.setBalance(balance - amount);
		b.setBalance(b.getBalance() + amount);
		if(!(accountDao.updateAccount(a))) return false;
		return accountDao.updateAccount(b);
	}
}
