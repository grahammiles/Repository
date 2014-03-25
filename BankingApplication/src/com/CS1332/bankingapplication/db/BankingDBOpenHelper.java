package com.CS1332.bankingapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BankingDBOpenHelper extends SQLiteOpenHelper {

	// private static final String LOGTAG = "EXPLOREDB";

	private static BankingDBOpenHelper instance = null;
	private static Context mCtx;
	private static final String DATABASE_NAME = "bank.db";
	private static final int DATABASE_VERSION = 21;

	
	// SETTING UP USER TABLE
	public static final String TABLE_BANK = "User";
	public static final String COLUMN_ID = "bankId";
	public static final String COLUMN_TITLE = "name";
	public static final String COLUMN_DESC = "password";
	public static final String COLUMN_INFO = "account";
	
	// SETTING UP ACCOUNT TABLE
	public static final String TABLE_ACCOUNT = "Account";
	public static final String ACCOUNT_ID = "accountId";
	public static final String ACCOUNT_USER = "user";
	public static final String ACCOUNT_NAME = "name";
	public static final String ACCOUNT_USERNAME = "username";
	public static final String ACCOUNT_INFO = "account";
	public static final String ACCOUNT_BALANCE = "balance";
	public static final String ACCOUNT_RATE = "rate";
	
	//SETTING UP DEPOSIT TABLE
	public static final String TABLE_DEPOSIT = "Deposit";
	public static final String DEPOSIT_ID = "depositId";
	public static final String DEPOSIT_USER = "user";
	public static final String DEPOSIT_NAME = "name";
	public static final String DEPOSIT_REASON = "reason";
	public static final String DEPOSIT_AMOUNT = "amount";
	public static final String DEPOSIT_TIME1 = "Time1";
	public static final String DEPOSIT_TIME2 = "Time2";
	
	// SETTING UP WITHDRAWAL TABLE
	public static final String TABLE_WITHDRAWAL = "Withdrawal";
	public static final String WITHDRAWAL_ID = "withdrawalId";
	public static final String WITHDRAWAL_USER = "user";
	public static final String WITHDRAWAL_NAME = "name";
	public static final String WITHDRAWAL_REASON = "reason";
	public static final String WITHDRAWAL_AMOUNT = "amount";
	public static final String WITHDRAWAL_TIME1 = "Time1";
	public static final String WITHDRAWAL_TIME2 = "Time2";

	private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_BANK + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE + " TEXT, " + COLUMN_DESC + " TEXT " + ")";

	private static final String TABLE_ACCOUNT_CREATE = "CREATE TABLE " + TABLE_ACCOUNT + " (" + ACCOUNT_ID + " INTEGER PRIMARY KEY, " + ACCOUNT_USER + " TEXT, " + ACCOUNT_NAME + " TEXT, " + ACCOUNT_USERNAME + " TEXT, " + ACCOUNT_BALANCE + " NUMERIC, " + ACCOUNT_RATE + " NUMERIC " + ")";
	
	private static final String TABLE_DEPOSIT_CREATE = "CREATE TABLE " + TABLE_DEPOSIT + " (" + DEPOSIT_ID + " INTEGER PRIMARY KEY, " + DEPOSIT_USER + " TEXT, " + DEPOSIT_NAME + " TEXT, " + DEPOSIT_REASON + " TEXT, " + DEPOSIT_AMOUNT + " NUMERIC, " + DEPOSIT_TIME1 + " NUMERIC, " + DEPOSIT_TIME2 + " NUMERIC " + ")";
	
	private static final String TABLE_WITHDRAWAL_CREATE = "CREATE TABLE " + TABLE_WITHDRAWAL + " (" + WITHDRAWAL_ID + " INTEGER PRIMARY KEY, " + WITHDRAWAL_USER + " TEXT, " + WITHDRAWAL_NAME + " TEXT, " + WITHDRAWAL_REASON + " TEXT, " + WITHDRAWAL_AMOUNT + " NUMERIC, " + WITHDRAWAL_TIME1 + " NUMERIC, " + WITHDRAWAL_TIME2 + " NUMERIC " + ")";


	private BankingDBOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		mCtx = context;
	}

	public static BankingDBOpenHelper getInstance(Context ctx) {
		if (instance == null) {
			
				instance = new BankingDBOpenHelper(ctx.getApplicationContext());
			
		}
		return instance;
	}

	public static BankingDBOpenHelper getInstance() {
		return getInstance(mCtx);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
		db.execSQL(TABLE_ACCOUNT_CREATE);
		db.execSQL(TABLE_DEPOSIT_CREATE);
		db.execSQL(TABLE_WITHDRAWAL_CREATE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BANK);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEPOSIT);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_WITHDRAWAL);

		onCreate(db);
	}

}
