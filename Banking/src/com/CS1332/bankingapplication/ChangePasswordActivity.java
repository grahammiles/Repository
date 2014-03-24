package com.CS1332.bankingapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.CS1332.bankingapplication.presenters.BankingPresenter;
import com.CS1332.bankingapplication.views.ChangePasswordView;
import com.CS1332.bankingapplication.views.ClickListener;

public class ChangePasswordActivity extends Activity implements ChangePasswordView {
	
	BankingPresenter listener;
	EditText oldPassword;
	EditText newPassword;
	EditText confirmNewPassword;
	TextView prompt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);

		listener = BankingPresenter.getInstance();
		listener.setChangePasswordView(this);
		oldPassword = (EditText) findViewById(R.id.editText1);
		newPassword = (EditText) findViewById(R.id.editText2);
		confirmNewPassword = (EditText) findViewById(R.id.editText3);
		prompt = (TextView) findViewById(R.id.textView1);

	}
	
	public void changePasswordClick(View v){
		listener.setChangePasswordView(this);
		listener.onChangePasswordClick();
		if(prompt.getText().toString().equals("Success!")){
			finish();
		}
	}

	@Override
	public String getOldPassword() {
		return oldPassword.getText().toString().trim();
	}

	@Override
	public String getNewPassword() {
		return newPassword.getText().toString().trim();
	}

	@Override
	public String confirmNewPassword() {
		return confirmNewPassword.getText().toString().trim();
	}

	@Override
	public void setPrompt(String string) {
		prompt.setText(string);
		return;
	}

	@Override
	public void addSearchRequestNotifyCallback(ClickListener listener) {
		// TODO Auto-generated method stub
		
	}

}
