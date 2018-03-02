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

    @Override
    public void onTimeSet(int hourOfDay, int minute) {
        timePickerEditText.setText(hourOfDay + ":" + minute);
    }
}
