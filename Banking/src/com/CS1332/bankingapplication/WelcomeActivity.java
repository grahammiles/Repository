package com.CS1332.bankingapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.CS1332.bankingapplication.db.BankDataSource;
import com.CS1332.bankingapplication.presenters.BankingPresenter;

public class WelcomeActivity extends Activity {

	BankDataSource datasource;
	BankingPresenter presenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		datasource = new BankDataSource(this);
		presenter = BankingPresenter.getInstance();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.welcome, menu);
		return true;
	}

	public void gotoLoginActivity(View v) {
		presenter.setRegistering(false);
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}

	public void gotoRegisterActivity(View v) {
		presenter.setRegistering(true);
		Intent intent2 = new Intent(this, RegisterActivity.class);
		startActivity(intent2);
	}

}
