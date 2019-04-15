package com.example.tradetrackerpro;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


/*
====================== TODO ====================
Figure Net Change for Detailed Entry View.

Add 3:05 cst reminder

export to csv
set csv file name to something decent
use implicent intent to send the csv to w.e. the user wants to share with

% change and $ change on home screen will represent the trade journal activity over the last week, so all gains minus all losses
Populate charts with real data
Update exit descrip from detail view onStop()
have to update exit price for entry only jounral logs
details view, have labels so we are not just setting Exit: $ via the set text option otherwise it will get overwrote
 */

public  class TradeTrackerActivity extends AppCompatActivity {
    private ImageButton homeBtn;
    private ImageButton performanceBtn;
    private ImageButton entryBtn;
    private ImageButton settingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_tracker_activity);

        FragmentManager fm = getSupportFragmentManager();
        Fragment homeFragment = fm.findFragmentById((R.id.home_layout));

        // ============================= Load home Fragment on start up ===========================

        if(homeFragment == null) {
            BaseFragment homeFrag = new HomeFragment();
            homeFrag.setLayout(R.layout.home);
            fm.beginTransaction().add(R.id.main_layout, homeFrag).commit();
        }

        homeBtn = (ImageButton) findViewById(R.id.home_btn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseFragment homeFrag = new HomeFragment();
                homeFrag.setLayout(R.layout.home);
                FragmentTransaction transact = getSupportFragmentManager().beginTransaction();
                transact.replace(R.id.main_layout, homeFrag);
                transact.commit();
            }
        });

        performanceBtn = (ImageButton) findViewById(R.id.performance_btn);
        performanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TradeDetailFragment performanceFrag = new TradeDetailFragment();
                FragmentTransaction transact = getSupportFragmentManager().beginTransaction();
                transact.replace(R.id.main_layout, performanceFrag);
                transact.commit();
            }
        });

        entryBtn = (ImageButton) findViewById(R.id.entry_button);
        entryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TradeEntryFragment entryFrag = new TradeEntryFragment();
                entryFrag.setLayout(R.layout.entry);
                FragmentTransaction transact = getSupportFragmentManager().beginTransaction();
                transact.replace(R.id.main_layout, entryFrag);
                transact.commit();

            }
        });

        settingsBtn = (ImageButton) findViewById(R.id.settings_button);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseFragment settingsFrag = new SettingsFragment();
                settingsFrag.setLayout(R.layout.settings);
                FragmentTransaction transact = getSupportFragmentManager().beginTransaction();
                transact.replace(R.id.main_layout, settingsFrag);
                transact.commit();
            }
        });

    }
}
