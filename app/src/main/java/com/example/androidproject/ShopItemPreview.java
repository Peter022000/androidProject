package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class ShopItemPreview extends AppCompatActivity {

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
    MyDatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_shop_item_preview);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            iid = extras.getInt("iid");
            name = extras.getString("name");
            description = extras.getString("description");
            value = extras.getInt("value");
            weight = extras.getInt("weight");
            amount = extras.getInt("amount");
            uid = extras.getInt("uid");
            sid = extras.getInt("sid");
            userMoney = extras.getInt("userMoney");
        }

        item_name = findViewById(R.id.item_name);
        item_description = findViewById(R.id.item_description);
        item_value = findViewById(R.id.item_value);
        item_weight = findViewById(R.id.item_weight);
        item_amount = findViewById(R.id.item_amount);
        money = findViewById(R.id.money);

        item_name.setText(this.name);
        item_description.setText(this.description);
        item_value.setText(String.valueOf(this.value));
        item_weight.setText(String.valueOf(this.weight));
        item_amount.setText(String.valueOf(this.amount));
        money.setText(String.valueOf(this.userMoney));
    }

    public void buyItem(View view)
    {
        if(value < userMoney)
        {
            this.amount--;
            this.userMoney -= value;
            myDatabaseHelper = new MyDatabaseHelper(ShopItemPreview.this);
            Log.d("aaaabbbbbbaaaa" , sid + "  " + iid + "  " + amount);
            myDatabaseHelper.deleteItemFromShop(sid, iid, amount);
            item_amount.setText(String.valueOf(amount));
            money.setText(String.valueOf(userMoney));
            myDatabaseHelper.addItemToEquipment(this.uid,iid);
            //myDatabaseHelper.changeUserMoney
            if(this.amount == 0) {
                Drawer.redirectActivity(this, Shop.class, this.uid, this.userMoney);
            }
            Toast.makeText(this, this.name+" has been purchased", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Not enough money", Toast.LENGTH_SHORT).show();
        }
    }

    public void goBackToShop(View view) {
        //Drawer.redirectActivity(this, Shop.class, this.uid, this.userMoney);
        Intent intent = new Intent(this,Shop.class);
        intent.putExtra("uid", uid);
        intent.putExtra("sid", sid);
        intent.putExtra("userMoney", userMoney);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
    }
}