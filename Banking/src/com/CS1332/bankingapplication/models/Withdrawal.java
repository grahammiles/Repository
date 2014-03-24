package com.CS1332.bankingapplication.models;

public class Withdrawal extends Transaction {

	public Withdrawal(String reason, Double amount, Account account) {
		super(reason, amount, account);
	}
	
	public Withdrawal() {
		super();
	}

	@Override
	public Account modifyAccount(Account account) {
		account.setBalance(account.getBalance() - this.amount);
		return account;
	}

}
