package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Equipment extends AppCompatActivity {
    DrawerLayout drawerLayout;

    MyDatabaseHelper myDatabaseHelper;

    ArrayList<Item> items;
    EquipmentAdapter equipmentAdapter;
    RecyclerView recyclerView;
    private int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment);

        uid = 1;

        myDatabaseHelper = new MyDatabaseHelper(Equipment.this);
        items = new ArrayList<Item>();

//        myDatabaseHelper.addItem("item1", "d1", 5,3);
//        myDatabaseHelper.addItem("item2", "d2", 5,3);
//        myDatabaseHelper.addItem("item3", "d3", 5,3);

//        myDatabaseHelper.addItemToEquipment(1,1);
//        myDatabaseHelper.addItemToEquipment(1,1);
//        myDatabaseHelper.addItemToEquipment(1,3);

        recyclerView = findViewById(R.id.recyclerViewEquipment);

        equipmentAdapter = new EquipmentAdapter(Equipment.this, items);
        storeItems();
        recyclerView.setAdapter(equipmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Equipment.this));
        equipmentAdapter.setUid(this.uid);
        drawerLayout = findViewById(R.id.drawer_layout);
    }

    public  void ClickMenu(View view) {
        Drawer.openDrawer(drawerLayout);
    }

    public  void ClickLogo(View view){
        Drawer.closeDrawer(drawerLayout);
    }

    public void ClickHome(View view) {
        Drawer.redirectActivity(this, Drawer.class);
    }

    public void ClickEquipment(View view){
        recreate();
    }

    public void ClickShop(View view){
        Drawer.redirectActivity(this, Shop.class);
    }


    public void ClickAboutUs(View view){
        Drawer.redirectActivity(this,AboutUs.class);
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

    void storeItems(){
        Cursor cursor = myDatabaseHelper.readData(uid);

        if(cursor.getCount() == 0)
        {
            Toast.makeText(this, "No items", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext())
            {
                items.add(new Item(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getInt(3), cursor.getInt(4),cursor.getInt(5)));
            }
        }
    }
}