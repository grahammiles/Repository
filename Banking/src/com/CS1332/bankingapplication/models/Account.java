package com.CS1332.bankingapplication.models;

public class Account {
	
	private String user;
	private String name;
	private String username;
	private long id;
	private Double balance;
	private Double rate;
	
	public Account(String user, String name, String username, Double balance, Double rate) {
		this.user = user;
		this.name = name;
		this.username = username;
		this.balance = balance;
		this.rate = rate;
	}
	
	public Account() {
		this(null, null, null, null, null);
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	
	public String toString() {
		return username + " " + balance + " " + rate;
	}

}
