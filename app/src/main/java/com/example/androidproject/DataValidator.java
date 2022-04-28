package com.example.androidproject;

import android.text.TextUtils;

import java.util.List;

public class DataValidator {

    static public boolean ValidateFields(List<String> credentials)
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

}
