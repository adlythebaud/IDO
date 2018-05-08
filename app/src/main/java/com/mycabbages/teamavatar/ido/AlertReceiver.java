package com.mycabbages.teamavatar.ido;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


public class AlertReceiver extends BroadcastReceiver {
    /**
     * ON RECEIVE
     * This method is called when our alarm is fired.
     * */

    String title;
    String message;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(String.valueOf(R.string.notification),
                "Alarm fired, we created a notification and showed it");

        title = intent.getStringExtra("title");
        message = intent.getStringExtra("message");

        Log.d(String.valueOf(R.string.notification), title + " " + message);

        // show notification here using notification helper class.
        NotificationHelper notificationHelper = new NotificationHelper(context,
                "Some ChannelID",
                "Some Channel Name",
                NotificationManager.IMPORTANCE_HIGH);
        NotificationCompat.Builder nb = notificationHelper
                .getNotification(title, message);
        notificationHelper.getManager().notify(1, nb.build());

    }


}
