package com.CS1332.bankingapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.CS1332.bankingapplication.presenters.BankingPresenter;

public class TransactionActivity extends Activity {

	BankingPresenter presenter;
	int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transaction);

		presenter = BankingPresenter.getInstance();

	}

	public void gotoCreateDepositActivity(View v) {
		Intent intent = new Intent(this, CreateTransactionActivity.class);
		presenter.setDepositing(true);
		startActivity(intent);
		finish();
		return;
	}

	public void gotoCreateWithdrawalActivity(View v) {
		Intent intent = new Intent(this, CreateTransactionActivity.class);
		presenter.setDepositing(false);
		startActivity(intent);
		finish();
		return;
	}

	public void gotoDepositHistory(View v) {
		if (presenter.isDepositInitialized()) {
			Intent intent = new Intent(this, DepositHistoryActivity.class);
			startActivity(intent);
			finish();
		}
	}

	public void gotoWithdrawalHistory(View v) {
		if (presenter.isWithdrawInitialized()) {
			Intent intent = new Intent(this, WithdrawalHistoryActivity.class);
			startActivity(intent);
			finish();
		}
	}

}
