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
}
