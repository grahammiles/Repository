package com.CS1332.bankingapplication.presenters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.CS1332.bankingapplication.models.Account;
import com.CS1332.bankingapplication.models.BankingModel;
import com.CS1332.bankingapplication.models.Deposit;
import com.CS1332.bankingapplication.models.Model;
import com.CS1332.bankingapplication.models.Transaction;
import com.CS1332.bankingapplication.models.Withdrawal;
import com.CS1332.bankingapplication.views.BankingView;
import com.CS1332.bankingapplication.views.ChangePasswordView;
import com.CS1332.bankingapplication.views.ClickListener;
import com.CS1332.bankingapplication.views.ReportView;
import com.CS1332.bankingapplication.views.TransactionView;
import com.CS1332.bankingapplication.views.UserView;

public class BankingPresenter implements ClickListener {
	private static BankingPresenter presenter;
	private BankingView bankView;
	private TransactionView transactionView;
	private ChangePasswordView changePasswordView;
	private UserView userView;
	private ReportView reportView;
	private Model model;
	public static String username;
	private boolean isRegistering;
	private boolean isDepositing;
	private boolean withdrawInitialized = false;
	private boolean depositInitialized = false;
	SimpleDateFormat dateFormat;
	Account account;

	private BankingPresenter(BankingModel model) {
		this.model = model;
		List<Transaction> deposits = model.findAllDeposits();
		List<Transaction> withdrawals = model.findAllWithdrawals();
		if (!deposits.isEmpty()) {
			depositInitialized = true;
		}
		if (!withdrawals.isEmpty()) {
			withdrawInitialized = true;
		}
	}

	@Override
	public void onClick() {
		String username = bankView.getUsername();
		String password = bankView.getPassword();
		if (isRegistering && (username.length() > 0) && (password.length() > 0) && (model.isUser(username) == false)) {
			model.createUser(username, password);
			model.initialize();
			bankView.transition(true);
		} else if (!isRegistering && (username.length() > 0) && (password.length() > 0)) {
			boolean isUser = model.isUser(username, password);
			if (isUser) {
				BankingPresenter.username = username;
				bankView.transition(isUser);
			} else {
				bankView.setPrompt("Login failed");
			}
		} else {
			bankView.setPrompt("Account information invalid. Please register again.");
		}
	}

	public void onCreateAccountClick() {

		String name = userView.getName();
		String display = userView.getDisplay();
		Double balance = userView.getBalance();
		Double mir = userView.getMir();

		if (name.trim().length() > 0 && display.trim().length() > 0 && balance != null && mir != null) {
			model.addToAccount(BankingPresenter.username, name, display, balance, mir);
			userView.setPrompt("");
		} else {
			userView.setPrompt("More account information required. Please fill in all data fields.");
		}

	}
	
	public void onChangePasswordClick(){
		String oldP = changePasswordView.getOldPassword();
		String newP = changePasswordView.getNewPassword();
		String confirmNewP = changePasswordView.confirmNewPassword();
		
		if(!model.isUser(username, oldP)){
			changePasswordView.setPrompt("Incorrect old password! Please try again.");
		}else if(!(newP.equals(confirmNewP))){
			changePasswordView.setPrompt("New passwords do not match! Please try again");
		}else{
			((BankingModel) model).changePassword(username, newP);
			changePasswordView.setPrompt("Success!");
		}
	}

	public void onCreateTransactionClick() {
		String reason = transactionView.getReason();
		Double amount = transactionView.getAmount();
		Integer date = transactionView.getDate();

		if (reason.length() > 0 && amount != null && isDepositing) {
			depositInitialized = true;
			Deposit deposit = new Deposit(reason, amount, this.account);
			this.account = deposit.modifyAccount(this.account);
			model.updateAccount(this.account);
			model.addToDeposit(deposit);
			transactionView.setPrompt("");
		} else if (reason.length() > 0 && amount != null && !isDepositing) {
			withdrawInitialized = true;
			Withdrawal withdrawal = new Withdrawal(reason, amount, this.account);
			this.account = withdrawal.modifyAccount(this.account);
			model.updateAccount(this.account);
			model.addToWithdrawal(withdrawal);
			transactionView.setPrompt("");
		} else {
			transactionView.setPrompt("Please fill in all data fields.");
		}
	}

