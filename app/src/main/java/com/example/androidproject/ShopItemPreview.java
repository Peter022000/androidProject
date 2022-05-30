package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class ShopItemPreview extends AppCompatActivity {

    DrawerLayout drawerLayout;


    private int iid;
    private int uid;
    private int sid;
    private int userMoney;

    private String name;
    private String description;
    private int value;
    private int weight;
    private int amount;

    private TextView item_name;
    private TextView item_description;
    private TextView item_value;
    private TextView item_weight;
    private TextView item_amount;
    private TextView money;
    DatabaseHelper DatabaseHelper;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_shop_item_preview);

        preferences = getSharedPreferences("UserCredentials", MODE_PRIVATE);
        editor = preferences.edit();

        uid = preferences.getInt("UID_KEY",-1);
        userMoney = preferences.getInt("MONEY_KEY",-1);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            iid = extras.getInt("iid");
            name = extras.getString("name");
            description = extras.getString("description");
            value = extras.getInt("value");
            weight = extras.getInt("weight");
            amount = extras.getInt("amount");
            sid = extras.getInt("sid");
        }

        item_name = findViewById(R.id.item_name);
        item_description = findViewById(R.id.item_description);
        item_value = findViewById(R.id.item_value);
        item_weight = findViewById(R.id.item_weight);
        item_amount = findViewById(R.id.item_amount);
        money = findViewById(R.id.money);

        item_name.setText(this.name);
        item_description.setText(this.description);
        item_description.setMovementMethod(new ScrollingMovementMethod());
        item_value.setText(String.valueOf(this.value));
        item_weight.setText(String.valueOf(this.weight));
        item_amount.setText(String.valueOf(this.amount));
        money.setText(String.valueOf(this.userMoney));

        drawerLayout = findViewById(R.id.drawer_layout);
    }

    public void buyItem(View view)
    {
        if(value < userMoney)
        {
            this.amount--;
            this.userMoney -= value;

            editor.putInt("MONEY_KEY", this.userMoney);
            editor.commit();

            DatabaseHelper = new DatabaseHelper(ShopItemPreview.this);
            DatabaseHelper.deleteItemFromShop(sid, iid, amount);
            item_amount.setText(String.valueOf(amount));
            money.setText(String.valueOf(userMoney));
            DatabaseHelper.addItemToEquipment(this.uid,iid);
            DatabaseHelper.updateMoney(this.uid, this.userMoney);

            if(this.amount == 0) {
                Intent intent = new Intent(this,Shop.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("sid", sid);
                startActivity(intent);
            }
            Toast.makeText(this, this.name+" has been purchased", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Not enough money", Toast.LENGTH_SHORT).show();
        }
    }

    public void goBackToShop(View view) {
        Intent intent = new Intent(this,Shop.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("sid", sid);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
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
        Drawer.redirectActivity(this,Equipment.class);
    }

    public void ClickShop(View view){
        Drawer.redirectActivity(this, Shop.class);
    }

    public void ClickAboutUs(View view){
        Drawer.redirectActivity(this, AboutUs.class);
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

    @Override
    protected void onPause() {
        super.onPause();
        Drawer.closeDrawer(drawerLayout);
    }
}