package dev.cross.controllers;

import java.util.List;

import dev.cross.models.Account;
import dev.cross.models.Client;
import dev.cross.services.AccountService;
import io.javalin.http.Context;

public class AccountController {

	private static AccountService accountService = new AccountService();
	
	public static void createAccount(Context ctx) {
		Account accountFromRequestBody = ctx.bodyAsClass(Account.class);
		Account account = accountService.createAccount(accountFromRequestBody);
		
		if (account != null) {
			ctx.status(201);
			ctx.json(account);
		} else {
			ctx.status(404);
		}
	}
	
	public static void getAccounts(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		List<Account> accounts;
		boolean params = false;
		String fText = ctx.queryParam("amountGreaterThan");
		String cText = ctx.queryParam("amountLessThan");
		if (fText != null || cText != null) params = true;
		
		if (params) {
			int f = 0;
			int c = Integer.MAX_VALUE;
			if (fText != null) f = Integer.parseInt(fText);
			if (cText == null) c = Integer.parseInt(cText);
			accounts = accountService.getAllAccountsWithParams(id, f, c);
		} else {
			accounts = accountService.getAllAccounts(id);
		}
		
		if (accounts.size() > 0) {
			ctx.status(200);
			ctx.json(accounts);
		} else {
			ctx.status(404);
		}
		
		
	}
	
	public static void getAccountByNumber(Context ctx) {
		
	}
	
	public static void updateAccount(Context ctx) {
		
	}
	
	public static void deleteAccount(Context ctx) {
		
	}
	
	public static void withdrawOrDepositFunds(Context ctx) {
		
	}
	
	public static void transferFunds(Context ctx) {
		
	}
	
}
