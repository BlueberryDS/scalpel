package com.uwaffle.scalpel.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import com.uwaffle.scalpel.MainActivity;
import com.uwaffle.scalpel.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.uwaffle.scalpel.MainActivity.getBus;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    public static class TimeRecieved{
        public String timeString;
        public Date date;

        public TimeRecieved(String timeString, Date date) {
            this.timeString = timeString;
            this.date = date;
        }

        public String getTimeString() {
            return timeString;
        }

        public Date getDate() {
            return date;
        }
    }

    public static TimePickerFragment createInstance(){
        return new TimePickerFragment();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Date c = new Date();
        int hour = c.getHours();
        int minute = c.getMinutes();

        // CreateFragment a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        SimpleDateFormat formatter = new SimpleDateFormat("hh : mm a");
        Date date = new Date();
        date.setHours(hourOfDay);
        date.setMinutes(minute);

        getBus().post(new TimeRecieved(formatter.format(date), date));
    }
}
