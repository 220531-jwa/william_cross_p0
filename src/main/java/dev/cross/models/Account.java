package dev.cross.models;

public class Account {
	
	private int id;
	private boolean savings;
	private int balance;
	private int userID;
	
	public Account() {
		super();
	}
	
	public Account(int id, boolean savings, int balance, int userID) {
		super();
		this.id = id;
		this.setSavings(savings);
		this.balance = balance;
		this.userID = userID;
	}
	
	public int getId() {
		return id;
	}
	
	public void setID(int id) {
		this.id = id;
	}

	public boolean getSavings() {
		return savings;
	}

	public void setSavings(boolean savings) {
		this.savings = savings;
	}
	
	public int getBalance() {
		return balance;
	}

	public void setBalance (int balance) {
		this.balance = balance;
	}
	
	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	@Override
	public String toString() {
		String type;
		if (savings) type = "Savings";
		else type = "Checking";
		return "Account [id=" + id +  ", type=" + type + ", balance=" + balance + ", user ID=" + userID + "]";
	}
	
}
