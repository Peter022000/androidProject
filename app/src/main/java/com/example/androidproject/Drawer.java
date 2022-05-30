package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.Context;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Drawer extends AppCompatActivity {
    //Initialize variable
    DrawerLayout drawerLayout;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Timer t = new Timer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_drawer);

        preferences = getSharedPreferences("UserCredentials", MODE_PRIVATE);
        editor = preferences.edit();

        //Assign variable
        drawerLayout = findViewById(R.id.drawer_layout);
    }

    public void ClickMenu(View view){
        //Open drawer
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        //Open drawer Layout
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view){
        //Close drawer
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        //Close drawer Layout
        //Check condition
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            //When drawer is open
            //Close drawer
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome(View view){
        //redirectActivity(this, Drawer.class);
        recreate();
    }

    public void ClickEquipment(View view){
        redirectActivity(this, Equipment.class);
    }

    public void ClickShop(View view){
        redirectActivity(this, Shop.class);
    }

    public void ClickAboutUs(View view){
        redirectActivity(this, AboutUs.class);
    }

    public void ClickUserProfile(View view){
        redirectActivity(this, ProfileActivity.class);
    }


    public void ClickLogout(View view){
        editor.clear();
        editor.commit();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity,aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    public void ClickDice(View view){
        Timer t = new Timer();
        Random liczba = new Random();

        t.scheduleAtFixedRate(
                new TimerTask() {
                    int rollTime = liczba.nextInt(7)+4;
                    public void run()
                    {
                        if (rollTime > 0) {
                            rollingFunction();
                            rollTime--;
                        } else {
                            t.cancel();
                        }
                    }
                },
                0,      // run first occurrence immediatetly
                200);

    }

    public void rollingFunction(){
        Random liczba = new Random();
        ImageView d_roll_img= (ImageView) findViewById(R.id.imageViewRoll);

        int los = liczba.nextInt(20);

        if (los == 0) {
            d_roll_img.setImageResource(R.drawable.d1_roll);
        }else if (los == 1) {
            d_roll_img.setImageResource(R.drawable.d2_roll);
        }else if (los == 2) {
            d_roll_img.setImageResource(R.drawable.d3_roll);
        }else if (los == 3) {
            d_roll_img.setImageResource(R.drawable.d4_roll);
        }else if (los == 4) {
            d_roll_img.setImageResource(R.drawable.d5_roll);
        }else if (los == 5) {
            d_roll_img.setImageResource(R.drawable.d6_roll);
        }else if (los == 6){
               d_roll_img.setImageResource(R.drawable.d7_roll);
        }else if (los == 7) {
            d_roll_img.setImageResource(R.drawable.d8_roll);
        }else if (los == 8) {
            d_roll_img.setImageResource(R.drawable.d9_roll);
        }else if (los == 9) {
            d_roll_img.setImageResource(R.drawable.d10_roll);
        }else if (los == 10) {
            d_roll_img.setImageResource(R.drawable.d11_roll);
        }else if (los == 11) {
            d_roll_img.setImageResource(R.drawable.d12_roll);
        }else if (los == 12) {
            d_roll_img.setImageResource(R.drawable.d13_roll);
        }else if (los == 13) {
            d_roll_img.setImageResource(R.drawable.d14_roll);
        }else if (los == 14) {
            d_roll_img.setImageResource(R.drawable.d15_roll);
        }else if (los == 15) {
            d_roll_img.setImageResource(R.drawable.d16_roll);
        }else if (los == 16) {
            d_roll_img.setImageResource(R.drawable.d17_roll);
        }else if (los == 17) {
            d_roll_img.setImageResource(R.drawable.d18_roll);
        }else if (los == 18) {
            d_roll_img.setImageResource(R.drawable.d19_roll);
        }else if (los == 19) {
            d_roll_img.setImageResource(R.drawable.d20_roll);
        }else {
        }

    }



    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }

    @Override
    public void onBackPressed() {
    }
}