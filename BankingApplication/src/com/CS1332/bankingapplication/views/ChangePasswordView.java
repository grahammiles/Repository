package com.CS1332.bankingapplication.views;

public interface ChangePasswordView {

	String getOldPassword();
	String getNewPassword();
	String confirmNewPassword();
	void addSearchRequestNotifyCallback(ClickListener listener);
	void setPrompt(String string);
}
