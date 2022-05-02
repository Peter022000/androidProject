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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SignUp extends AppCompatActivity {
    EditText loginField;
    EditText emailField;
    EditText securityQuestionAnswerField;
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

        loginField = findViewById(R.id.loginField);
        emailField = findViewById(R.id.emailField);
        securityQuestionAnswerField = findViewById(R.id.securityQuestionAnswerField);
        passwordConfirmField = findViewById(R.id.passwordConfirmField);
        passwordField = findViewById(R.id.passwordField);
        button = findViewById(R.id.button);

        //Drop-down list
        Spinner dropdown = findViewById(R.id.questionList);
        String[] items = new String[]{
                "What is the name of your favorite pet?",
                "What primary school did you attend?",
                "In what town or city was your first full time job?",
                "What is your partner's mother's maiden name?",
                "What is your partner's mother's maiden name?",
                "What is the middle name of your oldest child?",
                "What is the middle name of your oldest child?",
                "What is your favourite movie?",
                "What is your memorable date?",
                "What are the last three digits of your number?",
                "What is the name of your favorite pet?"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        String securityQuestion = dropdown.getSelectedItem().toString();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> credentials = new ArrayList<String>();
                credentials.add(loginField.getText().toString());
                credentials.add(emailField.getText().toString());
                credentials.add(securityQuestion);
                credentials.add(securityQuestionAnswerField.getText().toString());
                credentials.add(passwordField.getText().toString());
                credentials.add(passwordConfirmField.getText().toString());


                if(!DataValidator.validateFields(credentials))
                {
                    Toast.makeText(getApplicationContext(), "Some fields are empty!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(DataValidator.comparePasswords(credentials.get(4), credentials.get(5)))
                    {
                        if(DataValidator.strongPasswordCheck(credentials.get(4), getApplicationContext()))
                        {
                            if (DataValidator.validateEmail(credentials.get(1), getApplicationContext()))
                            {
                                DatabaseHelper db = new DatabaseHelper(SignUp.this);
                                if(!db.checkUser(credentials.get(0)))
                                {
                                    if(!db.checkEmail(credentials.get(1)))
                                    {
                                        db.createAccount(loginField.getText().toString(), emailField.getText().toString(), passwordField.getText().toString(), securityQuestion, securityQuestionAnswerField.getText().toString(), "avatar", 1000);
                                        Intent i = new Intent(SignUp.this, MainActivity.class);
                                        startActivity(i);
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(), "Email is already taken!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), "Login is already taken!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Passwords didn't match each other.", Toast.LENGTH_SHORT).show();
                    }
                }
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