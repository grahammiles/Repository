package com.CS1332.bankingapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.CS1332.bankingapplication.db.BankDataSource;

public class WelcomeActivity extends Activity {

	BankDataSource datasource;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
//		datasource = new BankDataSource(this);
//		datasource.open();
//		datasource.close();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.welcome, menu);
		return true;
	}

	public void gotoLoginActivity(View v) {

		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);

	}

	public void gotoRegisterActivity(View v) {

		Intent intent2 = new Intent(this, RegisterActivity.class);
		startActivity(intent2);

	}

}
