package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ResetPassword extends AppCompatActivity {

    String email;
    private EditText newPassword;
    private TextView securityQuestion;
    private EditText securityAnswer;
    DatabaseHelper db = new DatabaseHelper(ResetPassword.this);
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Disables action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_reset_password);

        newPassword = findViewById(R.id.newPassword);
        securityAnswer = findViewById(R.id.securityAnswer);
        securityQuestion = findViewById(R.id.securityQuestion);
        button = findViewById(R.id.button);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            email = extras.getString("Email");
        }

        ArrayList<String> userData = new ArrayList<>();
        userData = db.returnSecurityQuestion(email);
        securityQuestion.setText(userData.get(0));
        String securityQuestionAnswerDB = userData.get(1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword(securityQuestionAnswerDB);
            }
        });
    }

    //Go to MainAcitivity
    public void signIn(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void resetPassword(String securityAnswerDB)
    {
        List<String> fields = new ArrayList<String>();
        fields.add(securityAnswer.getText().toString());
        fields.add(newPassword.getText().toString());

        if(!DataValidator.validateFields(fields))
        {
            Toast.makeText(getApplicationContext(), "Some fields are empty!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(DataValidator.strongPasswordCheck(newPassword.getText().toString(),getApplicationContext()))
            {
                if(securityAnswer.getText().toString().equals(securityAnswerDB)) {
                    if (db.updateData(newPassword.getText().toString(), email)) {
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Failed to change password.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}