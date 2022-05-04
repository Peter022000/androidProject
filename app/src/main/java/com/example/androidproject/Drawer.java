package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class Drawer extends AppCompatActivity {
    //Initialize variable
    DrawerLayout drawerLayout;

    private int uid;
    private int userMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_drawer);

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);

        if (firstStart) {
            loadData();
        }


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            uid = extras.getInt("uid");
            userMoney = extras.getInt("userMoney");
        }

        //Assign variable
        drawerLayout = findViewById(R.id.drawer_layout);
    }

    private void loadData() {
        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(Drawer.this);
        myDatabaseHelper.addItem("item1", "d1", 5,3);
        myDatabaseHelper.addItem("item2", "d2", 5,3);
        myDatabaseHelper.addItem("item3", "d3", 5,3);

        myDatabaseHelper.addItemToShop(0,3,6);
        myDatabaseHelper.addItemToShop(1,1,15);
        myDatabaseHelper.addItemToShop(2,2,30);

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
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
        redirectActivity(this, Drawer.class, this.uid, this.userMoney);
        //recreate();
    }

    public void ClickEquipment(View view){
        redirectActivity(this, Equipment.class, this.uid, this.userMoney);
    }

    public void ClickShop(View view){
        redirectActivity(this, Shop.class, this.uid, this.userMoney);
    }

    public void ClickAboutUs(View view){
        redirectActivity(this, AboutUs.class, this.uid, this.userMoney);
    }

    public void ClickLogout(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public static void redirectActivity(Activity activity, Class aClass, int uid, int userMoney) {
        Intent intent = new Intent(activity,aClass);
        intent.putExtra("uid", uid);
        intent.putExtra("userMoney", userMoney);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
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