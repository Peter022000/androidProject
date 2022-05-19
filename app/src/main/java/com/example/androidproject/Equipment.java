package com.example.androidproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
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

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private ImageView sortOrderClick;
    private int selectedSortType;
    private String selectedSortOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_equipment);

        preferences = getSharedPreferences("UserCredentials", MODE_PRIVATE);
        editor = preferences.edit();

        sortOrderClick = findViewById(R.id.sortOrderIcon);
        selectedSortType = 0;
        selectedSortOrder = "name";
        sortOrderClick.setBackgroundResource(R.drawable.icon_arrow_up);


        uid = preferences.getInt("UID_KEY",-1);
        userMoney = preferences.getInt("MONEY_KEY",-1);

        DatabaseHelper = new DatabaseHelper(Equipment.this);
        items = new ArrayList<Item>();

        recyclerView = findViewById(R.id.recyclerViewEquipment);

        setRecyclerView();

        drawerLayout = findViewById(R.id.drawer_layout);
    }

    public void ClickMenu(View view) {
        Drawer.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view) {
        Drawer.closeDrawer(drawerLayout);
    }

    public void ClickHome(View view) {
        Drawer.redirectActivity(this, Drawer.class);
    }

    public void ClickEquipment(View view) {
        //Drawer.redirectActivity(this, Equipment.class);
        recreate();
    }

    public void ClickUserProfile(View view){
        Drawer.redirectActivity(this, ProfileActivity.class);
    }

    public void ClickShop(View view) {
        Drawer.redirectActivity(this, Shop.class);
    }


    public void ClickAboutUs(View view) {
        Drawer.redirectActivity(this, AboutUs.class);
    }

    public void ClickLogout(View view) {
        editor.clear();
        editor.commit();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Drawer.closeDrawer(drawerLayout);
    }

    void storeItems(String orderBy, int sortType) {
        Cursor cursor = DatabaseHelper.readDataEquipment(uid, orderBy, sortType);

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No items", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                items.add(new Item(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getInt(3), cursor.getInt(4), cursor.getInt(5),
                        cursor.getString(6),cursor.getInt(7)));
            }
        }
    }

    private void setRecyclerView()
    {
        this.items.clear();
        storeItems(selectedSortOrder, selectedSortType);
        equipmentAdapter = new EquipmentAdapter(Equipment.this, items);
        recyclerView.setAdapter(equipmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Equipment.this));
        equipmentAdapter.setUid(this.uid);
        equipmentAdapter.setUserMoney(this.userMoney);
    }


    public void sortType(View view) {
        if(selectedSortType == 0){
            selectedSortType = 1;
            sortOrderClick.setBackgroundResource(R.drawable.icon_arrow_down);
            setRecyclerView();
        } else {
            selectedSortType = 0;
            sortOrderClick.setBackgroundResource(R.drawable.icon_arrow_up);
            setRecyclerView();
        }
    }

    public void sortByValue(View view) {
        this.items.clear();
        selectedSortOrder = "name";
        setRecyclerView();
    }

    public void sortByAmount(View view) {
        this.items.clear();
        selectedSortOrder = "amount";
        setRecyclerView();
    }

    public void sortByName(View view) {
        this.items.clear();
        selectedSortOrder = "name";
        setRecyclerView();
    }

    public void sortByType(View view) {
        this.items.clear();
        selectedSortOrder = "type_name";
        setRecyclerView();
    }

    @Override
    public void onBackPressed() {
    }
}