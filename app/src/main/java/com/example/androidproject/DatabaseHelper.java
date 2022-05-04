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
    public static final int DATABASE_VERSION = 2; //Do sprawdzenia, wcześniej 1
    public static final String TABLE_NAME = "users";

    public static final String COLUMN_UID = "uid";
    public static final String COLUMN_LOGIN = "login";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_SECURITY_QUESTION = "security_question";
    public static final String COLUMN_SECURITY_ANSWER = "security_answer";
    public static final String COLUMN_PROFILE_IMAGE = "profile_image_url";
    public static final String COLUMN_MONEY = "money";

    //----------------------------------------------------------------------------------------------

    private static final String TABLE_NAME1 = "items";
    private static final String COLUMN_IID = "iid";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_VALUE = "value";
    private static final String COLUMN_WEIGHT = "weight";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_AMOUNT = "amount";

    private static final String TABLE_NAME2 = "equipment";

    private static final String TABLE_NAME3 = "shops";
    private static final String COLUMN_SID = "sid";

    //----------------------------------------------------------------------------------------------


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
                "money INTEGER DEFAULT 100,\n" + // zmiana numeric na integer
                "CONSTRAINT users_email_key UNIQUE (email),\n" +
                "CONSTRAINT users_login_key UNIQUE (login)\n" +
                ")";
        db.execSQL(query);

        //------------------------------------------------------------------------------------------

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

        //----------------------------------------------------------------------------------------------
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        //------------------------------------------------------------------------------------------

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);

        //------------------------------------------------------------------------------------------
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

//    public List<String> getUserCredentials(String login) {
//        List<String> userDataList = new LinkedList<String>();
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        String userData = null;
//
//        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE login = ?", new String[]{login}); //+" WHERE login = ?", new String[]{login}
//        if (cursor.moveToFirst()) {
//            do {
//                userData = new String();
//                userDataList.add(userData);
//            } while (cursor.moveToNext());
//        }
//        return userDataList;
//    }

    //Returns users login, email and password
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

    //Returns users security question and it's answer
    public ArrayList<String> returnSecurityQuestion(String email){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] { COLUMN_SECURITY_QUESTION,
                        COLUMN_SECURITY_ANSWER}, COLUMN_EMAIL + "=?",
                new String[] { email }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ArrayList<String> userData = new ArrayList<>();

        userData.add(cursor.getString(0));
        userData.add(cursor.getString(1));
        return userData;
    }

    //Updates users password
    boolean updateData(String newPassword, String email)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PASSWORD, newPassword);
        long result = db.update(TABLE_NAME, cv, "email=?", new String[]{email});
        
        if(result == -1)
        {
            Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            Toast.makeText(context, "Password changed successfully", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    //Updates users password
    boolean updateDataByLogin(String newPassword, String login)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PASSWORD, newPassword);
        long result = db.update(TABLE_NAME, cv, "login=?", new String[]{login});

        if(result == -1)
        {
            Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            Toast.makeText(context, "Password changed successfully", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    //Updates users email
    boolean updateEmail(String newEmail, String login)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_EMAIL, newEmail);
        long result = db.update(TABLE_NAME, cv, "login=?", new String[]{login});

        if(result == -1)
        {
            Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            Toast.makeText(context, "Email changed successfully", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    //Updates users password
    boolean updateLogin(String newLogin, String login)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_LOGIN, newLogin);
        long result = db.update(TABLE_NAME, cv, "login=?", new String[]{login});

        if(result == -1)
        {
            Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            Toast.makeText(context, "Login changed successfully", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    //Returns users avatarNumber
    public String returnAvatarNumber(String email){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] { COLUMN_PROFILE_IMAGE }, COLUMN_EMAIL + "=?",
                new String[] { email }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        String avatarNumber;

        avatarNumber = cursor.getString(0);
        return avatarNumber;
    }

    //Updates users avatar
    boolean updateAvatar(String newAvatarNumber, String login)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PROFILE_IMAGE, newAvatarNumber);
        long result = db.update(TABLE_NAME, cv, "login=?", new String[]{login});

        if(result == -1)
        {
            Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            Toast.makeText(context, "Avatar changed successfully", Toast.LENGTH_SHORT).show();
            return true;
        }
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

    //----------------------------------------------------------------------------------------------

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

    void addItemToShop(int sid, int iid, int amount){

        String query = "SELECT * FROM " + TABLE_NAME3 + " WHERE " + COLUMN_IID + " = " + iid + " AND " + COLUMN_SID + " = " + sid + " ;";
        SQLiteDatabase rdb = this.getReadableDatabase();
        SQLiteDatabase wdb = this.getWritableDatabase();

        Cursor cursor = null;

        if(rdb != null){
            cursor = rdb.rawQuery(query,null);
            if(cursor.getCount() == 0){
                ContentValues cv = new ContentValues();

                cv.put(COLUMN_SID, sid);
                cv.put(COLUMN_IID, iid);
                cv.put(COLUMN_AMOUNT, amount);

                wdb.insert(TABLE_NAME3, null, cv);
            }
            else
            {
                ContentValues cv = new ContentValues();
                cursor.moveToNext();
                int old = cursor.getInt(2);
                cv.put(COLUMN_AMOUNT, old + amount);

                wdb.update(TABLE_NAME3, cv,COLUMN_SID + " = ? AND " + COLUMN_IID + " = ?" , new String[]{String.valueOf(sid), String.valueOf(iid)});
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

    void deleteItemFromShop(int sid, int iid, int amount){

        SQLiteDatabase wdb = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_SID, sid);
        cv.put(COLUMN_IID, iid);
        cv.put(COLUMN_AMOUNT, amount);

        wdb.update(TABLE_NAME3, cv,COLUMN_SID + " = ? AND " + COLUMN_IID + " = ?" , new String[]{String.valueOf(sid), String.valueOf(iid)});
    }

    Cursor readDataEquipment(int uid){
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

    Cursor readDataShop(int sid){
        String query = "SELECT it.*, sh.amount FROM " + TABLE_NAME3 + " AS sh " +
                " JOIN " + TABLE_NAME1+ " AS it ON sh.iid = it.iid "+
                " WHERE sh.sid = " + sid + " AND sh.amount > 0 ";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query,null);
        }

        return cursor;
    }

    public ArrayList<Integer> returnIDAndMoney(String login){
        String query = "SELECT u.uid, u.money FROM " + TABLE_NAME + " AS u " +
                " WHERE u.login = " + '"' + login+ '"';

        SQLiteDatabase rdb = this.getReadableDatabase();

        Cursor cursor = null;

        if(rdb != null){
            cursor = rdb.rawQuery(query,null);
        }

        ArrayList<Integer> userData = new ArrayList<>();

        cursor.moveToNext();
        userData.add(cursor.getInt(0));
        userData.add(cursor.getInt(1));
        return userData;
    }

    void updateMoney(int uid, int money){

        SQLiteDatabase wdb = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_MONEY, money);

        wdb.update(TABLE_NAME, cv,COLUMN_UID + " = ?", new String[]{String.valueOf(uid)});
    }


}
