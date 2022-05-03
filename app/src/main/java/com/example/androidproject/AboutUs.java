package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class AboutUs extends AppCompatActivity {
    DrawerLayout drawerLayout;

    private int uid;
    private int userMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_about_us);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            uid = extras.getInt("uid");
            userMoney = extras.getInt("userMoney");
        }

        drawerLayout = findViewById(R.id.drawer_layout);
    }

    public  void ClickMenu(View view) {
        Drawer.openDrawer(drawerLayout);
    }

    public  void ClickLogo(View view){
        Drawer.closeDrawer(drawerLayout);
    }

    public void ClickHome(View view) {
        Drawer.redirectActivity(this, Drawer.class, this.uid, this.userMoney);
    }

    public void ClickEquipment(View view){
        Drawer.redirectActivity(this,Equipment.class, this.uid, this.userMoney);
    }

    public void ClickShop(View view){
        Drawer.redirectActivity(this, Shop.class, this.uid, this.userMoney);
    }


    public void ClickAboutUs(View view){
        Drawer.redirectActivity(this, AboutUs.class, this.uid, this.userMoney);
    }

    public void ClickLogout(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Drawer.closeDrawer(drawerLayout);
    }

    @Override
    public void onBackPressed() {
    }
}