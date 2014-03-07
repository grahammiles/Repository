package com.CS1332.bankingapplication;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.CS1332.bankingapplication.presenters.BankingPresenter;

public class AccountScreenActivity extends ListActivity {
	
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
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent(this, CreateAccountActivity.class);
		startActivity(intent);
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.account_screen, menu);
		return true;
	}
	
	public void refreshDisplay() {
		setListAdapter(presenter.getAccountAdapter(this));
		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		presenter.setAccount(position);
		Intent intent = new Intent(this, TransactionActivity.class);
		startActivity(intent);
		
	}
	
	

}
