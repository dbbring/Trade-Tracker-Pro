package com.example.tradetrackerpro;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Settings {
    private static Settings sSettings;
    private String mEmail;
    private String mCat1;
    private String mCat2;
    private String mCat3;
    private String mCat4;
    private String mCat5;
    private String mAcct1;
    private String mAcct2;
    private String mAcct3;
    private String mTradeCounter;
    private String mImportantMessage;


    private Settings(Context context) {
        try {
            InputStream inputStream = context.openFileInput("settings.txt");

            if (inputStream != null) {
                InputStreamReader reader = new InputStreamReader(inputStream);
                BufferedReader buff = new BufferedReader(reader);
                String receiveString = "";
                String[] InputSettingString;
                StringBuilder sb = new StringBuilder();

                while ((receiveString = buff.readLine()) != null) {
                    sb.append(receiveString);
                }

                inputStream.close();
                String tempStr = sb.toString();
                InputSettingString = tempStr.split(",");
                // I dont like this, Too closes coupled with the class. If you add a member to this
                // class be sure to write to the settings file in SettingsFragment.java so it will
                // be picked up here
                mEmail = InputSettingString[0];
                mCat1 = InputSettingString[1];
                mCat2 = InputSettingString[2];
                mCat3 = InputSettingString[3];
                mCat4 = InputSettingString[4];
                mCat5 = InputSettingString[5];
                mAcct1 = InputSettingString[6];
                mAcct2 = InputSettingString[7];
                mAcct3 = InputSettingString[8];
                mTradeCounter = InputSettingString[9];
                mImportantMessage = InputSettingString[10];
            }
            else {
                defaultSettings();
            }
        }
        catch (FileNotFoundException e) {
            Toast.makeText(context, "Settings File Not Found!", Toast.LENGTH_LONG).show();
            defaultSettings();
        } catch (IOException e) {
            Toast.makeText(context, "Cannot read file!", Toast.LENGTH_LONG);
            defaultSettings();
        }

    }

    private void defaultSettings() {
        mEmail = "";
        mCat1 = "";
        mCat2 = "";
        mCat3 = "";
        mCat4 = "";
        mCat5 = "";
        mAcct1 = "";
        mAcct2 = "";
        mAcct3 = "";
        mTradeCounter = "";
        mImportantMessage = "Welcome to Trade Tracker Pro!";
    }


    public static Settings get(Context context) {
        if (sSettings == null) {
            sSettings = new Settings(context);
            return sSettings;
        }
        else {
            return sSettings;
        }
    }


    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getCat1() {
        return mCat1;
    }

    public void setCat1(String cat1) {
        mCat1 = cat1;
    }

    public String getCat2() {
        return mCat2;
    }

    public void setCat2(String cat2) {
        mCat2 = cat2;
    }

    public String getCat3() {
        return mCat3;
    }

    public void setCat3(String cat3) {
        mCat3 = cat3;
    }

    public String getCat4() {
        return mCat4;
    }

    public void setCat4(String cat4) {
        mCat4 = cat4;
    }

    public String getCat5() {
        return mCat5;
    }

    public void setCat5(String cat5) {
        mCat5 = cat5;
    }

    public String getAcct1() {
        return mAcct1;
    }

    public void setAcct1(String acct1) {
        mAcct1 = acct1;
    }

    public String getAcct2() {
        return mAcct2;
    }

    public void setAcct2(String acct2) {
        mAcct2 = acct2;
    }

    public String getAcct3() {
        return mAcct3;
    }

    public void setAcct3(String acct3) {
        mAcct3 = acct3;
    }

    public String getTradeCounter() {
        return mTradeCounter;
    }

    public void setTradeCounter(String tradeCounter) {
        mTradeCounter = tradeCounter;
    }


    public String getImportantMessage() {
        return mImportantMessage;
    }

    public void setImportantMessage(String importantMessage) {
        mImportantMessage = importantMessage;
    }
}