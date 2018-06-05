package com.example.kuba.testrecyclerfragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Kuba on 12.03.2018.
 */

public class ListFragment extends Fragment {

    private ArrayList<Note> noteArrayList;
    private ListAdapter listAdapter;
    private FloatingActionButton addNewNoteButton;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        loadArrayList();

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.listRecyclerView);
        listAdapter = new ListAdapter(noteArrayList);
        listAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(listAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        addNewNoteButton = view.findViewById(R.id.add_new_note);
        addNewNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewNote.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }




    public void loadArrayList()
    {

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("notes file", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("notes list", null);
        Type type= new TypeToken<ArrayList<Note>>() {}.getType();
        noteArrayList = gson.fromJson(json, type);

    }


}
