package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ProfileSecurity extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Button button;
    TextView titleView;

    EditText newDataField;
    TextView newData;

    EditText confirmNewDataField;
    TextView confirmNewData;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Disables action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile_security);

        button = findViewById(R.id.button);
        titleView = findViewById(R.id.titleView);
        newDataField = findViewById(R.id.newDataField);
        newData = findViewById(R.id.newData);
        confirmNewDataField = findViewById(R.id.confirmNewDataField);
        confirmNewData = findViewById(R.id.confirmNewData);

        preferences = getSharedPreferences("UserCredentials", MODE_PRIVATE);
        editor = preferences.edit();

        db = new DatabaseHelper(ProfileSecurity.this);

        if(preferences.getString("CHANGE_KEY","password").equals("password"))
        {
            //password change method
        }
        else if(preferences.getString("CHANGE_KEY","password").equals("email"))
        {
            titleView.setText("Reset email");
            newData.setText("New email");
            confirmNewData.setText("Confirm email");
            newDataField.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        else if(preferences.getString("CHANGE_KEY","password").equals("login"))
        {
            titleView.setText("Reset login");
            newData.setText("New login");
            confirmNewData.setText("Confirm login");
            newDataField.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        else
        {
            //error
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> fields = new ArrayList<String>();
                fields.add(newDataField.getText().toString());
                fields.add(confirmNewDataField.getText().toString());
                if(preferences.getString("CHANGE_KEY","password").equals("password"))
                {
                    if(!DataValidator.validateFields(fields))
                    {
                        Toast.makeText(getApplicationContext(), "Some fields are empty!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (DataValidator.comparePasswords(newDataField.getText().toString(), confirmNewDataField.getText().toString())) {
                            if (DataValidator.strongPasswordCheck(newDataField.getText().toString(), getApplicationContext())) {
                                if (db.updateDataByLogin(newDataField.getText().toString(), preferences.getString("LOGIN_KEY", "default"))) {
                                    Intent i = new Intent(ProfileSecurity.this, MainActivity.class);
                                    startActivity(i);
                                }
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Fields must match each other.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else if(preferences.getString("CHANGE_KEY","password").equals("email"))
                {
                    if(!DataValidator.validateFields(fields))
                    {
                        Toast.makeText(getApplicationContext(), "Some fields are empty!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if(DataValidator.validateEmail(newDataField.getText().toString(), getApplicationContext()))
                        {
                            if (DataValidator.comparePasswords(newDataField.getText().toString(), confirmNewDataField.getText().toString())) {
                                if(!db.checkEmail(newDataField.getText().toString())) {
                                    if (db.updateEmail(newDataField.getText().toString(), preferences.getString("LOGIN_KEY", "default"))) {
                                        Intent i = new Intent(ProfileSecurity.this, MainActivity.class);
                                        startActivity(i);
                                    }
                                }
                                else
                                {
                                    //Email not found
                                    Toast.makeText(getApplicationContext(), "Email wrong.", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Fields must match each other.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
                else if(preferences.getString("CHANGE_KEY","password").equals("login"))
                {
                    if(!DataValidator.validateFields(fields))
                    {
                        Toast.makeText(getApplicationContext(), "Some fields are empty!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if (DataValidator.comparePasswords(newDataField.getText().toString(), confirmNewDataField.getText().toString())) {
                            if(!db.checkUser(newDataField.getText().toString())) {
                                if (db.updateLogin(newDataField.getText().toString(), preferences.getString("LOGIN_KEY", "default"))) {
                                    editor.putString("LOGIN_KEY", newDataField.getText().toString());
                                    editor.commit();
                                    Intent i = new Intent(ProfileSecurity.this, MainActivity.class);
                                    startActivity(i);
                                }
                            }
                            else
                            {
                                //Login not found
                                Toast.makeText(getApplicationContext(), "Login wrong.", Toast.LENGTH_SHORT).show();
                            }
                         }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Fields must match each other.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}