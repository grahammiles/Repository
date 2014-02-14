package com.CS1332.bankingapplication.models;

public class User {
	
	private String name;
	private String password;

	public User(String s, String p) {
		name = s;
		password = p;
	}
	
	public String toString() {
		return name + " " + password;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getName() {
		return name;
	}

}
