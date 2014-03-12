package com.CS1332.bankingapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.CS1332.bankingapplication.presenters.BankingPresenter;
import com.CS1332.bankingapplication.views.ClickListener;
import com.CS1332.bankingapplication.views.UserView;

public class CreateAccountActivity extends Activity implements UserView {

	EditText name;
	EditText display;
	EditText balance;
	EditText mir;
	ClickListener listener;
	TextView prompt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_account);

		listener = BankingPresenter.getInstance();
		listener.setUserView(this);

		name = (EditText) findViewById(R.id.editText1);
		display = (EditText) findViewById(R.id.editText2);
		balance = (EditText) findViewById(R.id.editText3);
		mir = (EditText) findViewById(R.id.editText4);
		prompt = (TextView) findViewById(R.id.textView1);

	}

	public String getName() {
		return name.getText().toString();
	}

	public String getDisplay() {
		return display.getText().toString();
	}

	public Double getBalance() {
		Double dub = null;
		if (balance.getText().toString().length() > 0) {
			dub = Double.parseDouble(balance.getText().toString());
		}
		return dub;
	}

	public Double getMir() {
		Double dub = null;
		if (mir.getText().toString().length() > 0) {
			dub = Double.parseDouble(mir.getText().toString());
		}
		return dub;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.create_account, menu);
		return true;
	}

	public void onCreateAccountClick(View v) {
		listener.setUserView(this);
		this.listener.onCreateAccountClick();
		if (prompt.getText().toString().length() == 0) {
			finish();
		}
	}

	@Override
	public void addSearchRequestNotifyCallback(ClickListener listener) {
		this.listener = listener;
	}

	@Override
	public void setPrompt(String string) {
		prompt.setText(string);
		return;
	}
}
