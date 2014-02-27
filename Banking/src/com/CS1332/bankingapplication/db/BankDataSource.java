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
import com.CS1332.bankingapplication.models.User;

public class BankDataSource {
	
	public static final String LOGTAG = "DB";
	
	 SQLiteOpenHelper dbhelper;
	 SQLiteDatabase database;
	 Map<String, User> users;
	 
	 private static final String[] allColumns = {
		 BankingDBOpenHelper.COLUMN_ID,
		 BankingDBOpenHelper.COLUMN_TITLE,
		 BankingDBOpenHelper.COLUMN_DESC
	 };
	 
	 private static final String[] accountColumns = {
		 BankingDBOpenHelper.ACCOUNT_ID,
		 BankingDBOpenHelper.ACCOUNT_USER,
		 BankingDBOpenHelper.ACCOUNT_NAME,
		 BankingDBOpenHelper.ACCOUNT_USERNAME,
		 BankingDBOpenHelper.ACCOUNT_BALANCE,
		 BankingDBOpenHelper.ACCOUNT_RATE
	 };

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
	
	public User create(User user) {
		ContentValues values = new ContentValues();
		values.put(BankingDBOpenHelper.COLUMN_TITLE, user.getName());
		values.put(BankingDBOpenHelper.COLUMN_DESC, user.getPassword());
		long insertid = database.insert(BankingDBOpenHelper.TABLE_BANK, null, values);
		user.setId(insertid);
		return user;

	}
	
	public Map<String, User> findAll() {
		Cursor cursor = database.query(BankingDBOpenHelper.TABLE_BANK, allColumns, null, null, null, null, null);
		
		Log.i(LOGTAG, "Returned " + cursor.getCount() + " rows");
		Map<String, User> users = cursorToMap(cursor);
		return users;
		
	}
	public List<Account> findFiltered(String[] selectionArgs, String orderBy) {
		Cursor cursor = database.query(BankingDBOpenHelper.TABLE_ACCOUNT, accountColumns, "user=?", selectionArgs, null, null, orderBy);
		
		Log.i(LOGTAG, "Returned " + cursor.getCount() + " rows");
		List<Account> accounts = cursorToList(cursor);
		return accounts;
		
	}

	private Map<String, User> cursorToMap(Cursor cursor) {
		Map<String, User> users = new HashMap<String, User>();
		if (cursor.getCount() > 0) {
			while(cursor.moveToNext()) {
				User user = new User();
				user.setId(cursor.getLong(cursor.getColumnIndex(BankingDBOpenHelper.COLUMN_ID)));
				user.setName(cursor.getString(cursor.getColumnIndex(BankingDBOpenHelper.COLUMN_TITLE)));
				user.setPassword(cursor.getString(cursor.getColumnIndex(BankingDBOpenHelper.COLUMN_DESC)));
				users.put(user.getName(), user);
			}
		}
		return users;
	}
	
	private List<Account> cursorToList(Cursor cursor) {
		List<Account> accounts = new ArrayList<Account>();
		if (cursor.getCount() > 0) {
			while(cursor.moveToNext()) {
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
	
}
