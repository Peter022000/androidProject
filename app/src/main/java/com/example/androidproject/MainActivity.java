package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText loginField;
    private EditText passwordField;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Disables action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        loginField = findViewById(R.id.loginField);
        passwordField = findViewById(R.id.passwordField);

        //Load data at first aplication start
        SharedPreferences prefs = getSharedPreferences("prefsfirstStart", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        if (firstStart) {
            loadData();
        }


        preferences = getSharedPreferences("UserCredentials", MODE_PRIVATE);
        editor = preferences.edit();
//        Log.i("Login:", preferences.getString("LOGIN_KEY",""));
//        Log.i("Password:", preferences.getString("PASSWORD_KEY",""));

        if (preferences.getString("KEEP_LOGGED_KEY","false").equals("true")) {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        } else {
        }
    }

    private void loadData() {
        DatabaseHelper DatabaseHelper = new DatabaseHelper(MainActivity.this);

        DatabaseHelper.addType("Weapon");
        DatabaseHelper.addType("Armor");
        DatabaseHelper.addType("Magic Item");
        DatabaseHelper.addType("Gemstone");
            ////////--------------------------Arms
        DatabaseHelper.addItem("Battleaxe", 1, "Proficiency with a battleaxe allows you to add your proficiency " + "bonus to the attack roll for any attack you make with it.", 10,4);
        DatabaseHelper.addItem("Blowgun", 1, "Proficiency with a blowgun allows you to add your proficiency " + "bonus to the attack roll for any attack you make with it.", 10,1);
        DatabaseHelper.addItem("Boomerang", 1, "The boomerang is a ranged weapon, and any creature proficient with the javelin is also proficient with this weapon.\n" +
                "\n" +
                "On a miss, a boomerang returns to the thrower's hand.", 5,1);
        DatabaseHelper.addItem("Club", 1, "Proficiency with a club allows you to add your proficiency bonus to the attack roll for any attack you make with it.", 1,2);
        DatabaseHelper.addItem("Crossbow, hand", 1, "Proficiency with a hand crossbow allows you to add your proficiency bonus to the attack roll for any attack you make with it.", 75,3);
        DatabaseHelper.addItem("Crossbow, heavy", 1, "Proficiency with a heavy crossbow allows you to add your proficiency bonus to the attack roll for any attack you make with it.", 50,18);
        DatabaseHelper.addItem("Crossbow, light", 1, "Proficiency with a light crossbow allows you to add your proficiency bonus to the attack roll for any attack you make with it.", 25,5);
        DatabaseHelper.addItem("Dagger", 1, "Proficiency with a dagger allows you to add your proficiency bonus to the attack roll for any attack you make with it.", 2,1);
        DatabaseHelper.addItem("Flail", 1, "Proficiency with a flail allows you to add your proficiency bonus to the attack roll for any attack you make with it.", 10,2);
        DatabaseHelper.addItem("Glaive", 1, "Proficiency with a glaive allows you to add your proficiency bonus to the attack roll for any attack you make with it.", 20,6);
            ////////--------------------------Armor
        DatabaseHelper.addItem("Breastplate", 2,"This armor consists of a fitted metal chest piece worn with supple leather.", 400,20);
        DatabaseHelper.addItem("Chain Mail", 2,"Made of interlocking metal rings, chain mail includes a layer of quilted fabric worn underneath the mail to prevent chafing and to cushion the impact of blows.", 75,55);
        DatabaseHelper.addItem("Chain Shirt", 2,"Made of interlocking metal rings, a chain shirt is worn between layers of clothing or leather.", 50,20);
        DatabaseHelper.addItem("Half Plate", 2,"Half plate consists of shaped metal plates that cover most of the wearer's body. ", 750,40);
        DatabaseHelper.addItem("Hide", 2,"This crude armor consists of thick furs and pelts.", 10,12);
        DatabaseHelper.addItem("Leather", 2,"The breastplate and shoulder protectors of this armor are made of leather that has been stiffened by being boiled in oil.", 10,10);
        DatabaseHelper.addItem("Padded", 2,"Padded armor consists of quilted layers of cloth and batting.", 5,8);
        DatabaseHelper.addItem("Plate", 2,"Plate consists of shaped, interlocking metal plates to cover the entire body.", 1500,65);
        DatabaseHelper.addItem("Ring Mail", 2,"This armor is leather armor with heavy rings sewn into it.", 30,40);
        DatabaseHelper.addItem("Spiked Armor", 2,"Spiked armor is a rare type of medium armor made by dwarves. It consists of a leather coat and leggings covered with spikes that are usually made of metal.", 75,45);
            ////////--------------------------Magic Items
        DatabaseHelper.addItem("Berserker Axe", 3,"You gain a +1 bonus to attack and damage rolls made with this magic weapon. In addition, while you are attuned to this weapon, your hit point maximum increases by 1 for each level you have attained.", 9999,4);
        DatabaseHelper.addItem("Dagger of Venom", 3,"You gain a +1 bonus to attack and damage rolls made with this magic weapon.", 9999,1);
        DatabaseHelper.addItem("Arrow of Slaying", 3,"An arrow of slaying is a magic weapon meant to slay a particular kind of creature. " +
                "Some are more focused than others; for example, there are both arrows of dragon slaying and arrows of blue dragon slaying. " +
                "If a creature belonging to the type, race, or group associated with an arrow of slaying takes damage from the arrow, the creature must make a DC 17 Constitution saving throw, " +
                "taking an extra 6d10 piercing damage on a failed save, or half as much extra damage on a successful one.", 999,1);
        DatabaseHelper.addItem("Dancing Sword", 3,"You can use a bonus action to toss this magic sword into the air and speak the command word. When you do so, the sword begins to hover, flies up to 30 feet, " +
                "and attacks one creature of your choice within 5 feet of it. The sword uses your attack roll and ability score modifier to damage rolls.", 999,15);
        DatabaseHelper.addItem("Defender", 3,"You gain a +3 bonus to attack and damage rolls made with this magic weapon.", 9999,20);
        DatabaseHelper.addItem("Dragon Slayer", 3,"You gain a +1 bonus to attack and damage rolls made with this magic weapon.\n" +
                "\n" +
                "When you hit a dragon with this weapon, the dragon takes an extra 3d6 damage. For the purpose of this weapon, \"dragon\" " +
                "refers to any creature with the dragon type, including dragon turtles and wyverns.", 999,15);
        DatabaseHelper.addItem("Dwarven Thrower", 3,"ou gain a +3 bonus to attack and damage rolls made with this magic weapon. It has the thrown property with a normal range of 20 feet and a long range of 60 feet. " +
                "When you hit with a ranged attack using this weapon, " +
                "it deals an extra 1d8 damage or, if the target is a giant, " +
                "2d8 damage. Immediately after the attack, the weapon flies back to your hand.", 999,35);
        DatabaseHelper.addItem("Flame Tongue", 3,"You can use a bonus action to speak this magic sword's command word, " +
                "causing flames to erupt from the blade. These flames shed bright light in a 40-foot radius and dim light for an additional 40 feet.", 999,3);
        DatabaseHelper.addItem("Frost Brand", 3,"When you hit with an attack using this magic sword, the target takes an extra 1d6 cold damage. In addition, while you hold the sword, you have resistance to fire damage.\n" +
                "\n" +
                "In freezing temperatures, the blade sheds bright light in a 10-foot radius and dim light for an additional 10 feet.", 999,15);
        DatabaseHelper.addItem(" Giant Slayer", 3,"You gain a +1 bonus to attack and damage rolls made with this magic weapon.\n" +
                "\n" +
                "When you hit a giant with it, the giant takes an extra 2d6 damage of the weapon's type and must succeed on a DC 15 Strength saving throw or fall prone. For the purpose of this weapon, \"giant\" " +
                "refers to any creature with the giant type, including ettins and trolls.", 9999,3);
            ////////--------------------------Miscellaneous
        DatabaseHelper.addItem("Alexandrite", 4,"A transparent dark green gemstone worth 500 gold pieces.", 500,1);
        DatabaseHelper.addItem("Amber", 4,"A transparent watery gold to rich gold gemstone worth 100 gold pieces.", 100,1);
        DatabaseHelper.addItem("Amethyst", 4,"A transparent deep purple gemstone worth 100 gold pieces.", 100,1);
        DatabaseHelper.addItem("Aquamarine", 4,"A transparent pale blue-green gemstone worth 500 gold pieces.", 500,1);
        DatabaseHelper.addItem("Azurite", 4,"An opaque mottled deep blue gemstone worth 10 gold pieces.", 10,1);
        DatabaseHelper.addItem("Banded Agate", 4,"A translucent striped brown, blue, white, or red gemstone worth 10 gold pieces.", 10,1);
        DatabaseHelper.addItem("Black Opal", 4,"A translucent dark green with black mottling and golden flecks gemstone worth 1,000 gold pieces.", 1000,1);
        DatabaseHelper.addItem("Black Pearl", 4,"An opaque pure black gemstone worth 500 gold pieces.", 500,1);
        DatabaseHelper.addItem("Black Sapphire", 4,"A translucent lustrous black gemstone with glowing highlights worth 5,000 gold pieces.", 5000,1);
        DatabaseHelper.addItem("Bloodstone", 4,"An opaque dark gray gemstone with red flecks worth 50 gold pieces", 50,1);

            ////////--------------------------Arms
        DatabaseHelper.addItemToShop(1,1,2);
        DatabaseHelper.addItemToShop(1,2,2);
        DatabaseHelper.addItemToShop(1,3,3);
        DatabaseHelper.addItemToShop(1,4,4);
        DatabaseHelper.addItemToShop(1,5,6);
        DatabaseHelper.addItemToShop(1,6,2);
        DatabaseHelper.addItemToShop(1,7,4);
        DatabaseHelper.addItemToShop(1,8,8);
        DatabaseHelper.addItemToShop(1,9,4);
        DatabaseHelper.addItemToShop(1,10,2);

            ////////--------------------------Armor
        DatabaseHelper.addItemToShop(2,11,3);
        DatabaseHelper.addItemToShop(2,12,4);
        DatabaseHelper.addItemToShop(2,13,4);
        DatabaseHelper.addItemToShop(2,14,2);
        DatabaseHelper.addItemToShop(2,15,5);
        DatabaseHelper.addItemToShop(2,16,7);
        DatabaseHelper.addItemToShop(2,17,4);
        DatabaseHelper.addItemToShop(2,18,2);
        DatabaseHelper.addItemToShop(2,19,4);
        DatabaseHelper.addItemToShop(2,20,3);

            ////////--------------------------Magic Items
        DatabaseHelper.addItemToShop(3,21,3);
        DatabaseHelper.addItemToShop(3,22,4);
        DatabaseHelper.addItemToShop(3,23,4);
        DatabaseHelper.addItemToShop(3,24,2);
        DatabaseHelper.addItemToShop(3,25,5);
        DatabaseHelper.addItemToShop(3,26,7);
        DatabaseHelper.addItemToShop(3,27,4);
        DatabaseHelper.addItemToShop(3,28,2);
        DatabaseHelper.addItemToShop(3,29,4);
        DatabaseHelper.addItemToShop(3,30,3);

            ////////--------------------------Gemstones
        DatabaseHelper.addItemToShop(4,31,3);
        DatabaseHelper.addItemToShop(4,32,4);
        DatabaseHelper.addItemToShop(4,33,4);
        DatabaseHelper.addItemToShop(4,34,2);
        DatabaseHelper.addItemToShop(4,35,5);
        DatabaseHelper.addItemToShop(4,36,7);
        DatabaseHelper.addItemToShop(4,37,4);
        DatabaseHelper.addItemToShop(4,38,2);
        DatabaseHelper.addItemToShop(4,39,4);
        DatabaseHelper.addItemToShop(4,40,3);



        SharedPreferences prefs = getSharedPreferences("prefsfirstStart", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }


    //Go to SignUp acitivity
    public void signIn(View view) {
        //List of fields to check
        List<String> credentials = new ArrayList<String>();
        credentials.add(loginField.getText().toString());
        credentials.add(passwordField.getText().toString());

        if (!DataValidator.validateFields(credentials)) {
            Toast.makeText(getApplicationContext(), "Some fields are empty!", Toast.LENGTH_SHORT).show();
        } else {
            DatabaseHelper db = new DatabaseHelper(MainActivity.this);
            if (db.checkUser(loginField.getText().toString(), passwordField.getText().toString())) {
                //Toast.makeText(getApplicationContext(), "Success find user", Toast.LENGTH_SHORT).show();

                ArrayList<Integer> IDAndMoney = db.returnIDAndMoney(loginField.getText().toString());

                Intent intent = new Intent(this, Drawer.class);
                intent.putExtra("uid",IDAndMoney.get(0));
                intent.putExtra("userMoney",IDAndMoney.get(1));
                startActivity(intent);

                editor.putString("LOGIN_KEY", loginField.getText().toString());
                editor.putString("PASSWORD_KEY", passwordField.getText().toString());
                editor.putInt("UID_KEY", IDAndMoney.get(0));
                editor.putInt("MONEY_KEY", IDAndMoney.get(1));
                editor.commit();
//                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
//                startActivity(intent);
            } else {
                    Toast.makeText(getApplicationContext(), "Credentials are wrong.", Toast.LENGTH_SHORT).show();
            }
            //Here will be goto Drawer activity after successfully validation
                //Intent intent = new Intent(this, Drawer.class);
                //startActivity(intent);
        }
    }

    //Go to SignUp acitivity
    public void signUp(View view)
    {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    //Go to forgotPasswordAcitivity
    public void forgotPassword(View view)
    {
        Intent intent = new Intent(this, ForgotPassword.class);
        startActivity(intent);
    }

    //Wczytanie danych loginu z sharedpreferences
    private String loadLogin(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String login = sharedPreferences.getString("LOGIN_KEY", "");
        loginField.setText(login);
        return login;
    }

    //Wczytanie danych loginu z sharedpreferences
    private String loadPassword(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String password = sharedPreferences.getString("PASSWORD_KEY", "");
        loginField.setText(password);
        return password;
    }

    //Zapisanie danych loginu do sharedpreferences
    private void saveLogin(String login, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("LOGIN_KEY", login);
        editor.putString("PASSWORD_KEY", password);
        editor.apply();
    }

    //Przejscie do kolejnego activity, jezeli login zostal podany
    public void setLogin(View view){
        String login = loginField.getText().toString();
        String password = passwordField.getText().toString();
        //Intent intent = new Intent(this, DrawerActivity.class); //Open new activity
        //intent.putExtra("ACTIVITY", "MainActivity");
        //intent.putExtra("KEY_YOUR_LOGIN", login); //Pass variable login ro another activity
        saveLogin(login, password);
        //startActivity(intent);
    }
}