package com.CS1332.bankingapplication;

import android.app.ListActivity;
import android.os.Bundle;

import com.CS1332.bankingapplication.presenters.BankingPresenter;
import com.CS1332.bankingapplication.views.ClickListener;

public class DepositHistoryActivity extends ListActivity {

	ClickListener listener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_screen);
		listener = BankingPresenter.getInstance();
	}
	

	@Override
	protected void onResume() {
		refreshDisplay();
		super.onResume();
	}
	
	public void refreshDisplay() {
		if (listener.isDepositInitialized()) {
			setListAdapter(listener.getDepositAdapter(this));
		}
	}
}
