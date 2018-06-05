package com.example.kuba.testrecyclerfragment;
/**
 * *****************************************
 * *****************************************
 * *****************************************
 * jest to okno z widokiem na wszystki notatki
 * w tym oknie znajduje sie recycler view z listą notatek
 * ---------------------------------@@@@@@@@@@ przytrzymując palcem na notatce usuwamy notatkę @@@@@@@@@@@@
 * *****************************************
 * *****************************************
 * *****************************************
 */

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;

import java.util.Stack;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ListFragment fragment;
    private Fragment fragmentCalendar, fragmentCoaching, fragmentInfo, fragmentDay;

    private final String filename="files_notes";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
/*
        calendarView= findViewById(R.id.calendar_view);
        calendarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
*/

        fragment = new ListFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_layout, fragment);
        fragmentTransaction.commit();

        fragmentDay = new Day();
        fragmentCalendar = new Calendar();
        fragmentCoaching = new Coaching();
        fragmentInfo = new Info();
/*
        changeFragment= findViewById(R.id.changeFragment);
        changeFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_layout, fragmentCalendar);
                fragmentTransaction.commit();
            }
        });
*/
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Calendar/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager mFragmentManager = getFragmentManager();
        mFragmentManager.popBackStack();   // usuwa wszystkie powroty do fragmentów
        if (id == R.id.nav_camera) {        // K A L E N D A R Z
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            /*if(getFragmentManager().findFragmentByTag("fragmentDay")!=null) {
                fragmentManager.popBackStack();
            }*/
            fragmentTransaction.replace(R.id.fragment_layout, fragmentCalendar, "calendar");
            fragmentTransaction.commit();
            // Handle the camera action
        } else if (id == R.id.nav_notes) {      // N O T A T K I
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_layout, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_slideshow) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_layout, fragmentDay);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_coaching) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_layout, fragmentCoaching);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_info) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_layout, fragmentInfo);
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
       drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
