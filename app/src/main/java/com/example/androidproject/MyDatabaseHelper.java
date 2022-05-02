package com.example.androidproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "VirtualMerchant.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_NAME = "items";
    private static final String COLUMN_ID = "iid";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_VALUE = "value";
    private static final String COLUMN_WEIGHT = "weight";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_AMOUNT = "amount";


    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_DESCRIPTION + " TEXT, " +
                        COLUMN_VALUE + " INTEGER, " +
                        COLUMN_WEIGHT + " INTEGER, " +
                        COLUMN_AMOUNT + " INTEGER);";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addItem(String name, String description, int value, int weight, int amount){
        Log.d("testsssss","a");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_DESCRIPTION, description);
        cv.put(COLUMN_VALUE, value);
        cv.put(COLUMN_WEIGHT, weight);
        cv.put(COLUMN_AMOUNT, amount);

        db.insert(TABLE_NAME, null, cv);
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query,null);
        }

        return cursor;
    }

}
