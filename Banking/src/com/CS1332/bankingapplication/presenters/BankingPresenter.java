package com.CS1332.bankingapplication.presenters;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.CS1332.bankingapplication.models.Account;
import com.CS1332.bankingapplication.models.BankingModel;
import com.CS1332.bankingapplication.models.User;
import com.CS1332.bankingapplication.views.BankingView;
import com.CS1332.bankingapplication.views.ClickListener;
import com.CS1332.bankingapplication.views.UserView;

public class BankingPresenter implements ClickListener {
	private static BankingPresenter presenter;
	private BankingView bankView;

	private UserView userView;
	private BankingModel model;
	public static String username;
	private boolean isRegistering;

	public BankingPresenter(BankingView view, BankingModel model) {
		this.bankView = view;
		this.model = model;
		this.bankView.addSearchRequestNotifyCallback(BankingPresenter.getInstance());
	}

	public BankingPresenter(UserView view, BankingModel model) {
		this.userView = view;
		this.model = model;
		this.userView.addSearchRequestNotifyCallback(BankingPresenter.getInstance());
	}

	public BankingPresenter(BankingModel model) {
		this.model = model;
	}

	@Override
	public void onClick() {
		String username = bankView.getUsername();
		String password = bankView.getPassword();
		if (isRegistering && (username.length() > 0) && (password.length() > 0) && (model.isUser(username) == false)) {
			model.openDatasource();
			model.getDatasource().create(new User(username, password));
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
			model.getDatasource().addToAccount(new Account(BankingPresenter.username, name, display, balance, mir));
			model.closeDatasource();
			userView.setPrompt("");
		} else {
			userView.setPrompt("More account information required. Please fill in all data fields.");
		}

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

	public void setRegistering(boolean isRegistering) {
		this.isRegistering = isRegistering;
	}

	public ArrayAdapter<Account> getAdapter(Context cxt) {
		String[] args = new String[] { BankingPresenter.username };
		model.openDatasource();
		List<Account> accounts = model.getDatasource().findFiltered(args, "balance ASC");
		model.closeDatasource();
		ArrayAdapter<Account> adapter = new ArrayAdapter<Account>(cxt, android.R.layout.simple_list_item_1, accounts);
		return adapter;
	}
}
