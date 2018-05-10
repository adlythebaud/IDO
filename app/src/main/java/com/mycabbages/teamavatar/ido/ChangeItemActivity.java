package com.mycabbages.teamavatar.ido;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ChangeItemActivity extends AppCompatActivity implements DatePickerFragment.DatePickerFragmentListener,
        TimePickerFragment.TimePickerFragmentListener {

    private final String TAG = "ChangeItemActivity";
    private ConstraintLayout mConstraintLayout; // ConstraintLayout containing delete button
    private Switch mSwitch;
    private EditText datePickerEditText;
    private Button goalPickerButton;
    private Button deleteButton;
    private ConstraintLayout notifConstraintLayout;
    private EditText descriptionEditText;
    private EditText timePickerEditText;
    private Calendar datePickerCalendar;
    private Calendar timePickerCalendar;
    private String title;
    private String message;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_item);

        // get references to everything we need in the UI.
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        mSwitch = (Switch) findViewById(R.id.notificationSettingsSwitch);
        datePickerEditText = (EditText) findViewById(R.id.datePickerEditText);
        goalPickerButton = (Button) findViewById(R.id.goalPickerButton);
        notifConstraintLayout = (ConstraintLayout) findViewById(R.id.notifSettingsConstraintLayout);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        timePickerEditText = (EditText) findViewById(R.id.timePickerEditText);

        Bundle extra = getIntent().getExtras();
        if (extra.getString("Title") != null) {
            Log.d(TAG, "The string has contents.....");
            goalPickerButton.setText(extra.getString("Title"));
            title = "Become A Better Spouse Today.";
            message = goalPickerButton.getText().toString();
            Log.d(TAG, message);

        }

        descriptionEditText = (EditText) findViewById(R.id.description);

        // Get reference to ConstraintLayout containing delete button
        mConstraintLayout = (ConstraintLayout) findViewById(R.id.bottomConstraintLayout);

        // make the notifConstraintLayout unclickable from the beginning.
        notifConstraintLayout.setVisibility(notifConstraintLayout.GONE);

        // set the toolbar as the action bar for the activity.
        setSupportActionBar(myToolbar);

        // get a reference to the action bar
        ActionBar actionBar = getSupportActionBar();

        // set the action bar title
        actionBar.setTitle("Change Item Activity");

        // Enable the up button.
        actionBar.setDisplayHomeAsUpEnabled(true);

        // set a listener for the switch for when it is checked
        // hid
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.d(TAG, "switch is on!");

                    // do something because the switch is on...
                    mConstraintLayout.animate().translationY(mConstraintLayout.getHeight() +
                            deleteButton.getHeight());
                    notifConstraintLayout.setVisibility(notifConstraintLayout.VISIBLE);
                } else {
                    Log.d(TAG, "switch is off!");

                    // do something because the switch is off..
                    mConstraintLayout.animate().translationY(0);
                    notifConstraintLayout.setVisibility(notifConstraintLayout.GONE);
                }
            }
        });

        datePickerCalendar = new GregorianCalendar();
        timePickerCalendar = new GregorianCalendar();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_item_activity, menu);
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
            //TODO: Make sure the individual notification settings is saved here.
            /*
            * TODO:
            * Get the day, get the time, show the non repeating notification to happen then.
            * */
            startAlarm();



            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * SHOW DATE PICKER DIALOG
     * */
    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(this);
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }

    /**
     * SHOW TIME PICKER DIALOG
     * */
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = TimePickerFragment.newInstance(this);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    /**
     * ON DATE SET
     * */
    @Override
    public void onDateSet(Date date) {
        datePickerCalendar.setTime(date);

        String day;
        String month;

        switch (datePickerCalendar.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                day = "Sunday";
                break;
            case 2:
                day = "Monday";
                break;
            case 3:
                day = "Tuesday";
                break;
            case 4:
                day = "Wednesday";
                break;
            case 5:
                day = "Thursday";
                break;
            case 6:
                day = "Friday";
                break;
            case 7:
                day = "Saturday";
                break;
            default:
                day = "Monday";
        }

        switch (datePickerCalendar.get(Calendar.MONTH)) {
            case 0:
                month = "January";
                break;
            case 1:
                month = "February";
                break;
            case 2:
                month = "March";
                break;
            case 3:
                month = "April";
                break;
            case 4:
                month = "May";
                break;
            case 5:
                month = "June";
                break;
            case 6:
                month = "July";
                break;
            case 7:
                month = "August";
                break;
            case 8:
                month = "September";
                break;
            case 9:
                month = "October";
                break;
            case 10:
                month = "November";
                break;
            case 11:
                month = "December";
                break;
            default:
                month = "January";
        }

        String dateText = day + ", " + month + " " + datePickerCalendar.get(Calendar.DAY_OF_MONTH);
        datePickerEditText.setText(dateText);
    }

    /**
     * ON TIME SET
     * */
    @Override
    public void onTimeSet(Date date) {
        // make a gregorian calendar object, set the time to passed in Date object
        timePickerCalendar.setTime(date);

        // Create a SimpleDateFormat object with format being "2:05" ("h:mm")
        // https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
        SimpleDateFormat localDateFormat = new SimpleDateFormat("h:mm");

        // Create a string of the chosen time from the passed in date, formatted correctly.
        String time = localDateFormat.format(date);

        // Set the text.
        timePickerEditText.setText(time);
    }

    /**
     * TO ADD ITEM ACTIVITY
     * Called from UI
     * */
    public void toAddItemActivity(View view) {
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivity(intent);
    }

    private void startAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, AlertReceiver.class);

        intent.putExtra("title", title);
        intent.putExtra("message", message);

        PendingIntent pendingIntent = PendingIntent
                .getBroadcast(this, 1, intent, 0); // request code must be different for each pending intent. Flags define different behavior for pending intent.

        /*
        TODO: Check for if user picks a time that is before device time, set the notification for the next day
        */

        // third parameter is the interval at which the user wants to be reminded of the notification.
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                timePickerCalendar.getTimeInMillis(),
                pendingIntent);

    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent
                .getBroadcast(this, 1, intent, 0); // request code must be different for each pending intent. Flags define different behavior for pending intent.
        alarmManager.cancel(pendingIntent);
    }
}
