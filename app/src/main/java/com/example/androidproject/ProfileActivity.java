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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
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
    CheckBox keepMeLogged;
    ImageView avatarImage;
    Button changeAvatarButton;
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
        loginField = findViewById(R.id.loginField);
        emailField = findViewById(R.id.emailField);
        avatarImage = findViewById(R.id.avatarImage);
        changeAvatarButton = findViewById(R.id.changeAvatarButton);

        //Sets login field
        loginField.setText(preferences.getString("LOGIN_KEY", ""));

        db = new DatabaseHelper(ProfileActivity.this);

        //Returns and set user data
        ArrayList<String> userData = new ArrayList<>();
        userData = db.returnUserData(loginField.getText().toString());

        //Sets user email
        emailField.setText(userData.get(1));

        CheckBox keepMeLogged = ( CheckBox ) findViewById( R.id.keepMeLogged );

        if(preferences.getString("KEEP_LOGGED_KEY","false").equals("true"))
        {
            keepMeLogged.setChecked(true);
        }

        keepMeLogged.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    editor.putString("KEEP_LOGGED_KEY", "true");
                    editor.commit();
                    Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    editor.clear();
                    editor.putString("KEEP_LOGGED_KEY", "false");
                    editor.commit();
                    Toast.makeText(getApplicationContext(), "Saved 2!", Toast.LENGTH_SHORT).show();
                }

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

        String avatarNumber = preferences.getString("USER_AVATAR_KEY", "default");

        setAvatar(avatarNumber);

        changeAvatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAvatarChooser(avatarNumber);
            }
        });
    }

    //Sets user avatar
    public void setAvatar(String avatarNumber)
    {
        switch (avatarNumber)
        {
            case "1":
                avatarImage.setImageDrawable(getDrawable(R.drawable.av1));
                break;
            case "2":
                avatarImage.setImageDrawable(getDrawable(R.drawable.av2));
                break;
            case "3":
                avatarImage.setImageDrawable(getDrawable(R.drawable.av3));
                break;
            case "4":
                avatarImage.setImageDrawable(getDrawable(R.drawable.av4));
                break;
            case "5":
                avatarImage.setImageDrawable(getDrawable(R.drawable.av5));
                break;
            case "6":
                avatarImage.setImageDrawable(getDrawable(R.drawable.av6));
                break;
            case "7":
                avatarImage.setImageDrawable(getDrawable(R.drawable.av7));
                break;
            case "8":
                avatarImage.setImageDrawable(getDrawable(R.drawable.av8));
                break;
            default:
                avatarImage.setImageDrawable(getDrawable(R.drawable.avatar));
                break;
        }
    }

    //Go to avatarChooser
    public void gotoAvatarChooser(String avatarNumber)
    {
        editor.putString("USER_AVATAR_KEY", avatarNumber);
        editor.commit();
        Intent intent = new Intent(this, AvatarChooser.class);
        startActivity(intent);
    }


}