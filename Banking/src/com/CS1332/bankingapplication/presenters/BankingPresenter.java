package com.CS1332.bankingapplication.presenters;

import com.CS1332.bankingapplication.models.BankingModel;
import com.CS1332.bankingapplication.models.User;
import com.CS1332.bankingapplication.views.BankingView;
import com.CS1332.bankingapplication.views.ClickListener;

public class BankingPresenter implements ClickListener {
	private BankingView view;
	private BankingModel model;

	public BankingPresenter(BankingView view, BankingModel model) {
		this.view = view;
		this.model = model;
		view.addSearchRequestNotifyCallback(this);
	}

	@Override
	public void onClick(boolean isRegistering) {
		String username = view.getUsername();
		String password = view.getPassword();
		if (isRegistering && (username.length() != 0) && (password.length() != 0) && (model.isUser(username) == false)) {
			model.openDatasource();
			model.getDatasource().create(new User(username, password));
			model.closeDatasource();
			view.transition(true);
		} else if (!isRegistering && (username.length() != 0) && (password.length() != 0)) {
			boolean isUser = model.isUser(username, password);
			if (isUser) {
				view.transition(isUser);
			} else {
				view.setPrompt("Login failed");
			}
		} else {
			view.setPrompt("Account information invalid. Please register again.");
		}
	}

}
