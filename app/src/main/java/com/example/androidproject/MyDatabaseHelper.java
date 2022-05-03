package com.example.androidproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "VirtualMerchant.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_NAME1 = "items";
    private static final String COLUMN_IID = "iid";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_VALUE = "value";
    private static final String COLUMN_WEIGHT = "weight";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_AMOUNT = "amount";

    private static final String TABLE_NAME2 = "equipment";
    private static final String COLUMN_UID = "uid";

    private static final String TABLE_NAME3 = "shops";
    private static final String COLUMN_SID = "sid";


    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String items =
                "CREATE TABLE " + TABLE_NAME1 +
                        "(" + COLUMN_IID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_DESCRIPTION + " TEXT, " +
                        COLUMN_VALUE + " INTEGER, " +
                        COLUMN_WEIGHT + " INTEGER);";

        String shops =
                "CREATE TABLE " + TABLE_NAME3 +
                        "("+COLUMN_SID + " INTEGER, " +
                        COLUMN_IID + " TEXT, "  +
                        COLUMN_AMOUNT + " INTEGER);";


        String equipment =
                "CREATE TABLE " + TABLE_NAME2 +
                        "("+ COLUMN_UID + " INTEGER, " +
                        COLUMN_IID + " INTEGER , " +
                        COLUMN_AMOUNT + " INTEGER);";

        db.execSQL(items);
        db.execSQL(shops);
        db.execSQL(equipment);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        onCreate(db);
    }

    void addItem(String name, String description, int value, int weight){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_DESCRIPTION, description);
        cv.put(COLUMN_VALUE, value);
        cv.put(COLUMN_WEIGHT, weight);

        db.insert(TABLE_NAME1, null, cv);
    }

    void addItemToEquipment(int uid, int iid){

        String query = "SELECT * FROM " + TABLE_NAME2 + " WHERE " + COLUMN_IID + " = " + iid + " AND " + COLUMN_UID + " = " + uid + " ;";
        SQLiteDatabase rdb = this.getReadableDatabase();
        SQLiteDatabase wdb = this.getWritableDatabase();

        Cursor cursor = null;

        if(rdb != null){
            cursor = rdb.rawQuery(query,null);
            if(cursor.getCount() == 0){
                Log.d("NowyItem", "a");
                ContentValues cv = new ContentValues();

                cv.put(COLUMN_UID, uid);
                cv.put(COLUMN_IID, iid);
                cv.put(COLUMN_AMOUNT, 1);

                wdb.insert(TABLE_NAME2, null, cv);
            }
            else
            {
                Log.d("NowyItem", "b");
                ContentValues cv = new ContentValues();
                cursor.moveToNext();
                int old = cursor.getInt(2);
                cv.put(COLUMN_AMOUNT, old + 1);

                wdb.update(TABLE_NAME2, cv,COLUMN_UID + " = ? AND " + COLUMN_IID + " = ?" , new String[]{String.valueOf(uid), String.valueOf(iid)});
            }
        }
    }

    void deleteItemFromEquipment(int uid, int iid, int amount){

        SQLiteDatabase wdb = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_UID, uid);
        cv.put(COLUMN_IID, iid);
        cv.put(COLUMN_AMOUNT, amount);

        wdb.update(TABLE_NAME2, cv,COLUMN_UID + " = ? AND " + COLUMN_IID + " = ?" , new String[]{String.valueOf(uid), String.valueOf(iid)});
    }


    Cursor readData(int uid){
        String query = "SELECT it.*, eq.amount FROM " + TABLE_NAME2 + " AS eq " +
                " JOIN " + TABLE_NAME1+ " AS it ON eq.iid = it.iid "+
                " WHERE eq.uid = " + uid + " AND eq.amount > 0 ";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query,null);
        }

        return cursor;
    }
}
