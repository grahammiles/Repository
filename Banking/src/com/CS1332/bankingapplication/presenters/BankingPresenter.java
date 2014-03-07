package com.CS1332.bankingapplication.presenters;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.CS1332.bankingapplication.models.Account;
import com.CS1332.bankingapplication.models.BankingModel;
import com.CS1332.bankingapplication.models.Deposit;
import com.CS1332.bankingapplication.models.Model;
import com.CS1332.bankingapplication.models.Transaction;
import com.CS1332.bankingapplication.models.User;
import com.CS1332.bankingapplication.models.Withdrawal;
import com.CS1332.bankingapplication.views.BankingView;
import com.CS1332.bankingapplication.views.ClickListener;
import com.CS1332.bankingapplication.views.TransactionView;
import com.CS1332.bankingapplication.views.UserView;

public class BankingPresenter implements ClickListener {
	private static BankingPresenter presenter;
	private BankingView bankView;
	private TransactionView transactionView;
	private UserView userView;
	private Model model;
	public static String username;
	private boolean isRegistering;
	private boolean isDepositing;
	private boolean withdrawInitialized = false;
	private boolean depositInitialized = false;

	Account account;


//	public BankingPresenter(BankingView view, BankingModel model) {
//		this.bankView = view;
//		this.model = model;
//		this.bankView.addSearchRequestNotifyCallback(BankingPresenter.getInstance());
//	}
//
//	public BankingPresenter(UserView view, BankingModel model) {
//		this.userView = view;
//		this.model = model;
//		this.userView.addSearchRequestNotifyCallback(BankingPresenter.getInstance());
//	}

	public BankingPresenter(BankingModel model) {
		this.model = model;
		model.openDatasource();
		List<Transaction> deposits = model.getDatasource().findAllDeposits();
		List<Transaction> withdrawals = model.getDatasource().findAllWithdrawals();
		if (!deposits.isEmpty()) {
			depositInitialized = true;
		}
		if (!withdrawals.isEmpty()) {
			withdrawInitialized = true;
		}
		model.closeDatasource();
	}

	@Override
	public void onClick() {
		String username = bankView.getUsername();
		String password = bankView.getPassword();
		if (isRegistering && (username.length() > 0) && (password.length() > 0) && (model.isUser(username) == false)) {
			model.openDatasource();
			model.getDatasource().create(new User(username, password)); // move functionality
			model.closeDatasource();
			model.initialize();
			bankView.transition(true);
		} else if (!isRegistering && (username.length() > 0) && (password.length() > 0)) {
			boolean isUser = model.isUser(username, password);
			if (isUser) {
				BankingPresenter.username = username;
				bankView.transition(isUser);
			} else {
				bankView.setPrompt("Login failed");
			}
		} else {
			bankView.setPrompt("Account information invalid. Please register again.");
		}
	}

	public void onCreateAccountClick() {

		String name = userView.getName();
		String display = userView.getDisplay();
		Double balance = userView.getBalance();
		Double mir = userView.getMir();

		if (name.trim().length() > 0 && display.trim().length() > 0 && balance != null && mir != null) {

			model.openDatasource();
			model.getDatasource().addToAccount(new Account(BankingPresenter.username, name, display, balance, mir)); // Switch all of this logic to model, add a method called addtoAccount to Model
			model.closeDatasource();
			userView.setPrompt("");
		} else {
			userView.setPrompt("More account information required. Please fill in all data fields.");
		}

	}

	public void onCreateTransactionClick() {
		String reason = transactionView.getReason();
		Double amount = transactionView.getAmount();
		Integer date = transactionView.getDate();

		if (reason.length() > 0 && amount != null && isDepositing) {
			depositInitialized = true;
			Deposit deposit = new Deposit(reason, amount, this.account);
			this.account = deposit.modifyAccount(this.account);
			model.openDatasource();
			model.getDatasource().updateAccount(this.account);
			model.getDatasource().addToDeposit(deposit);
			model.closeDatasource();
			transactionView.setPrompt("");
		} else if (reason.length() > 0 && amount != null && !isDepositing) {
			withdrawInitialized = true;
			Withdrawal withdrawal = new Withdrawal(reason, amount, this.account);
			this.account = withdrawal.modifyAccount(this.account);
			model.openDatasource();
			model.getDatasource().updateAccount(this.account);
			model.getDatasource().addToWithdrawal(withdrawal);
			model.closeDatasource();
			transactionView.setPrompt("");
		} else {
			transactionView.setPrompt("Please fill in all data fields.");

		}

	}
	public void setAccount(int position) {
		Account account = getAccountToModify(position);
		this.account = account;
	}
	public boolean isWithdrawInitialized() {
		return withdrawInitialized;
	}
	
	public boolean isDepositInitialized() {
		return depositInitialized;
	}

	public boolean isDepositing() {
		return isDepositing;
	}

	public void setDepositing(boolean isDepositing) {
		this.isDepositing = isDepositing;
	}

	public static BankingPresenter getInstance() {
		if (presenter == null) {
			presenter = new BankingPresenter(new BankingModel());
		}
		return presenter;
	}

	public void setBankView(BankingView bankView) {
		this.bankView = bankView;
		this.bankView.addSearchRequestNotifyCallback(BankingPresenter.getInstance());
	}

	public void setUserView(UserView userView) {
		this.userView = userView;
		this.userView.addSearchRequestNotifyCallback(BankingPresenter.getInstance());
	}

	public void setTransactionView(TransactionView transactionView) {
		this.transactionView = transactionView;
		this.transactionView.addSearchRequestNotifyCallback(BankingPresenter.getInstance());
	}

	public void setRegistering(boolean isRegistering) {
		this.isRegistering = isRegistering;
	}

	public ArrayAdapter<Account> getAccountAdapter(Context cxt) {
		String[] args = new String[] { BankingPresenter.username };
		model.openDatasource();
		List<Account> accounts = model.getDatasource().findAccount(args, "balance ASC");
		model.closeDatasource();
		ArrayAdapter<Account> adapter = new ArrayAdapter<Account>(cxt, android.R.layout.simple_list_item_1, accounts);
		return adapter;
	}

	public ArrayAdapter<Transaction> getDepositAdapter(Context cxt) {
		String[] args = new String[] { BankingPresenter.username, account.getName() };
		model.openDatasource();
		List<Transaction> transactions = model.getDatasource().findDeposits(args, "amount ASC");
		model.closeDatasource();
		ArrayAdapter<Transaction> adapter = new ArrayAdapter<Transaction>(cxt, android.R.layout.simple_list_item_1, transactions);
		return adapter;
	}

	public ArrayAdapter<Transaction> getWithdrawalAdapter(Context cxt) {
		String[] args = new String[] { BankingPresenter.username, account.getName() };
		model.openDatasource();
		List<Transaction> transactions = model.getDatasource().findWithdrawals(args, "amount DESC");
		model.closeDatasource();
		ArrayAdapter<Transaction> adapter = new ArrayAdapter<Transaction>(cxt, android.R.layout.simple_list_item_1, transactions);
		return adapter;
	}

	public Account getAccountToModify(int position) {
		String[] args = new String[] { BankingPresenter.username };
		model.openDatasource();
		List<Account> accounts = model.getDatasource().findAccount(args, "balance ASC");
		model.closeDatasource();
		Account account = accounts.get(position);
		return account;
	}

}
