package com.CS1332.bankingapplication.models;

import java.util.Collection;

import com.CS1332.bankingapplication.db.BankDataSource;

public interface Model {
	boolean isUser(final String username);
	boolean isUser(final String username, final String password);
	void openDatasource();
	void closeDatasource();
	void initialize();
	BankDataSource getDatasource();
	Collection<User> getUsers();
}
