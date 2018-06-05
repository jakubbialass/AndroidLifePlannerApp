package com.example.kuba.testrecyclerfragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Kuba on 17.03.2018.
 */

public class NoteReview extends AppCompatActivity {

    private EditText editText;
    private String originalNoteText;
    private ArrayList<Note> mCustomObjects;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_review_view);

        Bundle extras = getIntent().getExtras();

        editText=findViewById(R.id.text_edit_note);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setCursorVisible(true);
            }
        });
        originalNoteText=extras.getString("text");
        editText.setText(originalNoteText);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setCursorVisible(true);
            }
        });
        position=extras.getInt("position");


        EditText editText2 = findViewById(R.id.text_edit_note);
        editText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });





    }


    @Override
    public void onBackPressed()
    {
        if(!originalNoteText.equals(editText.getText().toString())){
            SharedPreferences sharedPreferences = getSharedPreferences("notes file", MODE_PRIVATE);

            Gson gson = new Gson();
            String json = sharedPreferences.getString("notes list", null);
            Type type= new TypeToken<ArrayList<Note>>() {}.getType();
            mCustomObjects = gson.fromJson(json, type);

            Note note = new Note(editText.getText().toString());
            mCustomObjects.remove(position);
            mCustomObjects.add(note);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson2 = new Gson();
            String json2 = gson2.toJson(mCustomObjects);
            editor.putString("notes list", json2);
            editor.apply();
            finish();
            Intent intent = new Intent(NoteReview.this, MainActivity.class);
            startActivity(intent);
        }
        else
        {
            finish();
            Intent intent = new Intent(NoteReview.this, MainActivity.class);
            startActivity(intent);
        }
    }




}
