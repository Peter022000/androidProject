package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Shop extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    DrawerLayout drawerLayout;

    private int sid;
    private int uid;
    private int userMoney;

    private TextView shopName;

    DatabaseHelper DatabaseHelper;

    ArrayList<Item> items;
    ShopAdapter shopAdapter;
    RecyclerView recyclerView;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_shop);

        preferences = getSharedPreferences("UserCredentials", MODE_PRIVATE);
        editor = preferences.edit();

        uid = preferences.getInt("UID_KEY",-1);
        userMoney = preferences.getInt("MONEY_KEY",-1);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            sid = extras.getInt("sid");
        }


        shopName = findViewById(R.id.shopName);

        DatabaseHelper = new DatabaseHelper(Shop.this);
        items = new ArrayList<Item>();

        recyclerView = findViewById(R.id.recyclerViewShop);

        setRecyclerView();

        drawerLayout = findViewById(R.id.drawer_layout);
    }

    private void setRecyclerView()
    {
        this.items.clear();
        storeItems();
        shopAdapter = new ShopAdapter(Shop.this, items);
        recyclerView.setAdapter(shopAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Shop.this));
        shopAdapter.setUid(this.uid);
        shopAdapter.setSid(this.sid);
        shopAdapter.setUserMoney(this.userMoney);

        setShopName();
    }

    private void setShopName(){
        if(sid == 0) {
            shopName.setText("Shop 1");
        } else if( sid == 1) {
            shopName.setText("Shop 2");
        } else if( sid == 2) {
            shopName.setText("Shop 3");
        }
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
        Drawer.redirectActivity(this, Equipment.class);
    }

    public void ClickShop(View view){
        //Drawer.redirectActivity(this, Shop.class);
        recreate();
    }


    public void ClickAboutUs(View view){
        Drawer.redirectActivity(this,AboutUs.class);
    }

    public void ClickUserProfile(View view){
        Drawer.redirectActivity(this, ProfileActivity.class);
    }

    public void ClickLogout(View view){
        editor.clear();
        editor.commit();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.shop1:
                this.sid = 0;
                setRecyclerView();
                return true;
            case R.id.shop2:
                this.sid = 1;
                setRecyclerView();
                return true;
            case R.id.shop3:
                this.sid = 2;
                setRecyclerView();
                return true;
            case R.id.shop4:
                this.sid = 3;
                setRecyclerView();
                return true;
            default:
                return false;
        }
    }

    void storeItems() {
        Cursor cursor = DatabaseHelper.readDataShop(sid);

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
    protected void onPause() {
        super.onPause();
        Drawer.closeDrawer(drawerLayout);
    }

    @Override
    public void onBackPressed() {
    }
}