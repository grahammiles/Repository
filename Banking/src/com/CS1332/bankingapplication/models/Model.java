package com.CS1332.bankingapplication.models;

import java.util.Collection;

public interface Model {
	boolean isUser(final String username);
	boolean isUser(final String username, final String password);
	Collection<User> getUsers();
}
