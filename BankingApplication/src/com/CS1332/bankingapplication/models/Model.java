package com.CS1332.bankingapplication.models;

import java.util.Collection;
import java.util.List;

import com.CS1332.bankingapplication.db.BankDataSource;

public interface Model {
	boolean isUser(final String username);
	boolean isUser(final String username, final String password);
	void openDatasource();
	void closeDatasource();
	void initialize();
	void createUser(String username, String password);
	void addToAccount(String username, String name, String display, Double balance, Double mir);
	void updateAccount(Account account);
	void addToDeposit(Deposit deposit);
	void addToWithdrawal(Withdrawal withdrawal);
	List<Account> findAccount(String[] args, String orderBy);
	List<Transaction> findAllDeposits();
	List<Transaction> findAllWithdrawals();
	List<Transaction> findDeposits(String[] args, String orderBy);
	List<Transaction> findWithdrawals(String[] args, String orderBy);
	List<Transaction> findWithdrawalsForSpendingReport(String[] args, String orderBy);
	BankDataSource getDatasource();
	Collection<User> getUsers();
	String getSpendingReportText(String[] args);
}
