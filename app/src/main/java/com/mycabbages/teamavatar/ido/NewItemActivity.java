package com.mycabbages.teamavatar.ido;

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
import android.widget.CompoundButton;
import android.widget.Switch;

public class NewItemActivity extends AppCompatActivity {

    private final String TAG = "NewItemActivity";
    private ConstraintLayout mConstraintLayout;
    private Switch mSwitch;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        // get references to everything we need in the UI.
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        mSwitch = (Switch) findViewById(R.id.notificationSettingsSwitch);
        mConstraintLayout = (ConstraintLayout) findViewById(R.id.bottomConstraintLayout);

        // set the toolbar as the action bar for the activity.
        setSupportActionBar(myToolbar);

        // get a reference to the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("New Item Activity");

        // Enable the up button.
        actionBar.setDisplayHomeAsUpEnabled(true);

        // set a listener for the switch for when it is checked
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.d(TAG, "switch is on");
                    // do something because the switch is on...
                    mConstraintLayout.animate().translationY(mConstraintLayout.getHeight() - 60);
                } else {
                    Log.d(TAG, "switch is off");
                    // do something because the switch is off..
                    mConstraintLayout.animate().translationY(0);
                }
            }
        });





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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");



    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

}
