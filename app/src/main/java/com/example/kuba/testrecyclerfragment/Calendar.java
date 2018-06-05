package com.example.kuba.testrecyclerfragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kuba.testrecyclerfragment.slidingTab.SlidingTabsBasicFragment;

/**
 * Created by Kuba on 01.04.2018.
 */

public class Calendar extends Fragment{

    private CalendarView calendarView;
    private Fragment fragmentDay;
    private FrameLayout frameLayout;
    private RelativeLayout someRelative;
    private ImageView icon;
    private Switch showDayViewSwitch;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_view, container, false);

        showDayViewSwitch= view.findViewById(R.id.show_day_view_switch);
        icon=view.findViewById(R.id.someicon);
        someRelative=view.findViewById(R.id.someRelative);
        someRelative.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            public void onSwipeTop() {
                LinearLayout.LayoutParams swiperParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 100f);
                someRelative.setLayoutParams(swiperParams);
                icon.setImageDrawable(getResources().getDrawable(R.drawable.baseline_keyboard_arrow_down_black_36));
                //Toast.makeText(getActivity(), "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                //Toast.makeText(getActivity(), "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                //Toast.makeText(getActivity(), "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                LinearLayout.LayoutParams swiperParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f);
                someRelative.setLayoutParams(swiperParams);
                icon.setImageDrawable(getResources().getDrawable(R.drawable.baseline_keyboard_arrow_up_black_36));
                //Toast.makeText(getActivity(), "bottom", Toast.LENGTH_SHORT).show();
            }

        });


        fragmentDay= new Day();
        calendarView= view.findViewById(R.id.calendar_view);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                if (showDayViewSwitch.isChecked()) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment currentFragment = getFragmentManager().findFragmentByTag("calendar");
                    fragmentTransaction.hide(currentFragment);
                    fragmentTransaction.setCustomAnimations(android.R.animator.fade_in,
                            android.R.animator.fade_out);

                    Bundle bundle = new Bundle();
                    //tworzymy string klikniętego dnia w formacie DD.MM.YYYY
                    String selectedDay = Integer.toString(i2) + "." + Integer.toString(i1 + 1) + "." + Integer.toString(i);
                    bundle.putString("event_date", selectedDay);
                    //Log.v("PRZEKAZANA DATA", selectedDay);
                    fragmentDay.setArguments(bundle);

                    fragmentTransaction.add(R.id.fragment_layout, fragmentDay, "fragmentDay").addToBackStack("calendar")
                            .commit();
                }
            }

        });


        if (savedInstanceState == null) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            SlidingTabsBasicFragment fragment = new SlidingTabsBasicFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }

        frameLayout=view.findViewById(R.id.sample_content_fragment);




        return view;
    }
}
