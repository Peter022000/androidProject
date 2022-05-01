package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ForgotPassword extends AppCompatActivity {

    private EditText emailField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Disables action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_forgot_password);
        emailField = findViewById(R.id.emailField);
    }

    public void gotoReset(View view)
    {
        List<String> fields = new ArrayList<String>();
        fields.add(emailField.getText().toString());
        if(!DataValidator.validateFields(fields))
        {
            Toast.makeText(getApplicationContext(), "Empty field!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(DataValidator.validateEmail(emailField.getText().toString(), getApplicationContext()))
            {
                DatabaseHelper db = new DatabaseHelper(ForgotPassword.this);
                if(db.checkEmail(emailField.getText().toString()))
                {
                    resetPassword(emailField.getText().toString());
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Email not found.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //Go to MainAcitivity
    public void signIn(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //Go to resetPassword
    public void resetPassword(String emailField)
    {
        Intent email = new Intent(this, ResetPassword.class);
        email.putExtra("Email",emailField);
        startActivity(email);
    }
}