package com.example.kuba.testrecyclerfragment;


import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Kuba on 01.05.2018.
 */

public class DayEventZoomFragment extends Fragment {

    private  GestureDetector gestureDetector;

    private TextView textView, deleteArea;
    private LinearLayout linearLayoutDelete;
    private int hour;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.day_event_zoom, container, false);



        linearLayoutDelete= view.findViewById(R.id.linear_layout_delete);




        textView = view.findViewById(R.id.title_zoom);
        deleteArea = view.findViewById(R.id.delete_area);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            textView.setText(bundle.getString("event_title"));
            hour=bundle.getInt("event_hour");
        }

        setDeleteAreaPosition();









        linearLayoutDelete.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                event = MotionEvent.obtainNoHistory(event);
                if(event.getAction()>=0) {
                    Log.v("UWAGA ZOOM", Integer.toString(event.getAction()));
                    return true;
                }
                if(event.getAction() == MotionEvent.ACTION_HOVER_ENTER){
                    deleteArea.setAlpha(1.0f);
                    return true;
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    deleteArea.setAlpha(0.5f);
                    Log.v("UWAGA ZOOM", Integer.toString(event.getAction()));
                    return true;
                }

                return false;
            }
        });




        return view;
    }




    private void setDeleteAreaPosition(){
        RelativeLayout.LayoutParams layoutParamsForDeleteArea = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        if(hour>3 && hour<19)
            layoutParamsForDeleteArea.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        else
            layoutParamsForDeleteArea.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        linearLayoutDelete.setLayoutParams(layoutParamsForDeleteArea);
    }



}
