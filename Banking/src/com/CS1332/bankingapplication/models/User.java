package com.CS1332.bankingapplication.models;

public class User {
	
	private String name;
	private String password;
	private long id;

	public User(String s, String p) {
		name = s;
		password = p;
	}
	
	public User() {
		name = null;
		password = null;
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

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long insertid) {
		this.id = insertid;
	}
	
	public boolean checkPassword(String password) {
		if (this.password.equals(password)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void changePassword(String password) {
		 this.password = password;
	}

}
