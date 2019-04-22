package com.example.tradetrackerpro;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

public class NotifyService extends JobIntentService {
    // Service unique ID
    static final int SERVICE_JOB_ID = 50;

    // Enqueuing work in to this service.
    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, NotifyService.class, SERVICE_JOB_ID, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        onHandleIntent(intent);
    }

    private void onHandleIntent(Intent intent) {
        initChannels(getApplicationContext());
        Intent i = new Intent(this, TradeTrackerActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, i ,0);

        Notification notify = new NotificationCompat.Builder(this, "TTP")
                .setTicker("Trade Tracker Pro")
                .setSmallIcon(R.drawable.ic_notifiy)
                .setContentTitle("All Caught Up?")
                .setContentText("Have You Entered All Your Trades For The Day?")
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();
        NotificationManagerCompat notifyManager = NotificationManagerCompat.from(this);
        notifyManager.notify(0, notify);
    }

    // Appearently since Oreo, you have to have a channel ID for notifications...
    public void initChannels(Context context) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel("TTP",
                "Trade Tracker Pro",
                NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("Reminding Alert");
        notificationManager.createNotificationChannel(channel);
    }

}
