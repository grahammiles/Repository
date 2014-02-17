package com.CS1332.bankingapplication.db;

import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

	public BankDataSource(Context context) {
		dbhelper = new BankingDBOpenHelper(context);
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
		Map<String, User> users = new HashMap<String, User>();
		Cursor cursor = database.query(BankingDBOpenHelper.TABLE_BANK, allColumns, null, null, null, null, null);
		
		Log.i(LOGTAG, "Returned " + cursor.getCount() + " rows");
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
	
}
