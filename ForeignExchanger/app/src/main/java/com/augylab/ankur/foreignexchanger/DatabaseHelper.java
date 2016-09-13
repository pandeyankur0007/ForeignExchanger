package com.augylab.ankur.foreignexchanger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ankur on 9/13/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {


    private static DatabaseHelper sInstance = null;

    private static final String TABLE_NAME = "recordInfo";  // tablename
    private static final String DATE = "date";  // column name
    private static final String CODE = "currency"; // column name
    private static final String QUANTITY = "quantity"; // column name
    private static final String PAIDVALUE = "buyvalue"; // column name
    private static final String TYPE = "type";
    private static final String HISTORY = "history";
    private static final String DATABASE_NAME = "recordDb"; // Dtabasename
    private static final int VERSION_CODE = 1; //versioncode of the database

    public static synchronized DatabaseHelper getInstance(Context context) {

        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_CODE);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String query;
        query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + CODE + " text, " + DATE + " text, " + QUANTITY + " integer, " + PAIDVALUE + " real, " + HISTORY + " text)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query;
        query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        onCreate(db);
    }

    public boolean saveRecord(AddRecord addRecord, boolean debitStatus) {
        // store column name and corresponding values

        String code = null;
        int qty = 0;
        double paidValue = 0;
        boolean usdResult = false;
        String history = null;

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + CODE + " = " + "'" + addRecord.getCode() + "'", null);
        if (cursor.moveToNext() == true) {
            code = cursor.getString(0);
            qty = cursor.getInt(2);
            paidValue = cursor.getDouble(3);
            history = cursor.getString(4);
            usdResult = true;

        }
        cursor.close();
        db.close();


        if ((debitStatus == false && usdResult == false) || (debitStatus == true && usdResult == false)) {
            ContentValues cv = new ContentValues();

            cv.put(CODE, addRecord.getCode());
            cv.put(QUANTITY, addRecord.getQty());
            cv.put(PAIDVALUE, addRecord.getPaidValue());
            cv.put(DATE, addRecord.getDate());

            // execute insert query with values from CV
            db = getWritableDatabase();
            long r = db.insert(TABLE_NAME, null, cv);
            db.close();

            if (r != -1)
                return true;
            else return false;
        } else if ((debitStatus == false && usdResult == true) || (debitStatus == true && usdResult == true)) {

            db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(CODE, addRecord.getCode());
            cv.put(QUANTITY, (qty - addRecord.getQty()));
            cv.put(PAIDVALUE, (paidValue - addRecord.getPaidValue()));
            cv.put(HISTORY, addRecord.getHistory() + history);
            Log.d("outcome", addRecord.getCode() + " " + (addRecord.getQty() - qty) + " " + (addRecord.getPaidValue() - paidValue));
            int result = db.update(TABLE_NAME, cv, "currency=?", new String[]{addRecord.getCode() + ""});
            Log.e("Result ", result + "");
            db.close();
            if (result == 1) return true;
            else return false;

        }

        return false;
    }

    public ArrayList<AddRecord> getRecord() {
        ArrayList<AddRecord> recordArrayList = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        while (cursor.moveToNext() == true) {
            String code = cursor.getString(0);
            int quantity = cursor.getInt(2);
            double paidValue = cursor.getDouble(3);
            String history = cursor.getString(4);
            recordArrayList.add(new AddRecord(code, quantity, paidValue, history));
        }
        cursor.close();
        db.close();

        return recordArrayList;
    }

    /*public ArrayList<String> getHistoryRecord() {
        ArrayList<AddRecord> recordArrayList = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        while (cursor.moveToNext()==true) {
            String history = cursor.getString(4);
            recordArrayList.add(history);
        }*/
}


