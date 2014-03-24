package com.CS1332.bankingapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.CS1332.bankingapplication.db.BankDataSource;
import com.CS1332.bankingapplication.presenters.BankingPresenter;
import com.CS1332.bankingapplication.views.BankingView;
import com.CS1332.bankingapplication.views.ClickListener;

public class RegisterActivity extends Activity implements BankingView {

	private ClickListener listener;
	EditText nameField;
	EditText passwordField;
	TextView prompt;
	BankDataSource datasource;
	private boolean isRegistering = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		listener = BankingPresenter.getInstance();
		listener.setBankView(this);

		nameField = (EditText) findViewById(R.id.editText1);
		passwordField = (EditText) findViewById(R.id.editText2);
		prompt = (TextView) findViewById(R.id.textView1);

	}

	public void onRegisterClick(View v) {
		listener.setBankView(this);
		listener.setRegistering(isRegistering);
		listener.onClick();
	}

	@Override
	public void addSearchRequestNotifyCallback(ClickListener listener) {
		this.listener = listener;
	}

	@Override
	public String getUsername() {
		return nameField.getText().toString();
	}

	@Override
	public String getPassword() {
		return passwordField.getText().toString();
	}

	public void transition(boolean isUser) {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}

	public void setPrompt(String msg) {
		prompt.setText(msg);
		return;
	}
}
