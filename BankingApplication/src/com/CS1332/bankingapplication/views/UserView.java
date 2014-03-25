package com.CS1332.bankingapplication.views;

public interface UserView {
	String getName();
	String getDisplay();
	Double getBalance();
	Double getMir();
	void addSearchRequestNotifyCallback(ClickListener listener);
	void setPrompt(String string);
}
