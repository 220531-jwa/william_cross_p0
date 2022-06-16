
package dev.cross;

import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.*;

import dev.cross.controllers.ClientController;

public class Main {

	public static void main(String[] args) {
		
		Javalin app = Javalin.create();
		
		app.start(8080);
		
		app.routes(() -> {
			path("/clients", () ->{
				post(ClientController::createClient); //A
				get(ClientController::getAllClients); //B
				
				path("/{id}", () ->{
					get(ClientController::getClientById); //C
					put(ClientController::updateClient); //D
					delete(ClientController::deleteClient); //E
					
					path("/accounts", () ->{
						//post(AccountController::createAccount); //F
						//get(AccountController::getAccounts); //G+H
						
						path("/{accountNum}", () ->{
							//get(AccountController::getAccountByNumber); //I
							//put(AccountController::updateAccount); //J
							//delete(AccountController::deleteAccount); //K
							//patch(AccountController::withdrawOrDepositFromAccount); //L
							
							path("/transfer/{transferDest}", () ->{
								//patch(AccountController::transferFunds); //M
							});
						});
					});
				});
			});
		});
		
		
		/* 
		 * ENDPOINTS
		 * 
		 * A (x) POST /clients => Creates a new client return a 201 status code
		 * B (x) GET /clients => gets all clients return 200
		 * C (x) GET /clients/10 => get client with id of 10 return 404 if no such client exist
		 * D (x) PUT /clients/12 => updates client with id of 12 return 404 if no such client exist
		 * E (x) DELETE /clients/15 => deletes client with the id of15 return 404 if no such client exist return 205 if success
		 * F (-) POST /clients/5/accounts =>creates a new account for client with the id of 5 return a 201 status code
		 * G (-) GET /clients/7/accounts => get all accounts for client 7 return 404 if no client exists
		 * H (-) GET /clients/7/accounts?amountLessThan=2000&amountGreaterThan400 => get all accounts for client 7 between 400 and 2000 return 404 if no client exists
		 * I (-) GET /clients/9/accounts/4 => get account 4 for client 9 return 404 if no account or client exists
		 * J (-) PUT /clients/10/accounts/3 => update account with the id 3 for client 10 return 404 if no account or client exists
		 * K (-) DELETE /clients/15/accounts/6 => delete account 6 for client 15 return 404 if no account or client exists
		 * L (-) PATCH /clients/17/accounts/12 => Withdraw/deposit given amount (Body: {"deposit":500} or {"withdraw":250} return 404 if no account or client exists return 422 if insufficient funds
		 * M (-) PATCH /clients/12/accounts/7/transfer/8 => transfer funds from account 7 to account 8 (Body: {"amount":500}) return 404 if no client or either account exists return 422 if insufficient funds
		 * 
		 */
		
		app.exception(Exception.class, (e, ctx) -> {
			ctx.status(404);
			System.out.println(e);
			ctx.result("Exception: Not found");
		});
		
	}

}