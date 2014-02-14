package com.CS1332.bankingapplication.models;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BankingModel implements Model {

	/** the collection of students, keyed by name */
	private Map<String, User> users;

	/**
	 * Makes a new model
	 */
	public BankingModel() {
		users = new HashMap<String, User>();
		users.put("admin", new User("admin", "pass123"));
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
}
