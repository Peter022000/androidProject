package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText loginField;
    private EditText passwordField;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Disables action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        loginField = findViewById(R.id.loginField);
        passwordField = findViewById(R.id.passwordField);

        //Load data at first aplication start
        SharedPreferences prefs = getSharedPreferences("prefsfirstStart", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        if (firstStart) {
            loadData();
        }


        preferences = getSharedPreferences("UserCredentials", MODE_PRIVATE);
        editor = preferences.edit();
//        Log.i("Login:", preferences.getString("LOGIN_KEY",""));
//        Log.i("Password:", preferences.getString("PASSWORD_KEY",""));

        if (preferences.getString("KEEP_LOGGED_KEY","false").equals("true")) {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        } else {
        }
    }

    private void loadData() {
        DatabaseHelper DatabaseHelper = new DatabaseHelper(MainActivity.this);

        DatabaseHelper.addType("Armor");
        DatabaseHelper.addType("Weapon");

        DatabaseHelper.addItem("item1", 1, "d1", 2,3);
        DatabaseHelper.addItem("item2", 2,"d2", 5,3);
        DatabaseHelper.addItem("item3", 1,"d3", 3,3);

        DatabaseHelper.addItemToShop(1,3,6);
        DatabaseHelper.addItemToShop(1,1,15);
        DatabaseHelper.addItemToShop(1,2,30);

        SharedPreferences prefs = getSharedPreferences("prefsfirstStart", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }


    //Go to SignUp acitivity
    public void signIn(View view) {
        //List of fields to check
        List<String> credentials = new ArrayList<String>();
        credentials.add(loginField.getText().toString());
        credentials.add(passwordField.getText().toString());

        if (!DataValidator.validateFields(credentials)) {
            Toast.makeText(getApplicationContext(), "Some fields are empty!", Toast.LENGTH_SHORT).show();
        } else {
            DatabaseHelper db = new DatabaseHelper(MainActivity.this);
            if (db.checkUser(loginField.getText().toString(), passwordField.getText().toString())) {
                //Toast.makeText(getApplicationContext(), "Success find user", Toast.LENGTH_SHORT).show();

                ArrayList<Integer> IDAndMoney = db.returnIDAndMoney(loginField.getText().toString());

                Intent intent = new Intent(this, Drawer.class);
                intent.putExtra("uid",IDAndMoney.get(0));
                intent.putExtra("userMoney",IDAndMoney.get(1));
                startActivity(intent);

                editor.putString("LOGIN_KEY", loginField.getText().toString());
                editor.putString("PASSWORD_KEY", passwordField.getText().toString());
                editor.putInt("UID_KEY", IDAndMoney.get(0));
                editor.putInt("MONEY_KEY", IDAndMoney.get(1));
                editor.commit();
//                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
//                startActivity(intent);
            } else {
                    Toast.makeText(getApplicationContext(), "Credentials are wrong.", Toast.LENGTH_SHORT).show();
            }
            //Here will be goto Drawer activity after successfully validation
                //Intent intent = new Intent(this, Drawer.class);
                //startActivity(intent);
        }
    }

    //Go to SignUp acitivity
    public void signUp(View view)
    {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    //Go to forgotPasswordAcitivity
    public void forgotPassword(View view)
    {
        Intent intent = new Intent(this, ForgotPassword.class);
        startActivity(intent);
    }

    //Wczytanie danych loginu z sharedpreferences
    private String loadLogin(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String login = sharedPreferences.getString("LOGIN_KEY", "");
        loginField.setText(login);
        return login;
    }

    //Wczytanie danych loginu z sharedpreferences
    private String loadPassword(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String password = sharedPreferences.getString("PASSWORD_KEY", "");
        loginField.setText(password);
        return password;
    }

    //Zapisanie danych loginu do sharedpreferences
    private void saveLogin(String login, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("LOGIN_KEY", login);
        editor.putString("PASSWORD_KEY", password);
        editor.apply();
    }

    //Przejscie do kolejnego activity, jezeli login zostal podany
    public void setLogin(View view){
        String login = loginField.getText().toString();
        String password = passwordField.getText().toString();
        //Intent intent = new Intent(this, DrawerActivity.class); //Open new activity
        //intent.putExtra("ACTIVITY", "MainActivity");
        //intent.putExtra("KEY_YOUR_LOGIN", login); //Pass variable login ro another activity
        saveLogin(login, password);
        //startActivity(intent);
    }
}