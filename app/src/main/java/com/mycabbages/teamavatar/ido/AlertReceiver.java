package com.mycabbages.teamavatar.ido;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;


public class AlertReceiver extends BroadcastReceiver {

    /**
     * ON RECEIVE
     * This method is called when our alarm is fired.
     * */
    @Override
    public void onReceive(Context context, Intent intent) {
        // show notification here using notification helper class.
        NotificationHelper notificationHelper = new NotificationHelper(context,
                "Some ChannelID",
                "Some Channel Name",
                NotificationManager.IMPORTANCE_HIGH);
        NotificationCompat.Builder nb = notificationHelper
                .getNotification("Test Title", "This is a test notification!");
        notificationHelper.getManager().notify(1, nb.build());

    }
}
