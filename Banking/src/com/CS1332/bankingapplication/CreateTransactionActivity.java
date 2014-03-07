package com.CS1332.bankingapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.CS1332.bankingapplication.db.BankDataSource;
import com.CS1332.bankingapplication.presenters.BankingPresenter;
import com.CS1332.bankingapplication.views.ClickListener;
import com.CS1332.bankingapplication.views.TransactionView;

public class CreateTransactionActivity extends Activity implements TransactionView {

	ClickListener listener;
	BankingPresenter presenter;
	EditText reason;
	EditText amount;
	EditText dateToTakeEffect;
	TextView prompt;
	BankDataSource datasource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_transaction);

		presenter = BankingPresenter.getInstance();
		presenter.setTransactionView(this);
		reason = (EditText) findViewById(R.id.editText1);
		amount = (EditText) findViewById(R.id.editText2);
		dateToTakeEffect = (EditText) findViewById(R.id.editText3);
		prompt = (TextView) findViewById(R.id.textView1);

	}
	
	public void createTransaction(View v) {
		presenter.setTransactionView(this);
		listener.onCreateTransactionClick();
		if (prompt.getText().toString().length() == 0) {
			finish();
		}

	}

	@Override
	public String getReason() {
		return reason.getText().toString().trim();
	}

	@Override
	public Double getAmount() {
		Double dub = null;
		if (amount.getText().toString().length() > 0) {
			dub = Double.parseDouble(amount.getText().toString());
		}
		return dub;
	}

	@Override
	public Integer getDate() {
		return 1; // Placeholder
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
