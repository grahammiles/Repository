package com.CS1332.bankingapplication.models;

import java.util.Collection;
import java.util.Map;

import com.CS1332.bankingapplication.db.BankDataSource;

public class BankingModel implements Model {

	private Map<String, User> users;
	private BankDataSource datasource;

	public BankingModel(BankDataSource datasource) {
		this.datasource = datasource;
		initialize();
	}

	public BankingModel() {

	}

	@Override
	public boolean isUser(final String username, final String password) {
		User user = users.get(username);
		if (user != null) {
			if (user.getPassword().equals(password)) {
				return true;
			}
		}
		return false;

	}

	@Override
	public User getUserByName(final String username) {
		User user = users.get(username);
		return user;
	}

	@Override
	public Collection<User> getUsers() {
		return users.values();
	}

	public BankDataSource getDatasource() {
		return datasource;
	}
	
	public void openDatasource() {
		datasource.open();
	}
	
	public void closeDatasource() {
		datasource.close();
	}

	public void setDatasource(BankDataSource datasource) {
		this.datasource = datasource;
	}
	
	public void initialize() {
		this.datasource.open();
		users = this.datasource.findAll();
		if (users.isEmpty()) {
			this.datasource.create(new User("admin", "pass123"));
			users = this.datasource.findAll();
		}
		this.datasource.close();
		
	}
}
