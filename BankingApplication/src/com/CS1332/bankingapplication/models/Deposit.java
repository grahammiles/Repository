package com.CS1332.bankingapplication.models;

public class Deposit extends Transaction {

	public Deposit(String reason, Double amount, Account account) {
		super(reason, amount, account);
	}
	
	public Deposit() {
		super();
	}

	@Override
	public Account modifyAccount(Account account) {
		account.setBalance(account.getBalance() + this.amount);
		return account;
	}

}
