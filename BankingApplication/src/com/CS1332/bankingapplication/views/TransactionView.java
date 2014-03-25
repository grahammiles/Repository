package com.CS1332.bankingapplication.views;

public interface TransactionView {
	String getReason();
	Double getAmount();
	Integer getDate();
	void addSearchRequestNotifyCallback(ClickListener listener);
	void setPrompt(String string);
}
