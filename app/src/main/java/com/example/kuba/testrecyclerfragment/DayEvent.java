package com.example.kuba.testrecyclerfragment;

import java.util.*;

/**
 * Created by Kuba on 28.04.2018.
 */

public class DayEvent {
    private String text, title;
    private int hour, minute;
    private Date date;



    public DayEvent(String title, String text, int hour, int minute)
    {
        this.title=title;
        this.text=text;
        this.hour=hour;
        this.minute=minute;
    }



    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title=title; }

    public int getHour() { return hour; }
    public void setHour(int hour) { this.hour=hour; }
    public int getMinute() { return minute; }
    public void setMinute(int minute) { this.minute=minute; }

    public Date getDate() { return date; }
    public void setDate() { this.date= java.util.Calendar.getInstance().getTime();}

    //public String dateToString() { return day+"."+month+"."+year; }
}
