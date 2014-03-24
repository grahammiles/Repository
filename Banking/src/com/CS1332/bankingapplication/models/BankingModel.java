package com.CS1332.bankingapplication.models;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.CS1332.bankingapplication.db.BankDataSource;

public class BankingModel implements Model {

	private Map<String, User> users;
	private BankDataSource datasource;
	private Double total;

	public Double getTotal() {
		return total;
	}

	public BankingModel() {
		this.datasource = new BankDataSource();
		initialize();
	}

	@Override
	public boolean isUser(final String username) {
		User user = users.get(username);
		if (user != null) {
			return true;
		} else {
			return false;
		}

	}
	
	@Override
	public boolean isUser(final String username, final String password) {
		User user = users.get(username);
		if (user != null && user.checkPassword(password)) {
			return true;
		} else {
			return false;
		}

	}
	
	public void changePassword(final String username, final String password){
		User user = users.get(username);
		user.changePassword(password);
		datasource.updateUser(user);
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
	
	public void createUser(String username, String password) {
		datasource.open();
		datasource.addToUser(new User(username, password));
		datasource.close();
		return;
	}
	
	public void addToAccount(String username, String name, String display, Double balance, Double mir) {
		datasource.open();
		datasource.addToAccount(new Account(username, name, display, balance, mir));
		datasource.close();
		return;
	}
	
	public void updateUser(User user) {
		datasource.open();
		datasource.updateUser(user);
		datasource.close();
		return;
	}
	
	public void updateAccount(Account account) {
		datasource.open();
		datasource.updateAccount(account);
		datasource.close();
		return;
	}
	
	public void addToDeposit(Deposit deposit) {
		datasource.open();
		datasource.addToDeposit(deposit);
		datasource.close();
		return;
	}
	
	public void addToWithdrawal(Withdrawal withdrawal) {
		datasource.open();
		datasource.addToWithdrawal(withdrawal);
		datasource.close();
		return;
	}
	
	public List<Account> findAccount(String[] args, String orderBy) {
		datasource.open();
		List<Account> list = datasource.findAccount(args, orderBy);
		datasource.close();
		return list;
	}
	
	public List<Transaction> findAllDeposits() {
		datasource.open();
		List<Transaction> list = datasource.findAllDeposits();
		datasource.close();
		return list;
	}
	
	public List<Transaction> findAllWithdrawals() {
		datasource.open();
		List<Transaction> list = datasource.findAllWithdrawals();
		datasource.close();
		return list;
	}
	
	public List<Transaction> findDeposits(String[] args, String orderBy) {
		datasource.open();
		List<Transaction> list = datasource.findDeposits(args, orderBy);
		datasource.close();
		return list;
	}
	
	public List<Transaction> findWithdrawals(String[] args, String orderBy) {
		datasource.open();
		List<Transaction> list = datasource.findWithdrawals(args, orderBy);
		datasource.close();
		return list;
	}
	
	public List<Transaction> findWithdrawalsForSpendingReport(String[] args, String orderBy) {
		datasource.open();
		List<Transaction> list = datasource.findWithdrawalsForSpendingReport(args, orderBy);
		datasource.close();
		return list;
	}
	
	public String getSpendingReportText(String[] args) {
		datasource.open();
		List<Transaction> report = datasource.findWithdrawalsForSpendingReport(args, "Time1 ASC");
		datasource.close();
		return listToString(report);
	}
	
	public String listToString(List<Transaction> list) {
		StringBuilder text = new StringBuilder();
		Double total = 0.0;
		Iterator<Transaction> myIterator = list.iterator();
		while(myIterator.hasNext()) {
			Transaction t = myIterator.next();
			text.append(t.toString() + "\n");
			total = total + t.getAmount();
		}
		text.append("Total " + total);
		return text.toString();
		
	}

	public void initialize() {
		this.datasource.open();
		users = this.datasource.findAll();
		if (users.isEmpty()) {
			this.datasource.addToUser(new User("admin", "pass123"));
			users = this.datasource.findAll();
		}
		this.datasource.close();

	}
}
