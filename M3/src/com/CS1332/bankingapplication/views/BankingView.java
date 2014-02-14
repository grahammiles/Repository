package com.CS1332.bankingapplication.views;


public interface BankingView {
	String getUsername();
	String getPassword();
	void addSearchRequestNotifyCallback(ClickListener listener);
	void transition(boolean isUser);
}
