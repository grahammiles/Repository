package com.CS1332.bankingapplication.views;

import java.text.ParseException;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.CS1332.bankingapplication.models.Account;
import com.CS1332.bankingapplication.models.Transaction;

public interface ClickListener {
     void onClick();
     void onCreateAccountClick();
     void onCreateTransactionClick();
     void onCreateReportClick() throws ParseException;
     void setUserView(UserView v);
     void setBankView(BankingView bankView);
     void setTransactionView(TransactionView transactionView);
     void setReportView(ReportView reportView);
     void setRegistering(boolean isRegistering);
     void setDepositing(boolean isDepositing);
     boolean isDepositInitialized();
     boolean isWithdrawInitialized(); 
     ArrayAdapter<Account> getAccountAdapter(Context cxt);
     ArrayAdapter<Transaction> getDepositAdapter(Context cxt);
     ArrayAdapter<Transaction> getWithdrawalAdapter(Context cxt);
     void setAccount(int position);
}
