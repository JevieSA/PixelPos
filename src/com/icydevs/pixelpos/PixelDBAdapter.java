package com.icydevs.pixelpos;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PixelDBAdapter {

	PixelPosDB helper;

	public PixelDBAdapter(Context c) {
		helper = new PixelPosDB(c);
		Log.d("-- DB core --", PixelPosDB.CREATE_USERS_TABLE);
		Log.d("-- DB core --", PixelPosDB.CREATE_INVOICE_TABLE);
	}

	public long deleteRows(String name) {
		SQLiteDatabase db = helper.getWritableDatabase();
		String[] whereArgs = { name };
		int count = db.delete(PixelPosDB.TABLE_USERS, PixelPosDB.NAME + " =? ",
				whereArgs);
		return count;
	}

	// -- Test Method --
	// -- Test Method -- Retrieve all data from TABLE_USERS
	public String getAllData() {
		SQLiteDatabase db = helper.getWritableDatabase();
		String[] columns = { PixelPosDB.UID, PixelPosDB.NAME,
				PixelPosDB.PASSWORD };
		Cursor cursor = db.query(PixelPosDB.TABLE_USERS, columns, null, null,
				null, null, null);

		StringBuffer buffer = new StringBuffer();

		while (cursor.moveToNext()) {
			int index1 = cursor.getColumnIndex(PixelPosDB.UID);
			int index2 = cursor.getColumnIndex(PixelPosDB.NAME);
			int index3 = cursor.getColumnIndex(PixelPosDB.PASSWORD);

			int cid = cursor.getInt(index1);
			String name = cursor.getString(index2);
			String pass = cursor.getString(index3);

			buffer.append(cid + " " + name + " " + pass + "\n");

		}
		cursor.close();
		return buffer.toString();

	}

	// || Table_ITEMS ||
	// Retrieve specific item data defined by the passed parameter(String
	// itemName)
	public ArrayList<ProductItem> getItemData(String itemName) {
		SQLiteDatabase db = helper.getWritableDatabase();
		String[] columns = { PixelPosDB.ITEM_ID, PixelPosDB.ITEM_QUANTITY,
				PixelPosDB.ITEM_NAME, PixelPosDB.ITEM_PRICE_SELL };
		Cursor cursor = db.query(PixelPosDB.TABLE_ITEMS, columns, "UPPER ("
				+ PixelPosDB.ITEM_NAME + ") LIKE '%" + itemName + "%'", null,
				null, null, null);

		ArrayList<ProductItem> listSearch = new ArrayList<ProductItem>();

		while (cursor.moveToNext()) {
			int index1 = cursor.getColumnIndex(PixelPosDB.ITEM_QUANTITY);
			int index2 = cursor.getColumnIndex(PixelPosDB.ITEM_NAME);
			int index3 = cursor.getColumnIndex(PixelPosDB.ITEM_PRICE_SELL);
			int index4 = cursor.getColumnIndex(PixelPosDB.ITEM_ID);

			int quant = cursor.getInt(index1);
			String name = cursor.getString(index2);
			double price = cursor.getDouble(index3);
			String id = cursor.getString(index4);

			ProductItem tempItem = new ProductItem(quant, name, price);

			tempItem.setId(id);
			tempItem.setQuantity(quant);
			tempItem.setDescription(name);
			tempItem.setPriceSell(price);

			listSearch.add(tempItem);
			Log.d("-- DB -- POPULATION CHECK",
					"tempItem = " + tempItem.toString());
		}
		cursor.close();
		return listSearch;
	}

	// -- Test Method -- || TABLE_ITEMS ||
	// -- Test Method -- Retrieve all data from TABLE_ITEMS
	public String getAllItemData() {
		SQLiteDatabase db = helper.getWritableDatabase();
		String[] columns = { PixelPosDB.ITEM_ID, PixelPosDB.ITEM_NAME,
				PixelPosDB.ITEM_QUANTITY, PixelPosDB.ITEM_PRICE_SELL };
		Cursor cursor = db.query(PixelPosDB.TABLE_ITEMS, columns, null, null,
				null, null, null);

		StringBuffer buffer = new StringBuffer();

		while (cursor.moveToNext()) {
			int index1 = cursor.getColumnIndex(PixelPosDB.ITEM_ID);
			int index2 = cursor.getColumnIndex(PixelPosDB.ITEM_NAME);
			int index3 = cursor.getColumnIndex(PixelPosDB.ITEM_QUANTITY);
			int index4 = cursor.getColumnIndex(PixelPosDB.ITEM_PRICE_SELL);

			String cid = cursor.getString(index1);
			String name = cursor.getString(index2);
			String quant = cursor.getString(index3);
			String price = cursor.getString(index4);

			buffer.append("id: " + cid + " Desc: " + name + " Quant: " + quant
					+ " price: " + price + "\n");

		}
		cursor.close();
		return buffer.toString();

	}

	// || TABLE_INVOICE ||
	// -- TEST -- Retrieve all data from TABLE_INVOICE
	public String getAllInvoiceData() {

		SQLiteDatabase db = helper.getWritableDatabase();
		String[] columns = { PixelPosDB.INVOICE_NUMBER,
				PixelPosDB.INVOICE_DATE, PixelPosDB.INVOICE_TOTAL };
		Cursor cursor = db.query(PixelPosDB.TABLE_INVOICES, columns, null,
				null, null, null, null);

		StringBuffer buffer = new StringBuffer();

		while (cursor.moveToNext()) {
			int index1 = cursor.getColumnIndex(PixelPosDB.INVOICE_NUMBER);
			int index2 = cursor.getColumnIndex(PixelPosDB.INVOICE_DATE);
			int index3 = cursor.getColumnIndex(PixelPosDB.INVOICE_TOTAL);

			String id = cursor.getString(index1);
			String date = cursor.getString(index2);
			String tot = cursor.getString(index3);

			buffer.append("ID: " + id + " Date: " + date + " Time: "
					+ " Total: " + tot + "\n");

		}
		cursor.close();
		return buffer.toString();

	}

	// || TABLE_ITEMS_SOLD ||
	// -- TEST -- Display all items in table
	public String getAllItemsSoldData() {
		SQLiteDatabase db = helper.getWritableDatabase();
		String[] columns = { PixelPosDB.ITEM_SOLD_INVOICE_NUM,
				PixelPosDB.ITEM_SOLD_ID, PixelPosDB.ITEM_SOLD_QUANTITY };
		Cursor cursor = db.query(PixelPosDB.TABLE_ITEMS_SOLD, columns, null,
				null, null, null, null);

		StringBuffer buffer = new StringBuffer();

		while (cursor.moveToNext()) {
			int index1 = cursor
					.getColumnIndex(PixelPosDB.ITEM_SOLD_INVOICE_NUM);
			int index2 = cursor.getColumnIndex(PixelPosDB.ITEM_SOLD_ID);
			int index3 = cursor.getColumnIndex(PixelPosDB.ITEM_SOLD_QUANTITY);

			String invId = cursor.getString(index1);
			String is = cursor.getString(index2);
			String quant = cursor.getString(index3);

			buffer.append("ID: " + invId + " Date: " + is + " Time: "
					+ " Total: " + quant + "\n");

		}
		cursor.close();
		return buffer.toString();
	}

	// || TABLE_USERS ||
	// Test method to check if there is a duplicate user name, false if not,
	// true if there is a duplicate
	public boolean testDuplicate(String name) {
		boolean test = false;
		SQLiteDatabase db = helper.getWritableDatabase();
		String[] columns = { PixelPosDB.NAME };
		Cursor cursor = db.query(PixelPosDB.TABLE_USERS, columns,
				PixelPosDB.NAME + " = '" + name + "'", null, null, null, null);

		if (cursor.moveToFirst()) {
			test = true;
		}

		return test;
	}

	// || TABLE_USERS ||
	// Validates user login details, returns false if invalid and true if valid
	public boolean validateUserLogin(String name, String pass) {
		boolean test = false;

		SQLiteDatabase db = helper.getWritableDatabase();
		String[] columns = { PixelPosDB.NAME, PixelPosDB.PASSWORD };
		try {
			Cursor cursor = db.query(PixelPosDB.TABLE_USERS, columns,
					PixelPosDB.NAME + " = '" + name + "'", null, null, null,
					null);
			if (cursor != null) {
				cursor.moveToFirst();
				if (cursor.getString(cursor.getColumnIndex(PixelPosDB.NAME))
						.equals(name)
						&& cursor.getString(
								cursor.getColumnIndex(PixelPosDB.PASSWORD))
								.equals(pass)) {
					test = true;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return test;
	}

	// || TABLE_USERS ||
	// Insert new user into TABLE_USERS
	public long insert(String name, String password) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		contentValues.put(PixelPosDB.NAME, name);
		contentValues.put(PixelPosDB.PASSWORD, password);

		long id = db.insert(PixelPosDB.TABLE_USERS, null, contentValues);

		return id;
	}

	// || TABLE_ITEMS ||
	// Insert new item into TABLE_ITEMS
	public long insertItem(String itemId, String description, double priceBuy,
			double priceSell, int quantity, String category, String supplier) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues contentVals = new ContentValues();

		contentVals.put(PixelPosDB.ITEM_ID, itemId);
		contentVals.put(PixelPosDB.ITEM_NAME, description);
		contentVals.put(PixelPosDB.ITEM_PRICE_BUY, priceBuy);
		contentVals.put(PixelPosDB.ITEM_PRICE_SELL, priceSell);
		contentVals.put(PixelPosDB.ITEM_QUANTITY, quantity);
		contentVals.put(PixelPosDB.ITEM_CATAGORY, category);
		contentVals.put(PixelPosDB.ITEM_SUPPLIER, supplier);

		long id = db.insert(PixelPosDB.TABLE_ITEMS, null, contentVals);

		return id;
	}

	// || TABLE_ITEMS ||
	// Inserts a new invoice into the invoice table and adds all the items to
	// the sold items list
	@SuppressLint("SimpleDateFormat")
	public void addNewItems(ArrayList<ProductItem> itemList) {
		SQLiteDatabase db = helper.getWritableDatabase();

		try {

			for (ProductItem temp : itemList) {
				// Change item quantities in DB
				updateItemQuantityAdd(temp.getId(), temp.getQuantity());
			}

		} catch (Exception e) {
			Log.d("-- DB --", "TABLE_ITEMS insert not successfull");
			e.printStackTrace();
		}

	}

	// || TABLE_INVOICE ||
	// Inserts a new invoice into the invoice table and adds all the items to
	// the sold items list
	@SuppressLint("SimpleDateFormat")
	public void addInvoiceData(ArrayList<ProductItem> itemList, double tot,
			String method) {
		SQLiteDatabase db = helper.getWritableDatabase();

		ContentValues contentValsInv = new ContentValues();

		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System
				.currentTimeMillis()));

		contentValsInv.put(PixelPosDB.INVOICE_DATE, date);
		contentValsInv.put(PixelPosDB.INVOICE_TOTAL, tot);
		contentValsInv.put(PixelPosDB.PAYMENT_METHOD, method);

		long id = 0;

		try {
			id = db.insert(PixelPosDB.TABLE_INVOICES, null, contentValsInv);
			if (id > 0) {
				Log.d("-- DB --", "TABLE_INVOICES insert completed");
			}
		} catch (Exception e) {
			Log.d("-- DB --", "TABLE_INVOICES insert not completed");
			e.printStackTrace();
		}

		try {

			if (id != 0) {
				for (ProductItem temp : itemList) {
					ContentValues contentVals = new ContentValues();

					contentVals.put(PixelPosDB.ITEM_SOLD_ID, temp.getId());
					contentVals.put(PixelPosDB.ITEM_SOLD_INVOICE_NUM, id);
					contentVals
							.put(PixelPosDB.ITEM_NAME, temp.getDescription());
					contentVals.put(PixelPosDB.ITEM_PRICE_SELL,
							temp.getPriceSell());
					contentVals.put(PixelPosDB.ITEM_QUANTITY,
							temp.getQuantity());
					contentVals.put(PixelPosDB.ITEM_CATAGORY,
							temp.getCategory());
					contentVals.put(PixelPosDB.ITEM_SUPPLIER,
							temp.getSupplier());

					long result = db.insert(PixelPosDB.TABLE_ITEMS_SOLD, null,
							contentVals);

					// Change item quantities in DB
					updateItemQuantityRemove(temp.getId(), temp.getQuantity());
				}
			}
		} catch (Exception e) {
			Log.d("-- DB --", "TABLE_ITEMS_SOLD insert not successfull");
			e.printStackTrace();
		}

	}

	// || TABLE_INVOICES ||
	// Find all the invoices for a certain date and calculate the totals by
	// payment method
	public String getDayEndTotals(int year, int month, int day) {
		String display = "";
		try {
			String date = year + "-" + month + "-" + day;

			SQLiteDatabase db = helper.getWritableDatabase();

			String[] columns = { PixelPosDB.PAYMENT_METHOD,
					PixelPosDB.INVOICE_TOTAL };

			Cursor cursor = db.query(PixelPosDB.TABLE_INVOICES, columns,
					PixelPosDB.INVOICE_DATE + " LIKE '%" + date + "%'", null,
					null, null, null);
			if (cursor != null) {
				double cashTot = 0, ccTot = 0, dcTot = 0, ddTot = 0;

				while (cursor.moveToNext()) {
					int index1 = cursor
							.getColumnIndex(PixelPosDB.PAYMENT_METHOD);
					int index2 = cursor
							.getColumnIndex(PixelPosDB.INVOICE_TOTAL);

					String method = cursor.getString(index1);
					double tot = cursor.getDouble(index2);

					if (method.equals("Cash")) {
						cashTot = cashTot + tot;
					}
					if (method.equals("Credit Card")) {
						ccTot = ccTot + tot;
					}
					if (method.equals("Debit Card")) {
						dcTot = dcTot + tot;
					}
					if (method.equals("Direct Deposit")) {
						ddTot = ddTot + tot;
					}
				}
				display = "Totals for " + date + ":\nCash: " + cashTot
						+ "\nCredit Card: " + ccTot + "\nDebit Card: " + dcTot
						+ "\nDirect Deposit: " + ddTot;
			}
		} catch (Exception e) {
			Log.d("-- Display Daily Totals --", "Failed");
			e.printStackTrace();
		}

		if (display == null || display == "") {
			display = "There is no data for that day!";
		}
		Log.d("-- Totals Display Test --", display);
		return display;
	}

	// || TABLE_CREDIT_NOTES ||
	// Inserts a new credit note into the credit note table and adds all the
	// items to
	// the returned items list
	public void addCreditNoteData(ArrayList<ProductItem> itemList, double tot,
			String method) {
		SQLiteDatabase db = helper.getWritableDatabase();

		ContentValues contentValsInv = new ContentValues();

		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date(System.currentTimeMillis()));

		contentValsInv.put(PixelPosDB.CN_DATE, date);
		contentValsInv.put(PixelPosDB.CN_TOTAL, tot);
		contentValsInv.put(PixelPosDB.CREDIT_METHOD, method);

		long id = 0;

		try {
			id = db.insert(PixelPosDB.TABLE_CREDIT_NOTES, null, contentValsInv);
			if (id > 0) {
				Log.d("-- DB --", "TABLE_CREDIT_NOTES insert completed");
			}
		} catch (Exception e) {
			Log.d("-- DB --", "TABLE_CREDIT_NOTES insert not completed");
			e.printStackTrace();
		}

		try {

			if (id != 0) {
				for (ProductItem temp : itemList) {
					ContentValues contentVals = new ContentValues();

					contentVals.put(PixelPosDB.ITEM_RETURNED_ID, temp.getId());
					contentVals.put(PixelPosDB.ITEM_RETURNED_CREDIT_NUM, id);
					contentVals.put(PixelPosDB.ITEM_RETURNED_NAME,
							temp.getDescription());
					contentVals.put(PixelPosDB.ITEM_RETURNED_PRICE,
							temp.getPriceSell());
					contentVals.put(PixelPosDB.ITEM_RETURNED_QUANTITY,
							temp.getQuantity());
					contentVals.put(PixelPosDB.ITEM_RETURNED_CATEGORY,
							temp.getCategory());
					contentVals.put(PixelPosDB.ITEM_RETURNED_SUPPLIER,
							temp.getSupplier());

					long result = db.insert(PixelPosDB.TABLE_ITEMS_RETURNED,
							null, contentVals);

					// Change item quantities in DB
					updateItemQuantityAdd(temp.getId(), temp.getQuantity());
				}
			}
		} catch (Exception e) {
			Log.d("-- DB --", "TABLE_ITEMS_RETURNED insert not successfull");
			e.printStackTrace();
		}

	}

	// || TABLE_ITEMS ||
	// Updates the item quantities when finalize is clicked on the invoice
	// screen
	public void updateItemQuantityRemove(String id, int out) {
		// UPDATE DBHelper.TABLE_ITEMS SET DBHelper.ITEM_ID='+id+' where
		// DBHelper.NAME='__'
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		try {
			String[] columns = { PixelPosDB.ITEM_QUANTITY };
			Cursor get = db.query(PixelPosDB.TABLE_ITEMS, columns,
					PixelPosDB.ITEM_ID + " = '" + id + "'", null, null, null,
					null);

			if (get.moveToFirst()) {
				Log.d("-- DB UPDATE --", "Cursor is not empty");
				int quant = get.getInt(0);

				int update = quant - out;

				contentValues.put(PixelPosDB.ITEM_QUANTITY, update);
				String[] whereArgs = { id };
				int count = db.update(PixelPosDB.TABLE_ITEMS, contentValues,
						PixelPosDB.ITEM_ID + " =? ", whereArgs);
			}
		} catch (Exception e) {
			Log.d("-- DB UPDATE --", "TABLE_ITEMS udpate failed");
			e.printStackTrace();
		}
	}

	// || TABLE_ITEMS ||
	// Updates the item quantities when finalize is clicked on the invoice
	// screen
	public void updateItemQuantityAdd(String id, int out) {
		// UPDATE DBHelper.TABLE_ITEMS SET DBHelper.ITEM_ID='+id+' where
		// DBHelper.NAME='__'
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		try {
			String[] columns = { PixelPosDB.ITEM_QUANTITY };
			Cursor get = db.query(PixelPosDB.TABLE_ITEMS, columns,
					PixelPosDB.ITEM_ID + " = '" + id + "'", null, null, null,
					null);

			if (get.moveToFirst()) {
				Log.d("-- DB UPDATE --", "Cursor is not empty");
				int quant = get.getInt(0);

				int update = quant + out;

				contentValues.put(PixelPosDB.ITEM_QUANTITY, update);
				String[] whereArgs = { id };
				int count = db.update(PixelPosDB.TABLE_ITEMS, contentValues,
						PixelPosDB.ITEM_ID + " =? ", whereArgs);
			}
		} catch (Exception e) {
			Log.d("-- DB UPDATE --", "TABLE_ITEMS udpate failed");
			e.printStackTrace();
		}
	}

	// ** Database Helper -- Hidden within PixelDBAdapter to try and avoid **
	// tampering with DB --
	static class PixelPosDB extends SQLiteOpenHelper {

		private static final String DB_NAME = "PIXEL_POS_DB";
		private static final int version = 20;
		Context context;

		// CREATE TABLE TABLE_USERS (_id INTEGER PRIMARY KEY AUTOINCREMENT,
		// user_name VARCHAR(255), user_pass VARCHAR(255));
		private static final String TABLE_USERS = "TABLE_USERS";
		private static final String UID = "_id";
		private static final String NAME = "user_name";
		private static final String PASSWORD = "user_pass";

		private static final String CREATE_USERS_TABLE = "CREATE TABLE "
				+ TABLE_USERS + " (" + UID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME
				+ " VARCHAR(255), " + PASSWORD + " VARCHAR(255));";

		// CREATE TABLE TABLE_ITEMS (_id text PRIMARY KEY
		// , ITEM_NAME VARCHAR(255), PRICE_BUY REAL,
		// PRICE_SELL NOT NULL, ITEM_QUANTITY INTEGER NOT NULL, ITEM_CATAGORY
		// VARCHAR(100), ITEM_SUPPLIER VARCHAR (100));
		private static final String TABLE_ITEMS = "TABLE_ITEMS";
		private static final String ITEM_ID = "_id";
		private static final String ITEM_NAME = "name";
		private static final String ITEM_PRICE_BUY = "price_buy";
		private static final String ITEM_PRICE_SELL = "price_sell";
		private static final String ITEM_QUANTITY = "quantity";
		private static final String ITEM_CATAGORY = "catagory";
		private static final String ITEM_SUPPLIER = "supplier";

		private static final String CREATE_ITEMS_TABLE = " CREATE TABLE "
				+ TABLE_ITEMS + " (" + ITEM_ID + " VARCHAR(100) PRIMARY KEY ,"
				+ ITEM_NAME + " VARCHAR(255), " + ITEM_PRICE_BUY + " REAL, "
				+ ITEM_PRICE_SELL + " REAL NOT NULL, " + ITEM_QUANTITY
				+ " INTEGER NOT NULL, " + ITEM_CATAGORY + " VARCHAR(100), "
				+ ITEM_SUPPLIER + " VARCHAR (100));";

		// CREATE TABLE TABLE_ITEMS_SOLD (id VARCHAR(100)
		// , ITEM_SOLD_INVOICE_NUM VARCHAR(100), ITEM_SOLD_NAME VARCHAR(100),
		// PRICE_SELL NOT NULL, ITEM_QUANTITY INTEGER NOT NULL, ITEM_CATEGORY
		// VARCHAR(100), ITEM_SUPPLIER VARCHAR (100));
		private static final String TABLE_ITEMS_SOLD = "TABLE_ITEMS_SOLD";
		private static final String ITEM_SOLD_INVOICE_NUM = "invoice_number";
		private static final String ITEM_SOLD_ID = "id";
		private static final String ITEM_SOLD_NAME = "name";
		private static final String ITEM_SOLD_PRICE_SELL = "price_sell";
		private static final String ITEM_SOLD_QUANTITY = "quantity";
		private static final String ITEM_SOLD_CATAGORY = "catagory";
		private static final String ITEM_SOLD_SUPPLIER = "supplier";

		private static final String CREATE_ITEMS_SOLD_TABLE = " CREATE TABLE "
				+ TABLE_ITEMS_SOLD + " (" + ITEM_SOLD_INVOICE_NUM
				+ " INTEGER NOT NULL," + ITEM_SOLD_ID + " VARCHAR(100) , "
				+ ITEM_SOLD_NAME + " VARCHAR(100), " + ITEM_SOLD_PRICE_SELL
				+ " REAL NOT NULL, " + ITEM_SOLD_QUANTITY
				+ " INTEGER NOT NULL, " + ITEM_SOLD_CATAGORY
				+ " VARCHAR(100), " + ITEM_SOLD_SUPPLIER + " VARCHAR (100));";

		// CREATE TABLE TABLE_ITEMS_RETURNED (id VARCHAR(100),
		// ITEM_RETURNED_CREDIT_NUM VARCHAR(100), ITEM_RETURNED_NAME
		// VARCHAR(100),
		// ITEM_RETURNED_PRICE NOT NULL, ITEM_RETURNED_QUANTITY INTEGER NOT
		// NULL, ITEM_RETURNED_CATEGORY VARCHAR(100), ITEM_RETURNED_SUPPLIER
		// VARCHAR(100));
		private static final String TABLE_ITEMS_RETURNED = "TABLE_ITEMS_RETURNED";
		private static final String ITEM_RETURNED_ID = "id";
		private static final String ITEM_RETURNED_CREDIT_NUM = "invoice_number";
		private static final String ITEM_RETURNED_NAME = "name";
		private static final String ITEM_RETURNED_PRICE = "price";
		private static final String ITEM_RETURNED_QUANTITY = "quantity";
		private static final String ITEM_RETURNED_CATEGORY = "category";
		private static final String ITEM_RETURNED_SUPPLIER = "supplier";
		private static final String CREATE_TABLE_ITEM_RETURNED = "CREATE TABLE "
				+ TABLE_ITEMS_RETURNED
				+ " ("
				+ ITEM_RETURNED_ID
				+ " VARCHAR(100), "
				+ ITEM_RETURNED_CREDIT_NUM
				+ " VARCHAR(100), "
				+ ITEM_RETURNED_NAME
				+ " VARCHAR(100), "
				+ ITEM_RETURNED_PRICE
				+ " NOT NULL, "
				+ ITEM_RETURNED_QUANTITY
				+ " INTEGER NOT NULL, "
				+ ITEM_RETURNED_CATEGORY
				+ " VARCHAR(100), "
				+ ITEM_RETURNED_SUPPLIER
				+ " VARCHAR(100));";

		// CREATE TABLE TABLE_INVOICES (INVOICE_NUMBER INTEGER PRIMARY KEY
		// AUTOINCREMENT
		// , INVOICE_DATE VARCHAR(100) NOT NULL,
		// SALES_PERSON VARCHAR(100) NOT NULL, PAYMENT_METHOD VARCHAR(100) NOT
		// NULL, INVOICE_TOTAL REAL NOT NULL);
		private static final String TABLE_INVOICES = "TABLE_INVOICES";
		private static final String INVOICE_NUMBER = "_invoice_num";
		private static final String INVOICE_DATE = "date";
		private static final String SALES_PERSON = "sales_person";
		private static final String PAYMENT_METHOD = "payment_method";
		private static final String INVOICE_TOTAL = "total";

		private static final String CREATE_INVOICE_TABLE = "CREATE TABLE "
				+ TABLE_INVOICES + " (" + INVOICE_NUMBER
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + INVOICE_DATE
				+ " VARCHAR(100) NOT NULL, " + SALES_PERSON + " VARCHAR(100), "
				+ PAYMENT_METHOD + " VARCHAR(100) NOT NULL, " + INVOICE_TOTAL
				+ " REAL NOT NULL);";

		// CREATE TABLE TABLE_CREDIT_NOTES (CN_NUMBER text PRIMARY KEY
		// , CN_DATE DATE NOT NULL, CN_TIME TIME NOT NULL,
		// CN_SALES_PERSON VARCHAR(100) NOT NULL, CREDIT_METHOD VARCHAR(100) NOT
		// NULL, CN_TOTAL REAL NOT NULL);
		private static final String TABLE_CREDIT_NOTES = "TABLE_CREDIT_NOTES";
		private static final String CN_NUMBER = "_credit_note_number";
		private static final String CN_DATE = "date";
		private static final String CN_SALES_PERSON = "sales_person";
		private static final String CREDIT_METHOD = "credit_method";
		private static final String CN_TOTAL = "total";

		private static final String CREATE_TABLE_CREDITS = "CREATE TABLE "
				+ TABLE_CREDIT_NOTES + " (" + CN_NUMBER + " text PRIMARY KEY, "
				+ CN_DATE + " DATE NOT NULL, " + CN_SALES_PERSON
				+ " VARCHAR(100), " + CREDIT_METHOD
				+ " VARCHAR(100) NOT NULL, " + CN_TOTAL + " REAL NOT NULL);";

		// Drop Table statement -- executed when DB_VERSION is changed
		// (increased) --
		// DROP TABLE IF EXISTS TABLE_USERS
		private static final String DROP_TABLE_USERS = "DROP TABLE IF EXISTS "
				+ TABLE_USERS;
		// DROP TABLE IF EXISTS TABLE_ITEMS
		private static final String DROP_TABLE_ITEMS = "DROP TABLE IF EXISTS "
				+ TABLE_ITEMS;
		// DROP TABLE IF EXISTS TABLE_INVOICES
		private static final String DROP_TABLE_INVOICES = "DROP TABLE IF EXISTS "
				+ TABLE_INVOICES;
		// DROP TABLE IF EXISTS TABLE_ITEMS_SOLD
		private static final String DROP_TABLE_ITEMS_SOLD = "DROP TABLE IF EXISTS "
				+ TABLE_ITEMS_SOLD;
		// DROP TABLE IF EXISTS TABLE_ITEMS_RETURNED
		private static final String DROP_TABLE_ITEMS_RETURNED = "DROP TABLE IF EXISTS "
				+ CREATE_TABLE_ITEM_RETURNED;
		// DROP TABLE IF EXISTS TABLE_CREDIT_NOTES
		private static final String DROP_TABLE_CREDITS = "DROP TABLE IF EXISTS "
				+ TABLE_CREDIT_NOTES;

		// Constructor for Table Users item
		public PixelPosDB(Context context) {
			super(context, DB_NAME, null, version);
			this.context = context;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL(CREATE_USERS_TABLE);
				db.execSQL(CREATE_ITEMS_TABLE);
				db.execSQL(CREATE_INVOICE_TABLE);
				db.execSQL(CREATE_ITEMS_SOLD_TABLE);
				db.execSQL(CREATE_TABLE_ITEM_RETURNED);
				db.execSQL(CREATE_TABLE_CREDITS);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			try {

				db.execSQL(DROP_TABLE_ITEMS);
				db.execSQL(DROP_TABLE_USERS);
				db.execSQL(DROP_TABLE_INVOICES);
				db.execSQL(DROP_TABLE_CREDITS);
				db.execSQL(DROP_TABLE_ITEMS_SOLD);
				db.execSQL(DROP_TABLE_ITEMS_RETURNED);
				onCreate(db);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
