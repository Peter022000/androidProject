package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class AvatarChooser extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Button setAvatarButton;
    ImageView userAvatar;
    ImageView avatar1;
    ImageView avatar2;
    ImageView avatar3;
    ImageView avatar4;
    ImageView avatar5;
    ImageView avatar6;
    ImageView avatar7;
    ImageView avatar8;
    ImageView avatar9;
    String newAvatarNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Disables action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_avatar_chooser);

        setAvatarButton = findViewById(R.id.setAvatarButton);
        userAvatar = findViewById(R.id.userAvatar);
        avatar1 = findViewById(R.id.avatar1);
        avatar2 = findViewById(R.id.avatar2);
        avatar3 = findViewById(R.id.avatar3);
        avatar4 = findViewById(R.id.avatar4);
        avatar5 = findViewById(R.id.avatar5);
        avatar6 = findViewById(R.id.avatar6);
        avatar7 = findViewById(R.id.avatar7);
        avatar8 = findViewById(R.id.avatar8);
        avatar9 = findViewById(R.id.avatar9);

        preferences = getSharedPreferences("UserCredentials", MODE_PRIVATE);
        editor = preferences.edit();

        showAvatar(preferences.getString("USER_AVATAR_KEY","default"));

        avatar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newAvatarNumber = "1";
                showAvatar(newAvatarNumber);
            }
        });

        avatar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newAvatarNumber = "2";
                showAvatar(newAvatarNumber);
            }
        });

        avatar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newAvatarNumber = "3";
                showAvatar(newAvatarNumber);
            }
        });

        avatar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newAvatarNumber = "4";
                showAvatar(newAvatarNumber);
            }
        });

        avatar5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newAvatarNumber = "5";
                showAvatar(newAvatarNumber);
            }
        });

        avatar6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newAvatarNumber = "6";
                showAvatar(newAvatarNumber);
            }
        });

        avatar7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newAvatarNumber = "7";
                showAvatar(newAvatarNumber);
            }
        });

        avatar8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newAvatarNumber = "8";
                showAvatar(newAvatarNumber);
            }
        });

        avatar9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newAvatarNumber = "default";
                showAvatar(newAvatarNumber);
            }
        });

        setAvatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAvatar(newAvatarNumber);
            }
        });

    }

    //Sets user avatar
    public void showAvatar(String avatarNumber)
    {
        switch (avatarNumber)
        {
            case "1":
                userAvatar.setImageDrawable(getDrawable(R.drawable.av1));
                break;
            case "2":
                userAvatar.setImageDrawable(getDrawable(R.drawable.av2));
                break;
            case "3":
                userAvatar.setImageDrawable(getDrawable(R.drawable.av3));
                break;
            case "4":
                userAvatar.setImageDrawable(getDrawable(R.drawable.av4));
                break;
            case "5":
                userAvatar.setImageDrawable(getDrawable(R.drawable.av5));
                break;
            case "6":
                userAvatar.setImageDrawable(getDrawable(R.drawable.av6));
                break;
            case "7":
                userAvatar.setImageDrawable(getDrawable(R.drawable.av7));
                break;
            case "8":
                userAvatar.setImageDrawable(getDrawable(R.drawable.av8));
                break;
            default:
                userAvatar.setImageDrawable(getDrawable(R.drawable.avatar));
                break;
        }
    }

    //Sets user avatar
    public void setAvatar(String newAvatarNumber)
    {
        switch (newAvatarNumber)
        {
            case "1":
                editor.putString("USER_AVATAR_KEY", newAvatarNumber);
                editor.commit();
                gotoProfile();
                break;
            case "2":
                editor.putString("USER_AVATAR_KEY", newAvatarNumber);
                editor.commit();
                gotoProfile();
                break;
            case "3":
                editor.putString("USER_AVATAR_KEY", newAvatarNumber);
                editor.commit();
                gotoProfile();
                break;
            case "4":
                editor.putString("USER_AVATAR_KEY", newAvatarNumber);
                editor.commit();
                gotoProfile();
                break;
            case "5":
                editor.putString("USER_AVATAR_KEY", newAvatarNumber);
                editor.commit();
                gotoProfile();
                break;
            case "6":
                editor.putString("USER_AVATAR_KEY", newAvatarNumber);
                editor.commit();
                gotoProfile();
                break;
            case "7":
                editor.putString("USER_AVATAR_KEY", newAvatarNumber);
                editor.commit();
                gotoProfile();
                break;
            case "8":
                editor.putString("USER_AVATAR_KEY", newAvatarNumber);
                editor.commit();
                gotoProfile();
                break;
            default:
                editor.putString("USER_AVATAR_KEY", newAvatarNumber);
                editor.commit();
                gotoProfile();
                break;
        }
    }

    //Go to profile
    public void gotoProfile()
    {
        Toast.makeText(getApplicationContext(), "Avatar changed", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
}