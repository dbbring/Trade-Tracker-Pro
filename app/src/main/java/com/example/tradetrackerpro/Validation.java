package com.example.tradetrackerpro;

import android.text.TextUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    public boolean isValidEmail(String string){
        if (TextUtils.isEmpty(string)) {
            return false;
        }
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }


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
