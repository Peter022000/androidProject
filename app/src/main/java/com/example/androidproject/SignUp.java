package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SignUp extends AppCompatActivity {
    EditText login;
    EditText email;
    EditText securityQuestionAnswer;
    EditText passwordConfirmField;
    EditText passwordField;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Disables action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_sign_up);

        login = findViewById(R.id.login);
        email = findViewById(R.id.email);
        securityQuestionAnswer = findViewById(R.id.securityQuestionAnswer);
        passwordConfirmField = findViewById(R.id.passwordConfirmField);
        passwordField = findViewById(R.id.passwordField);
        button = findViewById(R.id.button);

        //Drop-down list
        Spinner dropdown = findViewById(R.id.questionList);
        String[] items = new String[]{"Pytanie 1", "Pytanie 2", "Pytanie 3"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        String securityQuestion = dropdown.getSelectedItem().toString();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(SignUp.this);
                db.createAccount(login.getText().toString(), email.getText().toString(), passwordField.getText().toString(), securityQuestion, securityQuestionAnswer.getText().toString(), "1", 1000);
            }
        });

    }

    //Go to SignUp acitivity
    public void signIn(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}