package com.yogeshojha.nammakarnatakatraffic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by y0g3sh on 16/12/16.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "rateManager";
    private static final String TABLE_RATE = "rate";
    private static final String KEY_ID = "id";
    private static final String KEY_LASTSHOWN = "lastshown";
    private static final String KEY_CLICKEDON = "clickedon";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RATE_TABLE = "CREATE TABLE " + TABLE_RATE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_LASTSHOWN + " TEXT,"
                + KEY_CLICKEDON + " TEXT" + ")";
        db.execSQL(CREATE_RATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RATE);
        onCreate(db);
    }
    void addRate(RateClass rate) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LASTSHOWN, rate.get_lastshown());
        values.put(KEY_CLICKEDON, rate.get_clickedon());
        db.insert(TABLE_RATE, null, values);
        db.close();
    }
    RateClass getRate(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RATE, new String[] { KEY_ID,
                        KEY_LASTSHOWN, KEY_CLICKEDON}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        RateClass contact = new RateClass(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        return contact;
    }
    public int getRateCount() {
        String countQuery = "SELECT * FROM " + TABLE_RATE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();

        cursor.close();
        db.close();

        return count ;

    }
    public int updateRate(RateClass rate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_LASTSHOWN, rate.get_lastshown());
        values.put(KEY_CLICKEDON, rate.get_clickedon());
        return db.update(TABLE_RATE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(rate.getID()) });
    }

}