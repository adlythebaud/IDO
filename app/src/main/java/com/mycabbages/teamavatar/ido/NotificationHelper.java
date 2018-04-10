package com.mycabbages.teamavatar.ido;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.v4.app.NotificationCompat;

/**
 * NOTIFICATION HELPER
 * A helper class to help set up notifications before they are sent off.
 * */
public class NotificationHelper extends ContextWrapper {

    public final static String TAG = "NotificationHelper";
    private String channelID;
    private String channelName;
    private int importance;

    private NotificationManager mManager;
    /**
     * CONSTRUCTOR
     * */
    public NotificationHelper(Context base, String channelID, String channelName, int importance) {
        super(base);
        this.channelID = channelID;
        this.channelName = channelName;
        this.importance = importance;
        createChannel(channelID, channelName, importance);
        createNotificationManager();
    }

    /**
     * CREATE CHANNEL
     * Create the notification channel.
     * You can add lights and vibration patterns to the notification channel here in code.
     * */
    public void createChannel(String channelID, String channelName, int importance) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelID, channelName, importance);

            this.channelID = channelID;
            this.channelName = channelName;
            this.importance = importance;

            // this can be changed in code as well.
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            createNotificationManager();
            mManager.createNotificationChannel(channel);
        }
    }

    /**
     * CREATE NOTIFICATION MANAGER
     * */
    public void createNotificationManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
    }

    /**
     * GET MANAGER
     * */
    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    /**
     * SET MANAGER
     * */
    public void setManager(NotificationManager mManager) {
        this.mManager = mManager;
    }

    public NotificationCompat.Builder getNotification(String title, String message) {
        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.alarm_sound_icon);
    }
}
