package com.CS1332.bankingapplication.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.CS1332.bankingapplication.models.Account;
import com.CS1332.bankingapplication.models.Deposit;
import com.CS1332.bankingapplication.models.Transaction;
import com.CS1332.bankingapplication.models.User;
import com.CS1332.bankingapplication.models.Withdrawal;

public class BankDataSource {

	public static final String LOGTAG = "DB";

	SQLiteOpenHelper dbhelper;
	SQLiteDatabase database;
	Map<String, User> users;

	private static final String[] allColumns = { BankingDBOpenHelper.COLUMN_ID, BankingDBOpenHelper.COLUMN_TITLE, BankingDBOpenHelper.COLUMN_DESC };

	private static final String[] accountColumns = { BankingDBOpenHelper.ACCOUNT_ID, BankingDBOpenHelper.ACCOUNT_USER, BankingDBOpenHelper.ACCOUNT_NAME, BankingDBOpenHelper.ACCOUNT_USERNAME, BankingDBOpenHelper.ACCOUNT_BALANCE, BankingDBOpenHelper.ACCOUNT_RATE };

	private static final String[] depositColumns = { BankingDBOpenHelper.DEPOSIT_ID, BankingDBOpenHelper.DEPOSIT_USER, BankingDBOpenHelper.DEPOSIT_NAME, BankingDBOpenHelper.DEPOSIT_REASON, BankingDBOpenHelper.DEPOSIT_AMOUNT, BankingDBOpenHelper.DEPOSIT_TIME1, BankingDBOpenHelper.DEPOSIT_TIME2 };

	private static final String[] withdrawalColumns = { BankingDBOpenHelper.WITHDRAWAL_ID, BankingDBOpenHelper.WITHDRAWAL_USER, BankingDBOpenHelper.WITHDRAWAL_NAME, BankingDBOpenHelper.WITHDRAWAL_REASON, BankingDBOpenHelper.WITHDRAWAL_AMOUNT, BankingDBOpenHelper.WITHDRAWAL_TIME1, BankingDBOpenHelper.WITHDRAWAL_TIME2 };

	public BankDataSource(Context context) {
		dbhelper = BankingDBOpenHelper.getInstance(context);
	}

	public BankDataSource() {
		dbhelper = BankingDBOpenHelper.getInstance();
	}

	public void open() {
		Log.i(LOGTAG, "Database opened");
		database = dbhelper.getWritableDatabase();
	}

	public void close() {
		Log.i(LOGTAG, "Database closed");
		dbhelper.close();
	}


	public Map<String, User> findAll() {
		Cursor cursor = database.query(BankingDBOpenHelper.TABLE_BANK, allColumns, null, null, null, null, null);

		Log.i(LOGTAG, "Returned " + cursor.getCount() + " rows");
		Map<String, User> users = cursorToMap(cursor);
		return users;

	}
	
	public List<Transaction> findAllDeposits() {
		Cursor cursor = database.query(BankingDBOpenHelper.TABLE_DEPOSIT, depositColumns, null, null, null, null, null);

		List<Transaction>  list = cursorToDepositList(cursor);
		return list;
	}
	
	public List<Transaction> findAllWithdrawals() {
		Cursor cursor = database.query(BankingDBOpenHelper.TABLE_WITHDRAWAL, withdrawalColumns, null, null, null, null, null);

		List<Transaction>  list = cursorToWithdrawalList(cursor);
		return list;
	}

	public List<Account> findAccount(String[] selectionArgs, String orderBy) {
		Cursor cursor = database.query(BankingDBOpenHelper.TABLE_ACCOUNT, accountColumns, "user=?", selectionArgs, null, null, orderBy);

		Log.i(LOGTAG, "Returned " + cursor.getCount() + " rows");
		List<Account> accounts = cursorToAccountList(cursor);
		return accounts;

	}

	public List<Transaction> findDeposits(String[] selectionArgs, String orderBy) {
		Cursor cursor = database.query(BankingDBOpenHelper.TABLE_DEPOSIT, depositColumns, "user=? AND name=?", selectionArgs, null, null, orderBy);
		List<Transaction> transactions = cursorToDepositList(cursor);
		return transactions;
	}

	public List<Transaction> findWithdrawals(String[] selectionArgs, String orderBy) {
		Cursor cursor = database.query(BankingDBOpenHelper.TABLE_WITHDRAWAL, withdrawalColumns, "user=? AND name=?", selectionArgs, null, null, orderBy);
		List<Transaction> transactions = cursorToWithdrawalList(cursor);
		return transactions;
	}
	
