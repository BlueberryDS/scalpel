package com.uwaffle.scalpel.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import com.uwaffle.scalpel.R;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import static com.uwaffle.scalpel.MainActivity.getBus;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    public static class DateRecieved{
        private String dateString;
        private Date date;

        public DateRecieved(String dateString, Date date) {
            this.dateString = dateString;
            this.date = date;
        }

        public String getDateString() {
            return dateString;
        }

        public Date getDate() {
            return date;
        }
    }

    public static DatePickerFragment createInstance(){
        return new DatePickerFragment();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Date c = new Date();
        int year = c.getYear();
        int month = c.getMonth();
        int day = c.getDay();

        // CreateFragment a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy");
        Date date = new Date(year, month, day);

        getBus().post(new DateRecieved(formatter.format(date), date));
    }
}
