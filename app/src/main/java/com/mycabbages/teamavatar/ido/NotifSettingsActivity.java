package com.mycabbages.teamavatar.ido;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class NotifSettingsActivity extends AppCompatActivity
        implements TimePickerFragment.TimePickerFragmentListener {

    private final String TAG = "NotifSettingsActivity";
    public EditText timePickerEditText;
    public Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif_settings);

        getUIElements();

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
}
