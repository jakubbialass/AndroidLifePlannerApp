package com.example.kuba.testrecyclerfragment;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kuba on 23.04.2018.
 */

public class Day extends Fragment {

    private FloatingActionButton addNewDayEventButton;
    private String selectedDayDate; //DD.MM.YYYY
    private ArrayList<DayEvent> dayEventArrayList;
    private List<LinearLayout> linearLayouts;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.day_view, container, false);

        linearLayouts = new ArrayList<>(9);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            selectedDayDate = bundle.getString("event_date");
        }

        addNewDayEventButton= view.findViewById(R.id.add_new_day_event);
        addNewDayEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("event_date", selectedDayDate);
                Intent intent = new Intent(getActivity(), NewDayEvent.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }
        });
        updateEvents(view);

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        Fragment fragmentDay= new Day();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putString("event_date", selectedDayDate);
        fragmentDay.setArguments(bundle);
        Fragment currentFragment = getFragmentManager().findFragmentByTag("calendar");
        fragmentTransaction.hide(currentFragment);
        fragmentTransaction.add(R.id.fragment_layout, fragmentDay, "fragmentDay").addToBackStack("calendar")
                .commit();


    }



    private void updateEvents(View view) {

        int z=6;
        for(int i=0; i<9; i++){
            String layoutId="ll_day_events_"+Integer.toString(z);
            int resID = getResources().getIdentifier(layoutId, "id", getActivity().getPackageName());
            LinearLayout linearLayout= getActivity().findViewById(resID);
            linearLayouts.add(linearLayout);
            z=z+2;
        }



        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("event-day-"+selectedDayDate, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("event-day-"+selectedDayDate, null);
        Type type= new TypeToken<ArrayList<DayEvent>>() {}.getType();
        dayEventArrayList = gson.fromJson(json, type);

        if(dayEventArrayList!=null) {
            for (DayEvent dayEvent : dayEventArrayList) {

                if (dayEvent.getHour() < 7 && dayEvent.getHour() >= 3) {
                    LinearLayout linearLayout = view.findViewById(R.id.ll_day_events_6);
                    showEvent(linearLayout, view, dayEvent);
                } else if (dayEvent.getHour() < 9 && dayEvent.getHour() >= 7) {
                    LinearLayout linearLayout = view.findViewById(R.id.ll_day_events_8);
                    showEvent(linearLayout, view, dayEvent);
                } else if (dayEvent.getHour() < 11 && dayEvent.getHour() >= 9) {
                    LinearLayout linearLayout = view.findViewById(R.id.ll_day_events_10);
                    showEvent(linearLayout, view, dayEvent);
                } else if (dayEvent.getHour() < 13 && dayEvent.getHour() >= 11) {
                    LinearLayout linearLayout = view.findViewById(R.id.ll_day_events_12);
                    showEvent(linearLayout, view, dayEvent);
                } else if (dayEvent.getHour() < 15 && dayEvent.getHour() >= 13) {
                    LinearLayout linearLayout = view.findViewById(R.id.ll_day_events_14);
                    showEvent(linearLayout, view, dayEvent);
                } else if (dayEvent.getHour() < 17 && dayEvent.getHour() >= 15) {
                    LinearLayout linearLayout = view.findViewById(R.id.ll_day_events_16);
                    showEvent(linearLayout, view, dayEvent);
                } else if (dayEvent.getHour() < 19 && dayEvent.getHour() >= 17) {
                    LinearLayout linearLayout = view.findViewById(R.id.ll_day_events_18);
                    showEvent(linearLayout, view, dayEvent);
                } else if (dayEvent.getHour() < 21 && dayEvent.getHour() >= 19) {
                    LinearLayout linearLayout = view.findViewById(R.id.ll_day_events_20);
                    showEvent(linearLayout, view, dayEvent);
                } else if (dayEvent.getHour() < 3 || dayEvent.getHour() >= 21) {
                    LinearLayout linearLayout = view.findViewById(R.id.ll_day_events_22);
                    showEvent(linearLayout, view, dayEvent);
                }
            }
        }



    }




    public void showEvent(LinearLayout linearLayout, View view, final DayEvent dayEvent)
    {
        //ustawienia linear layoutu przechowujacego 2x textviews (czas i tytul)
        LinearLayout linearLayout2 = new LinearLayout(view.getContext());
        LinearLayout.LayoutParams linearLayoutParams2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
        linearLayout2.setOrientation(LinearLayout.VERTICAL);
        linearLayoutParams2.setMargins(3, 3, 3, 3);
        linearLayout2.setBackgroundColor(Color.parseColor("#0d47a1"));
        linearLayout2.setLayoutParams(linearLayoutParams2);
        // ustawienia text view czasu
        LinearLayout.LayoutParams textViewParamsForTime = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView textViewForTime = new TextView(view.getContext());
        textViewForTime.setText(Integer.toString(dayEvent.getHour())
        + ":" + Integer.toString(dayEvent.getMinute()));
        textViewForTime.setTextSize(10);
        textViewForTime.setGravity(Gravity.LEFT);
        textViewForTime.setTextColor(Color.parseColor("#FAFAFA"));
        textViewForTime.setPadding(pdToPx(3),pdToPx(3), 0, 0);
        // ustawienia text view tytulu
        LinearLayout.LayoutParams textViewParamsForTitle = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        TextView textViewForTitle = new TextView(view.getContext());
        textViewForTitle.setText(dayEvent.getTitle());
        textViewForTitle.setMaxLines(1);
        textViewForTitle.setGravity(Gravity.CENTER);
        textViewForTitle.setTextColor(Color.parseColor("#FAFAFA"));
        textViewForTitle.setTextSize(16);
        textViewForTitle.setPadding(0, 0, 0, pdToPx(12));

        linearLayout2.addView(textViewForTime, textViewParamsForTime);
        linearLayout2.addView(textViewForTitle, textViewParamsForTitle);


        linearLayout2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    Fragment dayEventZoomFragment = new DayEventZoomFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment currentFragment = getFragmentManager().findFragmentByTag("fragmentDay");
                    fragmentTransaction.hide(currentFragment);
                    fragmentTransaction.setCustomAnimations(android.R.animator.fade_in,
                            android.R.animator.fade_out);

                    Bundle bundle = new Bundle();
                    bundle.putString("event_title", dayEvent.getTitle());
                    bundle.putInt("event_hour", dayEvent.getHour());
                    dayEventZoomFragment.setArguments(bundle);

                    fragmentTransaction.add(R.id.fragment_layout, dayEventZoomFragment, "dayEventZoomFragment").addToBackStack("fragmentDay")
                            .commit();
                    // Do what you want
                    return true;
                }

                if(event.getAction() == MotionEvent.ACTION_UP){
                    getActivity().dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
                    getActivity().dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
                    // Do what you want
                    return true;
                }/*
                if(event.getAction()!=-35) {
                    Log.v("UWAGA", Integer.toString(event.getAction()));
                    return true;
                }*/
                return false;
            }
        });

        // dodajemy nasz linear layout z tytulem i czasem do glownego dla danego przedzialu czasu horyzontalnego linear layoutu
        linearLayout.addView(linearLayout2);


/*
        TextView textViewForTitle = new TextView(view.getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
        layoutParams.setMargins(3,3,3,3);
        textViewForTitle.setText(dayEvent.getTitle());
        textViewForTitle.setMaxLines(1);
        textViewForTitle.setGravity(Gravity.CENTER);
        textViewForTitle.setTextColor(Color.parseColor("#FAFAFA"));
        textViewForTitle.setBackgroundColor(Color.parseColor("#0d47a1"));
        linearLayout.addView(textViewForTitle, layoutParams);
*/
    }

    private int pdToPx(int padding_in_dp){
        final float scale = getResources().getDisplayMetrics().density;
        int padding_in_px = (int) (padding_in_dp * scale + 0.5f);
        return padding_in_px;
    }




}
