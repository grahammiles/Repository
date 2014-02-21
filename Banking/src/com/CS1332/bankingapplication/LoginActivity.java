package com.CS1332.bankingapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.CS1332.bankingapplication.db.BankDataSource;
import com.CS1332.bankingapplication.models.BankingModel;
import com.CS1332.bankingapplication.presenters.BankingPresenter;
import com.CS1332.bankingapplication.views.BankingView;
import com.CS1332.bankingapplication.views.ClickListener;

public class LoginActivity extends Activity implements BankingView {
	
	private ClickListener listener;
	BankingPresenter presenter;
    EditText nameField;
    EditText passwordField;
    TextView prompt;
    BankDataSource datasource;
    private boolean isRegistering = false;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		datasource = new BankDataSource(this);
		presenter = new BankingPresenter(this, new BankingModel(datasource));
		
		nameField =  (EditText) findViewById(R.id.editText1);
		passwordField =  (EditText) findViewById(R.id.editText2);
		prompt = (TextView) findViewById(R.id.textView1);
		
	}	
	public void onLoginClick(View v) {
		listener.onClick(isRegistering);
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
	
	public void setPrompt(String msg) {
		prompt.setText(msg);
		return;
	}
	
	public void transition(boolean isUser) {
		if (isUser) {
			Intent intent = new Intent(this, LoginScreenActivity.class);
			startActivity(intent);
		}
	}
	
}
