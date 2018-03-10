package com.mycabbages.teamavatar.ido;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class NotifSettingsActivity extends AppCompatActivity
        implements TimePickerFragment.TimePickerFragmentListener {

    private final String TAG = "NotifSettingsActivity";
    public EditText timePickerEditText;
    public Toolbar myToolbar;
    private CheckBox mondayCheckbox;
    private CheckBox tuesdayCheckbox;
    private CheckBox wednesdayCheckbox;
    private CheckBox thursdayCheckbox;
    private CheckBox fridayCheckbox;
    private CheckBox saturdayCheckbox;
    private CheckBox sundayCheckbox;
    private Spinner mSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif_settings);

        // get UI elements
        getUIElements();

        // set up notifications
        setUpNotificationChannel();

        // set the toolbar as the action bar for the activity.
        setSupportActionBar(myToolbar);

        // get a reference to the action bar
        ActionBar actionBar = getSupportActionBar();

        // set the action bar title
        actionBar.setTitle("Notification Settings Activity");

        // Display back arrow (up button) in top left and enable it.
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notif_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.done) {
            Log.d(TAG, "Done button tapped");

            // return to main. This may need to be changed so we can return to calling activity.
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * GET UI ELEMENTS
     * Get references to all the UI elements so we can use them.
     * */
    public void getUIElements() {
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        timePickerEditText = (EditText) findViewById(R.id.timePickerEditText);
        getCheckBoxes();
        mSpinner = (Spinner) findViewById(R.id.alarm_spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout:
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.alarm_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        mSpinner.setAdapter(adapter);
    }

    /**
     * SHOW TIME PICKER DIALOG
     * */
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = TimePickerFragment.newInstance(this);
        newFragment.show(getSupportFragmentManager(), "timePicker");
        Log.d(TAG, "timepicker was clicked");
    }

    /**
     * ON TIME SET
     * Display the chosen time.
     * */
    @Override
    public void onTimeSet(Date date) {
        // make a gregorian calendar object, set the time to passed in Date object
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        // Create a SimpleDateFormat object with format being "2:05" ("h:mm")
        // https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
        SimpleDateFormat localDateFormat = new SimpleDateFormat("h:mm");

        // Create a string of the chosen time from the passed in date, formatted correctly.
        String time = localDateFormat.format(date);

        // Set the text.
        timePickerEditText.setText(time);
    }

    /**
     * GET CHECK BOXES
     * hook up activity variables to UI components
     * */
    public void getCheckBoxes() {
        mondayCheckbox = (CheckBox) findViewById(R.id.mondayCheckbox);
        tuesdayCheckbox = (CheckBox) findViewById(R.id.tuesdayCheckbox);
        wednesdayCheckbox = (CheckBox) findViewById(R.id.wednesdayCheckbox);
        thursdayCheckbox = (CheckBox) findViewById(R.id.thursdayCheckbox);
        fridayCheckbox = (CheckBox) findViewById(R.id.fridayCheckBox);
        saturdayCheckbox = (CheckBox) findViewById(R.id.saturdayCheckbox);
        sundayCheckbox = (CheckBox) findViewById(R.id.sundayCheckbox);
    }

    /**
     * Set up notifications from the app.
     * */
    public void setUpNotificationChannel() {
        Log.d(TAG, "Build.version.SDK_INT: " + Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // get access to Notification Service through a Notification Manager object
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            String channelId = "some_channel_id";
            CharSequence channelName = "Some Channel";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    /**
     * SEND TEST NOTIFICATION
     * testing out push notification functionality and frequency
     * */
    public void sendTestNotification(View view) {

        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,
                "some_channel_id")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Test Notification!")
                .setContentText("This is a test notification. Does this work?")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("This is a test notification. Does this work?"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(001, mBuilder.build());


    }


}
