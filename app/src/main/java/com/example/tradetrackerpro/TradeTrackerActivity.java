package com.example.tradetrackerpro;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import java.util.Calendar;

public  class TradeTrackerActivity extends AppCompatActivity {
    private ImageButton homeBtn;
    private ImageButton performanceBtn;
    private ImageButton entryBtn;
    private ImageButton settingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_tracker_activity);

        // Set up our daily alarm for 7:00PM to remind users to enter logs
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(this, AlarmReceiverForNotification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        // Set Time
        Calendar alarmStartTime = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        alarmStartTime.set(Calendar.HOUR_OF_DAY, 19);
        alarmStartTime.set(Calendar.MINUTE, 00);
        alarmStartTime.set(Calendar.SECOND, 0);
        // Increment each day for a repeating alarm
        if (now.after(alarmStartTime)) {
            alarmStartTime.add(Calendar.DATE, 1);
        }
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis() , AlarmManager.INTERVAL_DAY, pendingIntent);

        // Onload setup our constant layout that will never change (Buttons on bottom)
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
