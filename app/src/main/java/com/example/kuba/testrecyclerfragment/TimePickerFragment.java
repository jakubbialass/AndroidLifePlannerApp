package com.example.kuba.testrecyclerfragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Calendar;

/**
 * Created by Kuba on 30.04.2018.
 */

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    private int hour, minute;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final java.util.Calendar c = Calendar.getInstance();
        TextView eventTime= ((NewDayEvent)getActivity()).findViewById(R.id.event_time);
        String[] arr = eventTime.getText().toString().split(":");
        int hour =Integer.parseInt(arr[0]); //c.get(Calendar.HOUR_OF_DAY);
        int minute =Integer.parseInt(arr[1]); //c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this, hour, minute, true);
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        this.hour=hourOfDay;
        this.minute=minute;
        TextView eventTime= ((NewDayEvent)getActivity()).findViewById(R.id.event_time);
        eventTime.setText(Integer.toString(hourOfDay) + ":" + Integer.toString(minute));
    }


    public String getEventTime(){
        String eventTime;
        eventTime= Integer.toString(hour) + ":" + Integer.toString(minute);
        return eventTime;
    }

}