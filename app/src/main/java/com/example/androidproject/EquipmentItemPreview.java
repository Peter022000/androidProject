package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class EquipmentItemPreview extends AppCompatActivity {

    private int iid;
    private int uid;
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
    DatabaseHelper DatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_equipment_item_preview);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            iid = extras.getInt("iid");
            name = extras.getString("name");
            description = extras.getString("description");
            value = extras.getInt("value");
            weight = extras.getInt("weight");
            amount = extras.getInt("amount");
            uid = extras.getInt("uid");
            userMoney = extras.getInt("userMoney");
        }

        item_name = findViewById(R.id.item_name);
        item_description = findViewById(R.id.item_description);
        item_value = findViewById(R.id.item_value);
        item_weight = findViewById(R.id.item_weight);
        item_amount = findViewById(R.id.item_amount);

        item_name.setText(this.name);
        item_description.setText(this.description);
        item_value.setText(String.valueOf(this.value));
        item_weight.setText(String.valueOf(this.weight));
        item_amount.setText(String.valueOf(this.amount));
    }

    public void deleteItemFromEquipment(View view)
    {
        amount--;
        DatabaseHelper = new DatabaseHelper(EquipmentItemPreview.this);
        DatabaseHelper.deleteItemFromEquipment(uid, iid, amount);
        item_amount.setText(String.valueOf(amount));
        if(this.amount == 0) {
            Drawer.redirectActivity(this, Equipment.class, this.uid, this.userMoney);
        }
    }

    public void goBackToEquipment(View view)
    {
        Drawer.redirectActivity(this, Equipment.class, this.uid, this.userMoney);
    }

    @Override
    public void onBackPressed() {
    }
}