package com.example.kuba.testrecyclerfragment;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.*;
import java.util.Calendar;

/**
 * Created by Kuba on 29.04.2018.
 */

public class NewDayEvent extends AppCompatActivity {

    private TimePickerFragment timePicker;
    private Button changeTime, anuluj, gotowe;
    private TextView eventTime;
    private EditText title, txt;
    private String hour, minute;
    private String selectedDayDate; // DD.MM.YYYY
    private String selectedDayDate2; // DD/MM/YYYY
    private ArrayList<DayEvent> dayEventArrayList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_day_event_view);

        selectedDayDate = getIntent().getStringExtra("event_date");
        generateSelectedDayDate2();

        eventTime= findViewById(R.id.event_time);
        eventTime.setText(Integer.toString(java.util.Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) +":" + Integer.toString(Calendar.getInstance().get(Calendar.MINUTE)));
        updateTime();

        changeTime = findViewById(R.id.change_time);
        changeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "timePicker");
            }}
        );

        anuluj = findViewById(R.id.anuluj_button);
        gotowe = findViewById(R.id.gotowe_button);
        title = findViewById(R.id.day_event_title);
        txt = findViewById(R.id.day_event_text);

        anuluj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        gotowe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewEventToList();
                setResult(1);
                finish();
            }
        });

    }


    public void addNewEventToList()
    {
        updateTime();

        //######### TWORZENIE OBIEKTU KLASY DAYEVENT
        DayEvent dayEvent = new DayEvent(title.getText().toString(), txt.getText().toString(), Integer.parseInt(hour), Integer.parseInt(minute));


        //######### ZAPISYWANIE EVENTU DO PLIKU #########
        SharedPreferences sharedPreferences = getSharedPreferences("event-day-"+selectedDayDate, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("event-day-"+selectedDayDate, null);
        Type type= new TypeToken<ArrayList<DayEvent>>() {}.getType();
        dayEventArrayList = gson.fromJson(json, type);
        if(dayEventArrayList==null)
            dayEventArrayList=new ArrayList<>();
        dayEventArrayList.add(dayEvent);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson2 = new Gson();
        String json2 = gson2.toJson(dayEventArrayList);
        editor.putString("event-day-"+selectedDayDate, json2);
        editor.apply();
        //###############################################

        //TEST
        Log.v("tytul notatki ", dayEventArrayList.get(0).getTitle());
    }


    private void updateTime(){
        String[] arr = eventTime.getText().toString().split(":");
        hour = arr[0];
        minute = arr[1];
    }

    private void generateSelectedDayDate2(){
        String[] arr = selectedDayDate.split("\\.");
        selectedDayDate2= arr[0] + "/" + arr[1] + "/" + arr[2];
        Log.v("selectedDayDate2", selectedDayDate2);
    }

}



