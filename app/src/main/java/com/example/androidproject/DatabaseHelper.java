package com.example.androidproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME = "VirtualMerchant.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "users";

    public static final String COLUMN_UID = "uid";
    public static final String COLUMN_LOGIN = "login";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_SECURITY_QUESTION = "security_question";
    public static final String COLUMN_SECURITY_ANSWER = "security_answer";
    public static final String COLUMN_PROFILE_IMAGE = "profile_image_url";
    public static final String COLUMN_MONEY = "money";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE users (\n" +
                "uid INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "login VARCHAR NOT NULL,\n" +
                "email VARCHAR NOT NULL,\n" +
                "password VARCHAR NOT NULL,\n" +
                "security_question VARCHAR NOT NULL,\n" +
                "security_answer VARCHAR NOT NULL,\n" +
                "profile_image_url VARCHAR DEFAULT 0,\n" +
                "money numeric DEFAULT 100,\n" +
                "CONSTRAINT users_email_key UNIQUE (email),\n" +
                "CONSTRAINT users_login_key UNIQUE (login)\n" +
                ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        onCreate(db);
    }

    //Creates account
    public void createAccount(String login, String email, String password, String security_question, String security_answer, String profile_image_url, float money)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("login",login);
        cv.put("email",email);
        cv.put("password",password);
        cv.put("security_question",security_question);
        cv.put("security_answer",security_answer);
        cv.put("profile_image_url",profile_image_url);
        cv.put("money",money);

        long result = db.insert(TABLE_NAME, null, cv);
        if(result==-1)
        {
            Toast.makeText(context, "Failed to create account.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Account created!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);

        }

    }

    //Check if user exists in DB, only for log in
    public boolean checkUser(String login, String password) {
        String[] columns = {
                COLUMN_UID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_LOGIN + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";

        String[] selectionArgs = {login, password};
        Cursor cursor = db.query(TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    //Check if user exists in DB
    public boolean checkUser(String login) {
        String[] columns = {
                COLUMN_UID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_LOGIN + " = ?";

        String[] selectionArgs = {login};
        Cursor cursor = db.query(TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    //Check if email exists in DB
    public boolean checkEmail(String email) {
        String[] columns = {
                COLUMN_UID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_EMAIL + " = ?";

        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public List<String> getUserCredentials(String login) {
        List<String> userDataList = new LinkedList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        String userData = null;

        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE login = ?", new String[]{login}); //+" WHERE login = ?", new String[]{login}
        if (cursor.moveToFirst()) {
            do {
                userData = new String();
                userDataList.add(userData);
            } while (cursor.moveToNext());
        }
        return userDataList;
    }

    public ArrayList<String> returnUserData(String login){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] { COLUMN_LOGIN,
                        COLUMN_EMAIL, COLUMN_PASSWORD}, COLUMN_LOGIN + "=?",
                new String[] { login }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ArrayList<String> userData = new ArrayList<>();

        userData.add(cursor.getString(0));
        userData.add(cursor.getString(1));
        userData.add(cursor.getString(2));
        return userData;
    }

//    public List<String> getUserCredentials(String login) {
//        List<String> userDataList = new LinkedList<String>();
//
//        // 1. build the query
//        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE login = ";
//
//        // 2. get reference to writable DB
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//
//        // 3. go over each row, build book and add it to list
//        String userData = null;
//        if (cursor.moveToFirst()) {
//            do {
//                userData = new String();
//                userDataList.add(userData);
//            } while (cursor.moveToNext());
//        }
//
//        Log.d("UserData:", userDataList.toString());
//
//        // return books
//        return userDataList;
//    }

}
