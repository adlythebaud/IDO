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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Log.d(TAG, "date was set");
    }

    public DatePickerFragmentListener getDatePickerListener() {
        return datePickerListener;
    }

    public void setDatePickerListener(DatePickerFragmentListener datePickerListener) {
        this.datePickerListener = datePickerListener;
    }

    /**
     * NOTIFY DATE PICKER LISTENER
     * Method to safely call date picker listener
     * */
    protected void notifyDatePickerListener(Date date) {
        if(this.datePickerListener != null) {
            this.datePickerListener.onDateSet(date);
        }
    }

}
