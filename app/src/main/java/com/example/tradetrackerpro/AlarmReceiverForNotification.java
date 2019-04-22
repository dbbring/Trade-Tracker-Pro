package com.example.tradetrackerpro;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiverForNotification extends BroadcastReceiver {

    // When we receive our alarm fire up the Job service intent so we can display our notification
    @Override
    public void onReceive(Context context, Intent intent) {
        ComponentName comp = new ComponentName(context.getPackageName(),
                NotifyService.class.getName());

        NotifyService.enqueueWork(context, (intent.setComponent(comp)));
    }


}