	@Override
	public void onCreateReportClick() throws ParseException {
		String startDate = reportView.getStartDate();
		String endDate = reportView.getEndDate();
		startDate = startDate.trim();
		endDate = endDate.trim();

		if (startDate.length() > 0 && endDate.length() > 0) {
			dateFormat = new SimpleDateFormat("MM/dd/yyyy kk:mm:ss.SSS", Locale.ENGLISH);
			Date d1 = dateFormat.parse(startDate + " 00:00:00.000");
			Date d2 = dateFormat.parse(endDate + " 23:59:59.999");
			long startTimeInMillisSinceEpoch = d1.getTime();
			long endTimeInMillisSinceEpoch = d2.getTime();
			String[] args = new String[] { BankingPresenter.username, Long.toString(startTimeInMillisSinceEpoch), Long.toString(endTimeInMillisSinceEpoch) };
			reportView.setTitle("Spending Category Report for " + BankingPresenter.username + "\n" + "from " + startDate + " to " + endDate);
			String description = model.getSpendingReportText(args);
			reportView.setText(description);

		}

	}

	public void setAccount(int position) {
		Account account = getAccountToModify(position);
		this.account = account;
	}

	public boolean isWithdrawInitialized() {
		return withdrawInitialized;
	}

	public boolean isDepositInitialized() {
		return depositInitialized;
	}

	public boolean isDepositing() {
		return isDepositing;
	}

	public void setDepositing(boolean isDepositing) {
		this.isDepositing = isDepositing;
	}

	public static BankingPresenter getInstance() {
		if (presenter == null) {
			presenter = new BankingPresenter(new BankingModel());
		}
		return presenter;
	}

	public void setBankView(BankingView bankView) {
		this.bankView = bankView;
//		this.bankView.addSearchRequestNotifyCallback(BankingPresenter.getInstance());
	}

	public void setUserView(UserView userView) {
		this.userView = userView;
//		this.userView.addSearchRequestNotifyCallback(BankingPresenter.getInstance());
	}

	public void setTransactionView(TransactionView transactionView) {
		this.transactionView = transactionView;
//		this.transactionView.addSearchRequestNotifyCallback(BankingPresenter.getInstance());
	}
	
	public void setChangePasswordView(ChangePasswordView changePasswordView){
		this.changePasswordView = changePasswordView;
	}

	public void setReportView(ReportView reportView) {
		this.reportView = reportView;
//		this.reportView.addSearchRequestNotifyCallback(BankingPresenter.getInstance());
	}

	public void setRegistering(boolean isRegistering) {
		this.isRegistering = isRegistering;
	}

	public ArrayAdapter<Account> getAccountAdapter(Context cxt) {
		String[] args = new String[] { BankingPresenter.username };
		List<Account> accounts = model.findAccount(args, "balance ASC");
		ArrayAdapter<Account> adapter = new ArrayAdapter<Account>(cxt, android.R.layout.simple_list_item_1, accounts);
		return adapter;
	}

	public ArrayAdapter<Transaction> getDepositAdapter(Context cxt) {
		String[] args = new String[] { BankingPresenter.username, account.getName() };
		List<Transaction> transactions = model.findDeposits(args, "amount ASC");
		ArrayAdapter<Transaction> adapter = new ArrayAdapter<Transaction>(cxt, android.R.layout.simple_list_item_1, transactions);
		return adapter;
	}

	public ArrayAdapter<Transaction> getWithdrawalAdapter(Context cxt) {
		String[] args = new String[] { BankingPresenter.username, account.getName() };
		List<Transaction> transactions = model.findWithdrawals(args, "amount DESC");
		ArrayAdapter<Transaction> adapter = new ArrayAdapter<Transaction>(cxt, android.R.layout.simple_list_item_1, transactions);
		return adapter;
	}

	public List<Transaction> getSpendingReportList(Context cxt, String[] args) {
		List<Transaction> transactions = model.findWithdrawalsForSpendingReport(args, "amount DESC");
		return transactions;
	}

	public Account getAccountToModify(int position) {
		String[] args = new String[] { BankingPresenter.username };
		List<Account> accounts = model.findAccount(args, "balance ASC");
		Account account = accounts.get(position);
		return account;
	}

}
