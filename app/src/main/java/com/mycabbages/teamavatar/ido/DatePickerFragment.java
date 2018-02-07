package com.mycabbages.teamavatar.ido;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by adlythebaud on 2/5/18.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private final String TAG = "DatePickerFragment";
    public String test = "test";
    private DatePickerFragmentListener datePickerListener;

    /**
     * DATE PICKER FRAGMENT LISTENER INTERFACE.
     * Implement methods to get date back from fragment.
     * */
    public interface DatePickerFragmentListener {
        public void onDateSet(Date date);
    }

    /**
     * NEW INSTANCE
     * @param listener - activity that will be listening to changes in the fragment.
     * */
    // static factory method to create a fragment instance. Useful for
    // defining a method that properly sets up the fragment.
    public static DatePickerFragment newInstance(DatePickerFragmentListener listener) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setDatePickerListener(listener);
        return fragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    /**
     * ON DATE SET
     * @param year
     * @param month
     * @param dayOfMonth
     *
     * Parameters are passed in by UI.
     * Method notifies the listener with a date object created from parameters
     * Calls notifyDatePickerListener.
     * */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Log.d(TAG, "date was set");
        Calendar c = Calendar.getInstance();
        c.set(year, month, dayOfMonth);
        Date date = c.getTime();
        notifyDatePickerListener(date);
    }

    /**
     * GET DATE PICKER LISTENER
     * */
    public DatePickerFragmentListener getDatePickerListener() {
        return datePickerListener;
    }

    /**
     * SET DATE PICKER LISTENER
     * */
    public void setDatePickerListener(DatePickerFragmentListener datePickerListener) {
        this.datePickerListener = datePickerListener;
    }

    /**
     * NOTIFY DATE PICKER LISTENER
     * @param date - passed in date from fragment's implementation of onDateSet.
     * Calls the listener's implementation of onDateSet with passed in parameter.
     * */
    protected void notifyDatePickerListener(Date date) {
        if(this.datePickerListener != null) {
            this.datePickerListener.onDateSet(date);
        }
    }

}
