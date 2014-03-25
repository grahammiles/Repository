package com.CS1332.bankingapplication.views;

public interface ReportView {
	void setTitle(String string);
	void setText(String string);
	String getStartDate();
	String getEndDate();
	void addSearchRequestNotifyCallback(ClickListener listener);
}
