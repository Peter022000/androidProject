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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
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

    private ImageView sortOrderClick;

    private int selectedSortType;
    private String selectedSortOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_shop);

        sortOrderClick = findViewById(R.id.sortOrderIcon);
        selectedSortType = 0;
        selectedSortOrder = "name";
        sortOrderClick.setBackgroundResource(R.drawable.icon_arrow_up);

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
        storeItems(selectedSortOrder, selectedSortType);
        shopAdapter = new ShopAdapter(Shop.this, items);
        recyclerView.setAdapter(shopAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Shop.this));
        shopAdapter.setUid(this.uid);
        shopAdapter.setSid(this.sid);
        shopAdapter.setUserMoney(this.userMoney);
    }

    private void setShopName(){
        if(sid == 1) {
            shopName.setText("Arm`s smith");
        } else if( sid == 2) {
            shopName.setText("Armorer");
        } else if( sid == 3) {
            shopName.setText("Enchanter");
        } else if( sid == 4) {
            shopName.setText("Jeweler");
        }
    }

    public void ClickMenu(View view) {
        Drawer.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view){
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
                this.sid = 1;
                setRecyclerView();
                setShopName();
                return true;
            case R.id.shop2:
                this.sid = 2;
                setRecyclerView();
                setShopName();
                return true;
            case R.id.shop3:
                this.sid = 3;
                setRecyclerView();
                setShopName();
                return true;
            case R.id.shop4:
                this.sid = 4;
                setRecyclerView();
                setShopName();
                return true;
            default:
                return false;
        }
    }

    void storeItems(String orderBy, int sortType) {
        Cursor cursor = DatabaseHelper.readDataShop(sid, orderBy, sortType);

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
        selectedSortOrder = "value";
        setRecyclerView();
    }

    public void sortByAmount(View view) {
        selectedSortOrder = "amount";
        setRecyclerView();
    }

    public void sortByName(View view) {
        selectedSortOrder = "name";
        setRecyclerView();
    }

    public void sortByType(View view) {
        selectedSortOrder = "type_name";
        setRecyclerView();
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