package com.example.kuba.testrecyclerfragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Kuba on 12.03.2018.
 */

public class NewNote extends AppCompatActivity {

    private EditText text;
    private ArrayList<Note> noteArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_note_view);
        noteArrayList = new ArrayList<>();

        text= findViewById(R.id.text);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Calendar/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(item.getIcon().getConstantState().equals(getResources().getDrawable(R.drawable.ic_save_white_36dp).getConstantState()))
        {
           // Intent intent = new Intent(NewNote.this, MainActivity.class);
            Note note = new Note(text.getText().toString());
            addNewNoteToList(note);
            Toast.makeText(getApplicationContext(), "Zapisano", Toast.LENGTH_SHORT).show();
            hideKeyboard(this, text);
            Intent intent = new Intent(this, MainActivity.class);
            finish();
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.new_note_menu, menu);
        return true;
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public void addNewNoteToList(Note note)
    {



        SharedPreferences sharedPreferences = getSharedPreferences("notes file", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("notes list", null);
        Type type= new TypeToken<ArrayList<Note>>() {}.getType();
        noteArrayList = gson.fromJson(json, type);
        if(noteArrayList==null)
            noteArrayList=new ArrayList<>();
        noteArrayList.add(note);


        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson2 = new Gson();
        String json2 = gson2.toJson(noteArrayList);
        editor.putString("notes list", json2);
        editor.apply();
    }


    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
