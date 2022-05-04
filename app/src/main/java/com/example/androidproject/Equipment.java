package com.example.androidproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Equipment extends AppCompatActivity {
    DrawerLayout drawerLayout;

    DatabaseHelper DatabaseHelper;

    ArrayList<Item> items;
    EquipmentAdapter equipmentAdapter;
    RecyclerView recyclerView;
    private int uid;
    private int userMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_equipment);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            uid = extras.getInt("uid");
            userMoney = extras.getInt("userMoney");
        }

        DatabaseHelper = new DatabaseHelper(Equipment.this);
        items = new ArrayList<Item>();

//        myDatabaseHelper.addItem("item1", "d1", 5,3);
//        myDatabaseHelper.addItem("item2", "d2", 5,3);
//        myDatabaseHelper.addItem("item3", "d3", 5,3);

//        myDatabaseHelper.addItemToShop(0,3,6);

//        myDatabaseHelper.addItemToEquipment(1,1);
//        myDatabaseHelper.addItemToEquipment(1,1);
//        myDatabaseHelper.addItemToEquipment(1,1);
//        myDatabaseHelper.addItemToEquipment(1,1);
//        myDatabaseHelper.addItemToEquipment(1,1);
//        myDatabaseHelper.addItemToEquipment(1,1);
//        myDatabaseHelper.addItemToEquipment(1,1);
//        myDatabaseHelper.addItemToEquipment(1,3);
//        myDatabaseHelper.addItemToEquipment(1,3);
//        myDatabaseHelper.addItemToEquipment(1,3);

        recyclerView = findViewById(R.id.recyclerViewEquipment);

        storeItems();
        equipmentAdapter = new EquipmentAdapter(Equipment.this, items);
        recyclerView.setAdapter(equipmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Equipment.this));
        equipmentAdapter.setUid(this.uid);
        equipmentAdapter.setUserMoney(this.userMoney);
        drawerLayout = findViewById(R.id.drawer_layout);
    }

    public void ClickMenu(View view) {
        Drawer.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view) {
        Drawer.closeDrawer(drawerLayout);
    }

    public void ClickHome(View view) {
        Drawer.redirectActivity(this, Drawer.class, this.uid, this.userMoney);
    }

    public void ClickEquipment(View view) {
        Drawer.redirectActivity(this, Equipment.class, this.uid, this.userMoney);
    }

    public void ClickUserProfile(View view){
        Drawer.redirectActivity(this, ProfileActivity.class, this.uid, this.userMoney);
    }

    public void ClickShop(View view) {
        Drawer.redirectActivity(this, Shop.class, this.uid, this.userMoney);
    }


    public void ClickAboutUs(View view) {
        Drawer.redirectActivity(this, AboutUs.class, this.uid, this.userMoney);
    }

    public void ClickLogout(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Drawer.closeDrawer(drawerLayout);
    }

    void storeItems() {
        Cursor cursor = DatabaseHelper.readDataEquipment(uid);

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No items", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                items.add(new Item(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getInt(3), cursor.getInt(4), cursor.getInt(5)));
            }
        }
    }

    @Override
    public void onBackPressed() {
    }
}