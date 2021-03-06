package dev.cross.controllers;

import java.util.List;

import dev.cross.models.Account;
import dev.cross.models.Client;
import dev.cross.repositories.AccountDAO;
import dev.cross.services.AccountService;
import io.javalin.http.Context;

public class AccountController {

	private static AccountService accountService = new AccountService(new AccountDAO());
	
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
			int c = 100000;
			if (fText != null) f = Integer.parseInt(fText);
			if (cText != null) c = Integer.parseInt(cText);
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
		int id = Integer.parseInt(ctx.pathParam("id"));
		int accountNum = Integer.parseInt(ctx.pathParam("accountNum"));
		try {
			Account account = accountService.getAccountById(id, accountNum);
			ctx.status(200);
			ctx.json(account);
		} catch (Exception e) {
			e.printStackTrace();
			ctx.status(404);
		}
	}
	
	public static void updateAccount(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		int accountNum = Integer.parseInt(ctx.pathParam("accountNum"));
		Account updatedAccount = ctx.bodyAsClass(Account.class);
		updatedAccount.setID(accountNum);
		if (accountService.updateAccount(updatedAccount)) {
			ctx.status(200);
		} else {
			ctx.status(404);
		}
	}
	
	public static void deleteAccount(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		int accountNum = Integer.parseInt(ctx.pathParam("accountNum"));
		try {
			if (accountService.deleteAccount(id, accountNum)) {
				ctx.status(205);
			} else {
				ctx.status(404);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void withdrawOrDepositFunds(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		int accountNum = Integer.parseInt(ctx.pathParam("accountNum"));
		String body = ctx.body();
		String[] split = body.split(":");
		if ((split[0].substring(2,split[0].length() - 1)).equals("withdraw")) {
			int amount = Integer.parseInt(split[1].substring(0, split[1].length() - 1));
			if (accountService.withdraw(id, accountNum, amount)) {
				ctx.status(200);
			} else {
				ctx.status(422);
			}
		} else if ((split[0].substring(2,split[0].length() - 1)).equals("deposit")) {
			int amount = Integer.parseInt(split[1].substring(0, split[1].length() - 1));
			if (accountService.deposit(id, accountNum, amount)) {
				ctx.status(200);
			} else {
				ctx.status(404);
			}
		} else {
			ctx.status(400);
		}
	}
	
	public static void transferFunds(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		int accountNum = Integer.parseInt(ctx.pathParam("accountNum"));
		int transferDest = Integer.parseInt(ctx.pathParam("transferDest"));
		String body = ctx.body();
		String[] split = body.split(":");
		if (accountService.transfer(id, accountNum, transferDest, Integer.parseInt(split[1].substring(0, split[1].length() - 1)))) {
			ctx.status(200);
		} else {
			ctx.status(422);
		}
	}
	
}