	public List<Transaction> findWithdrawalsForSpendingReport(String[] selectionArgs, String orderBy) {
		Cursor cursor = database.query(BankingDBOpenHelper.TABLE_WITHDRAWAL, withdrawalColumns, "user=? AND Time1 BETWEEN ? AND ?", selectionArgs, null, null, orderBy);
		List<Transaction> transactions = cursorToWithdrawalList(cursor);
		return transactions;
	}

	private Map<String, User> cursorToMap(Cursor cursor) {
		Map<String, User> users = new HashMap<String, User>();
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				User user = new User();
				user.setId(cursor.getLong(cursor.getColumnIndex(BankingDBOpenHelper.COLUMN_ID)));
				user.setName(cursor.getString(cursor.getColumnIndex(BankingDBOpenHelper.COLUMN_TITLE)));
				user.setPassword(cursor.getString(cursor.getColumnIndex(BankingDBOpenHelper.COLUMN_DESC)));
				users.put(user.getName(), user);
			}
		}
		return users;
	}

	private List<Account> cursorToAccountList(Cursor cursor) {
		List<Account> accounts = new ArrayList<Account>();
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				Account account = new Account();
				account.setId(cursor.getLong(cursor.getColumnIndex(BankingDBOpenHelper.ACCOUNT_ID)));
				account.setUser(cursor.getString(cursor.getColumnIndex(BankingDBOpenHelper.ACCOUNT_USER)));
				account.setName(cursor.getString(cursor.getColumnIndex(BankingDBOpenHelper.ACCOUNT_NAME)));
				account.setUsername(cursor.getString(cursor.getColumnIndex(BankingDBOpenHelper.ACCOUNT_USERNAME)));
				account.setBalance(cursor.getDouble(cursor.getColumnIndex(BankingDBOpenHelper.ACCOUNT_BALANCE)));
				account.setRate(cursor.getDouble(cursor.getColumnIndex(BankingDBOpenHelper.ACCOUNT_RATE)));
				accounts.add(account);
			}
		}
		return accounts;
	}

	private List<Transaction> cursorToDepositList(Cursor cursor) {
		List<Transaction> transactions = new ArrayList<Transaction>();
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				Deposit transaction = new Deposit();
				transaction.setId(cursor.getLong(cursor.getColumnIndex(BankingDBOpenHelper.DEPOSIT_ID)));
				transaction.setUser(cursor.getString(cursor.getColumnIndex(BankingDBOpenHelper.DEPOSIT_USER)));
				transaction.setName(cursor.getString(cursor.getColumnIndex(BankingDBOpenHelper.DEPOSIT_NAME)));
				transaction.setReason(cursor.getString(cursor.getColumnIndex(BankingDBOpenHelper.DEPOSIT_REASON)));
				transaction.setAmount(cursor.getDouble(cursor.getColumnIndex(BankingDBOpenHelper.DEPOSIT_AMOUNT)));
				transaction.setTimeOfTransaction(cursor.getLong(cursor.getColumnIndex(BankingDBOpenHelper.DEPOSIT_TIME1)));
				transaction.setTimeOfUser(cursor.getLong(cursor.getColumnIndex(BankingDBOpenHelper.DEPOSIT_TIME2)));
				transactions.add(transaction);
			}
		}
		return transactions;
	}

	private List<Transaction> cursorToWithdrawalList(Cursor cursor) {
		List<Transaction> transactions = new ArrayList<Transaction>();
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				Withdrawal transaction = new Withdrawal();
				transaction.setId(cursor.getLong(cursor.getColumnIndex(BankingDBOpenHelper.WITHDRAWAL_ID)));
				transaction.setUser(cursor.getString(cursor.getColumnIndex(BankingDBOpenHelper.WITHDRAWAL_USER)));
				transaction.setName(cursor.getString(cursor.getColumnIndex(BankingDBOpenHelper.WITHDRAWAL_NAME)));
				transaction.setReason(cursor.getString(cursor.getColumnIndex(BankingDBOpenHelper.WITHDRAWAL_REASON)));
				transaction.setAmount(cursor.getDouble(cursor.getColumnIndex(BankingDBOpenHelper.WITHDRAWAL_AMOUNT)));
				transaction.setTimeOfTransaction(cursor.getLong(cursor.getColumnIndex(BankingDBOpenHelper.WITHDRAWAL_TIME1)));
				transaction.setTimeOfUser(cursor.getLong(cursor.getColumnIndex(BankingDBOpenHelper.WITHDRAWAL_TIME2)));
				transactions.add(transaction);
			}
		}
		return transactions;
	}

	public User addToUser(User user) {
		ContentValues values = new ContentValues();
		values.put(BankingDBOpenHelper.COLUMN_TITLE, user.getName());
		values.put(BankingDBOpenHelper.COLUMN_DESC, user.getPassword());
		long insertid = database.insert(BankingDBOpenHelper.TABLE_BANK, null, values);
		user.setId(insertid);
		return user;
	}
	
	public boolean addToAccount(Account account) {
		ContentValues values = new ContentValues();
		values.put(BankingDBOpenHelper.ACCOUNT_USER, account.getUser());
		values.put(BankingDBOpenHelper.ACCOUNT_NAME, account.getName());
		values.put(BankingDBOpenHelper.ACCOUNT_USERNAME, account.getUsername());
		values.put(BankingDBOpenHelper.ACCOUNT_BALANCE, account.getBalance());
		values.put(BankingDBOpenHelper.ACCOUNT_RATE, account.getRate());

		long result = database.insert(BankingDBOpenHelper.TABLE_ACCOUNT, null, values);
		return (result != -1);
	}

	public boolean addToDeposit(Deposit deposit) {
		ContentValues values = new ContentValues();
		values.put(BankingDBOpenHelper.DEPOSIT_USER, deposit.getUser());
		values.put(BankingDBOpenHelper.DEPOSIT_NAME, deposit.getName());
		values.put(BankingDBOpenHelper.DEPOSIT_REASON, deposit.getReason());
		values.put(BankingDBOpenHelper.DEPOSIT_AMOUNT, deposit.getAmount());
		values.put(BankingDBOpenHelper.DEPOSIT_TIME1, deposit.getTimeOfTransaction());
		values.put(BankingDBOpenHelper.DEPOSIT_TIME2, deposit.getTimeOfUser());


		long result = database.insert(BankingDBOpenHelper.TABLE_DEPOSIT, null, values);
		return (result != -1);
	}

	public boolean addToWithdrawal(Withdrawal withdrawal) {
		ContentValues values = new ContentValues();
		values.put(BankingDBOpenHelper.WITHDRAWAL_USER, withdrawal.getUser());
		values.put(BankingDBOpenHelper.WITHDRAWAL_NAME, withdrawal.getName());
		values.put(BankingDBOpenHelper.WITHDRAWAL_REASON, withdrawal.getReason());
		values.put(BankingDBOpenHelper.WITHDRAWAL_AMOUNT, withdrawal.getAmount());
		values.put(BankingDBOpenHelper.WITHDRAWAL_TIME1, withdrawal.getTimeOfTransaction());
		values.put(BankingDBOpenHelper.WITHDRAWAL_TIME2, withdrawal.getTimeOfUser());


		long result = database.insert(BankingDBOpenHelper.TABLE_WITHDRAWAL, null, values);
		return (result != -1);
	}
	
	public boolean updateAccount(Account account) {
		ContentValues values = new ContentValues();
		values.put(BankingDBOpenHelper.ACCOUNT_USER, account.getUser());
		values.put(BankingDBOpenHelper.ACCOUNT_NAME, account.getName());
		values.put(BankingDBOpenHelper.ACCOUNT_USERNAME, account.getUsername());
		values.put(BankingDBOpenHelper.ACCOUNT_BALANCE, account.getBalance());
		values.put(BankingDBOpenHelper.ACCOUNT_RATE, account.getRate());
		
		String[] args = new String[] { account.getUser(), account.getName(), account.getUsername() };		
		int result = database.update(BankingDBOpenHelper.TABLE_ACCOUNT, values, "user=? AND name=? AND username=?", args);
		return (result != -1);
	}
	
	public boolean updateUser(User user) {
		ContentValues values = new ContentValues();
		values.put(BankingDBOpenHelper.COLUMN_TITLE, user.getName());
		values.put(BankingDBOpenHelper.COLUMN_DESC, user.getPassword());

		String [] args = new String[] { user.getName() };
		int result = database.update(BankingDBOpenHelper.TABLE_BANK, values, "user=?", args);
		return (result != -1);
	}
}
