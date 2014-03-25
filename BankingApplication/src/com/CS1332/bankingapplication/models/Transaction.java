package com.CS1332.bankingapplication.models;

public abstract class Transaction {

	protected long id;
	protected String user; // User associated with this Transaction
	protected String name; // Name of account associated with this Transaction;
	protected String reason;
	protected Double amount;

	protected long timeOfTransaction;
	protected long timeOfUser;



	protected Transaction(String reason, Double amount, Account account) {
		this.reason = reason;
		this.amount = amount;
		user = account.getUser();
		name = account.getName();
		timeOfTransaction = System.currentTimeMillis();
		timeOfUser = 1; // Place holder
	}
	
	public Transaction() {
		reason = null;
		amount = 0.0;
	}

	public long getTimeOfUser() {
		return timeOfUser;
	}
	
	public void setTimeOfUser(long timeOfUser) {
		this.timeOfUser = timeOfUser;
	}
	
	public String getUser() {
		return user;
	}

	public String getName() {
		return name;
	}

	public long getTimeOfTransaction() {
		return timeOfTransaction;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTimeOfTransaction(long timeOfTransaction) {
		this.timeOfTransaction = timeOfTransaction;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public abstract Account modifyAccount(Account account);
	
	@Override
	public String toString() {
		return reason + " " + amount;
	}
}
