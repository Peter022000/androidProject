package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText loginField;
    private EditText passwordField;

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
    }

    //Go to SignUp acitivity
    public void signIn(View view)
    {
        //List of fields to check
        List<String> credentials = new ArrayList<String>();
        credentials.add(loginField.getText().toString());
        credentials.add(passwordField.getText().toString());

        if(!DataValidator.validateFields(credentials))
        {
            Toast.makeText(getApplicationContext(), "Some fields are empty!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            DatabaseHelper db = new DatabaseHelper(MainActivity.this);
            if(db.checkUser(loginField.getText().toString(), passwordField.getText().toString()))
            {
                Toast.makeText(getApplicationContext(), "Success find user", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Failed, no such user", Toast.LENGTH_SHORT).show();
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
}