package com.example.androidproject;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataValidator {

    static public boolean validateFields(List<String> credentials)
    {
        for (String x : credentials) {
            if(TextUtils.isEmpty(x))
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        return true;
    }

    static public boolean comparePasswords(String password, String confirmPassword)
    {
        if(password.equals(confirmPassword))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    static public boolean strongPasswordCheck(String password, Context context)
    {
        Pattern passwordPattern = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9])(?=.{8,})");
        Matcher passwordMatcher = passwordPattern.matcher(password);

        if(passwordMatcher.find())
        {
            //Password is strong
            Toast.makeText(context, "Password is strong", Toast.LENGTH_SHORT).show();
            return true;
        }
        else
        {
            Toast.makeText(context, "Password is not strong enough:" +
                    "\nPassword must contain at least one digit [0-9]" +
                    "\nPassword must contain at least one lowercase Latin character [a-z]" +
                    "\nPassword must contain at least one uppercase Latin character [A-Z]" +
                    "\nPassword must contain at least one special character like ! @ # &" +
                    "\nPassword must contain a length of at least 8 characters and a maximum of 20 characters", Toast.LENGTH_SHORT).show();

            return false;
        }
    }

    static public boolean validateEmail(String email, Context context)
    {
        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        Matcher emailMatcher = emailPattern.matcher(email);
        if(emailMatcher.find())
        {
            Toast.makeText(context, "Email good", Toast.LENGTH_SHORT).show();
            return true;
        }
        else
        {
            Toast.makeText(context, "Email is invalid.", Toast.LENGTH_SHORT).show();

            return false;
        }
    }

}
