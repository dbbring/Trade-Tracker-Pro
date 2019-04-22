package com.example.tradetrackerpro;

import android.text.TextUtils;

/*
    Simple validation class. Add more methods here for validation purposes.
 */
public class Validation {

    public boolean isNullOrEmpty(String string){
        return TextUtils.isEmpty(string);
    }

    public boolean isNumeric(String string){
        if (TextUtils.isEmpty(string)) {
            return false;
        }
        return TextUtils.isDigitsOnly(string);
    }
}
