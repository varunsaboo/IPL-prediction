package com.example.saboo.iplpredict;

/**
 * Created by saboo on 18-04-2015.
 */
import com.example.saboo.iplpredict.util.*;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import java.util.ArrayList;


public class PredictingActivity extends ActionBarActivity {


        // Declaring Your View and Variables

        Toolbar toolbar;
        ViewPager pager;
        ViewPagerAdapter adapter;
        SlidingTabLayout tabs;
        int Numboftabs =2;
        String Titles[]=new String[Numboftabs];


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.predict);
            Titles = getIntent().getExtras().getStringArray("teams");

            // Creating The Toolbar and setting it as the Toolbar for the activity

            toolbar = (Toolbar) findViewById(R.id.tool_bar);
            //toolbar.inflateMenu(R.menu.actions_toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Select Players");

            // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles for the Tabs and Number Of Tabs.
            adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);
            toolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Handle menu item click event
                        ArrayList<String> homeplayers = adapter.home.boxAdapter.getBox();
                        int fcounthome = adapter.home.boxAdapter.foreigncount;
                        int totalhome = adapter.home.boxAdapter.num;
                        //Toast.makeText(getApplicationContext(),homeplayers.toString(),Toast.LENGTH_LONG).show();
                        ArrayList<String> awayplayers = adapter.away.boxAdapter.getBox();
                        int fcountaway = adapter.away.boxAdapter.foreigncount;
                        int totalaway = adapter.away.boxAdapter.num;

                        if(fcounthome >4 || fcountaway>4) {
                            String s=fcounthome+"";
                            Log.e("Foreign player home",s);
                            s="";
                             s=fcountaway+"";
                            Log.e("Foreign player away",s);
                            Toast.makeText(getApplicationContext(), "Only 4 foreign players allowed!", Toast.LENGTH_LONG).show();
                        }
                        else if(totalhome!=11 || totalaway!=11)
                            Toast.makeText(getApplicationContext(),"Choose only 11 players",Toast.LENGTH_LONG).show();
                        else {
                        Intent i = new Intent(PredictingActivity.this, TossActivity.class);
                        i.putExtra("team1", homeplayers);
                        i.putExtra("team2", awayplayers);
                        i.putExtra("teamnames", Titles);
                        startActivity(i);
                    }
                        return true;
                    }
                });
            // Assigning ViewPager View and setting the adapter
            pager = (ViewPager) findViewById(R.id.pager);
            pager.setAdapter(adapter);

            // Assiging the Sliding Tab Layout View
            tabs = (SlidingTabLayout) findViewById(R.id.tabs);
            tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

            // Setting Custom Color for the Scroll bar indicator of the Tab View
            tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
                @Override
                public int getIndicatorColor(int position) {
                    return getResources().getColor(R.color.tabsScrollColor);
                }
            });

            // Setting the ViewPager For the SlidingTabsLayout
            tabs.setViewPager(pager);



        }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions_toolbar, menu);
        return true;
    }





}
