package com.example.kuba.testrecyclerfragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Kuba on 12.03.2018.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private ArrayList<Note> mCustomObjects;


    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private TextView mItemText;
        private TextView mItemDate;
        public Context context;

        public ListViewHolder(View itemView, Context context) {
            super(itemView);

            this.context=context;
            mItemText = itemView.findViewById(R.id.itemText);
            mItemDate = itemView.findViewById(R.id.date);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View view)
                {
                    mCustomObjects.remove(getAdapterPosition());
                    removeItemFromList(mCustomObjects, view);
                    Toast.makeText(view.getContext(), "Usunięto notatkę", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }

        public void onClick(View view) {
            Note note =mCustomObjects.get(getAdapterPosition());
            Intent intent = new Intent(view.getContext(), NoteReview.class);
            Bundle extras = new Bundle();
            extras.putString("text", note.getText());
            extras.putInt("position", getAdapterPosition());
            intent.putExtras(extras);
            view.getContext().startActivity(intent);
            ((Activity)view.getContext()).finish();

        }

        public boolean onLongClick(View view) {
            return true;
        }


    }





    @Override
    public int getItemCount() {
        int size=0;
        if(mCustomObjects!=null)
            size=mCustomObjects.size();
        return size;
    }


    public ListAdapter(ArrayList<Note> arrayList) {
        mCustomObjects = arrayList;
    }


    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ListViewHolder(view, view.getContext());
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        if(mCustomObjects!=null) {
            Note note = mCustomObjects.get(position);
            holder.mItemText.setText(note.getText());
            holder.mItemDate.setText(note.dateToString());
        }
    }


    public void removeItemFromList(ArrayList<Note> mCustomObjects, View view){

            SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("notes file", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson2 = new Gson();
            String json2 = gson2.toJson(mCustomObjects);
            editor.putString("notes list", json2);
            editor.apply();
            notifyDataSetChanged();

    }


}
