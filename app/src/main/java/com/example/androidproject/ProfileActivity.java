package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Button button;
    Button loggedIn;
    Button loggedOut;
    TextView loginField;
    TextView emailField;
    DatabaseHelper db;
//    ArrayList<String> login, email, password, security_question, security_answer, profile_image_url, money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Disables action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile);
        preferences = getSharedPreferences("UserCredentials", MODE_PRIVATE);
        editor = preferences.edit();
        button = findViewById(R.id.logoutButton);
        loggedOut = findViewById(R.id.loggedOut);
        loggedIn = findViewById(R.id.loggedIn);
        loginField = findViewById(R.id.loginField);
        emailField = findViewById(R.id.emailField);

        //Sets login field
        loginField.setText(preferences.getString("LOGIN_KEY", ""));

        db = new DatabaseHelper(ProfileActivity.this);

//        login = new ArrayList<>();
//        email = new ArrayList<>();
//        password = new ArrayList<>();
//        security_question = new ArrayList<>();
//        security_answer = new ArrayList<>();
//        profile_image_url = new ArrayList<>();
//        money = new ArrayList<>();

        //Returns and set user data
        ArrayList<String> userData = new ArrayList<>();
        userData = db.returnUserData(loginField.getText().toString());

        emailField.setText(userData.get(1));


        //Sets system to keep user logged
        loggedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("KEEP_LOGGED_KEY", "true");
                editor.commit();
                Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_SHORT).show();
            }
        });

        //Clears all preferences
        loggedOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.putString("KEEP_LOGGED_KEY", "false");
                editor.commit();
                Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_SHORT).show();
            }
        });


        //Logout method
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.commit();
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

//    void setUserData()
//    {
//        Cursor cursor = db.findRow(loginField.getText().toString());
//        if(cursor.getCount() == 0)
//        {
//            Toast.makeText(getApplicationContext(), "No data.", Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//            while (cursor.moveToNext())
//            {
//                login.add(cursor.getString(0));
//                email.add(cursor.getString(1));
//                password.add(cursor.getString(2));
//                security_question.add(cursor.getString(3));
//                security_answer.add(cursor.getString(4));
//                profile_image_url.add(cursor.getString(5));
//                money.add(cursor.getString(6));
//            }
//        }
//    }
}