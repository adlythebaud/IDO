package com.mycabbages.teamavatar.ido;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by adlythebaud on 2/5/18.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private final String TAG = "TimePickerFragment";
    private TimePickerFragmentListener timePickerListener;

    /**
     * TIME PICKER FRAGMENT LISTENER INTERFACE.
     * Implement methods to get date back from fragment.
     * */
    public interface TimePickerFragmentListener {
        public void onTimeSet(Date date);
    }

    /**
     * NEW INSTANCE
     * @param listener - activity that will be listening to changes in the fragment.
     * */
    // static factory method to create a fragment instance. Useful for
    // defining a method that properly sets up the fragment.
    public static TimePickerFragment newInstance(TimePickerFragment.TimePickerFragmentListener listener) {
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setTimePickerListener(listener);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    /**
     * ON TIME SET
     * set a notification based on hourOfDay and minute selected by user.
     * */
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // do something with the time selected by the user.
        Calendar c = Calendar.getInstance();
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), hourOfDay, minute);

        Date date = c.getTime();

        Log.d(TAG, c.getTime().toString());
        Log.d(TAG, date.toString());
//        Log.d(TAG, hourOfDay + ":" + minute);

        // Here we call the listener and pass the date back to it.
        notifyTimePickerListener(date);
    }

    /**
     * GET TIME PICKER LISTENER
     * */
    public TimePickerFragmentListener getTimePickerListener() {
        return timePickerListener;
    }

    /**
     * SET TIME PICKER LISTENER
     * */
    public void setTimePickerListener(TimePickerFragmentListener timePickerListener) {
        this.timePickerListener = timePickerListener;
    }

    /**
     * NOTIFY TIME PICKER LISTENER
     * @param date - passed in date from fragment's implementation of onTimeSet.
     * Calls the listener's implementation of onTimeSet with passed in parameter.
     * */
    protected void notifyTimePickerListener(Date date) {
        if(this.timePickerListener != null) {
            this.timePickerListener.onTimeSet(date);
        }
    }


}
