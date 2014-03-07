package com.CS1332.bankingapplication;

import android.app.ListActivity;
import android.os.Bundle;

import com.CS1332.bankingapplication.presenters.BankingPresenter;

public class DepositHistoryActivity extends ListActivity {

	BankingPresenter presenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_screen);
		presenter = BankingPresenter.getInstance();
		
		
	}
	

	@Override
	protected void onResume() {
		refreshDisplay();
		super.onResume();
	}
	
	public void refreshDisplay() {
		if (presenter.isDepositInitialized()) {
			setListAdapter(presenter.getDepositAdapter(this));
		}
	}
}
