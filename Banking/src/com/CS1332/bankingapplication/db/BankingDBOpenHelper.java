package com.CS1332.bankingapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BankingDBOpenHelper extends SQLiteOpenHelper {
	
//	private static final String LOGTAG = "EXPLOREDB";

	private static final String DATABASE_NAME = "bank.db";
	private static final int DATABASE_VERSION = 11;
	
	public static final String TABLE_BANK = "User";
	public static final String COLUMN_ID = "bankId";
	public static final String COLUMN_TITLE = "name";
	public static final String COLUMN_DESC = "password";
	
	private static final String TABLE_CREATE = 
			"CREATE TABLE " + TABLE_BANK + " (" +
			COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			COLUMN_TITLE + " TEXT, " +
			COLUMN_DESC + " TEXT " +
			")";
			
			

	public BankingDBOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BANK);
		onCreate(db);
	}

}
