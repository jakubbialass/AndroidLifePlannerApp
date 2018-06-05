package com.example.kuba.testrecyclerfragment;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Kuba on 13.03.2018.
 */

public class Note {

    private String text, day, month, year;
    private Date date;



    public Note(String text)
    {
        this.text=text;
        this.date = Calendar.getInstance().getTime();
        this.day = Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        this.month = Integer.toString(Calendar.getInstance().get(Calendar.MONTH)+1);
        this.year = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
    }



    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() { return date; }

    public void setDate() { this.date= Calendar.getInstance().getTime();}

    public String dateToString() { return day+"."+month+"."+year; }
}
