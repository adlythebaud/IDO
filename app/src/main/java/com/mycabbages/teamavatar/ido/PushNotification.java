package com.mycabbages.teamavatar.ido;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.util.Date;

/**
 * Created by adlythebaud on 3/13/18.
 */

class PushNotification extends ContextWrapper {

    private String title;
    private Date dateToSend;
    private Notification notification;
    public String channelId = "some_channel_id";
    public CharSequence channelName = "Some Channel";
    private NotificationManager notificationManager;
    private final String TAG = "PushNotification";
    private Context context;

    public PushNotification(Context context) {
        super(context);
        this.context = context;
        setUpNotificationChannel();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDateToSend() {
        return dateToSend;
    }

    public void setDateToSend(Date dateToSend) {
        this.dateToSend = dateToSend;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    /**
     * Sets up the correct notification channel from the app.
     * */
    public void setUpNotificationChannel() {
        Log.d(TAG, "Build.version.SDK_INT: " + Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            if (notificationManager == null) {

                // get access to Notification Service through a Notification Manager object
                notificationManager =
                       (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

               int importance = NotificationManager.IMPORTANCE_LOW;

                NotificationChannel notificationChannel =
                        new NotificationChannel(channelId, channelName, importance);

                notificationManager.createNotificationChannel(notificationChannel);
            }

        }
    }

    /**
     * SEND TEST NOTIFICATION
     * Sends a test notification to the user.
     * */
    public void sendTestNotification() {

        Log.d(TAG, "version: " + Build.VERSION.SDK_INT);
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        final int NOTIFICATION_ID = 1;
        final String NOTIFICATION_CHANNEL_ID = "my_notification_channel";

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.alarm_sound_icon) // icon must be right size for all supported APIs
                .setContentTitle("IDO")
                .setContentText("Become A Better Spouse Today!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());

    }



}
