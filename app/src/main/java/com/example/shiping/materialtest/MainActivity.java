package com.example.shiping.materialtest;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbar;
    private ViewPager mPager;
    private SlidingTabLayout mTabs;

    public static final String LOCATION = "location";

    public static FragmentManager fragmentManager;

    private CustomMapFragment customMapFragment = new CustomMapFragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.animation_enter_left, R.anim.animation_exit_right);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();


        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("TravelSG");
        setSupportActionBar(toolbar);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mTabs.setDistributeEvenly(true);
        mTabs.setCustomTabView(R.layout.custom_tab_view, R.id.tabText);

        mTabs.setBackgroundColor(getResources().getColor(R.color.primaryColor));
        mTabs.setSelectedIndicatorColors(getResources().getColor(R.color.accentColor));

        mTabs.setViewPager(mPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.itinerary) { /** IMPLEMENT HELP ACTIVITY **/
            if (ListOfSelectedPlacesAndModes.interestedLocations.length == 0)
                Toast.makeText(this, "You have no itinerary yet", Toast.LENGTH_SHORT).show();
            else {
                Intent myIntent = new Intent(this, RouteFinder.class).putExtra("best route", ListOfSelectedPlacesAndModes.interestedLocations);
                myIntent.putExtra("transport mode", ListOfSelectedPlacesAndModes.modeOfTransport);
                myIntent.putExtra("total cost", ListOfSelectedPlacesAndModes.totalCost);
                myIntent.putExtra("total time", ListOfSelectedPlacesAndModes.totalTime);
                startActivity(myIntent);
                overridePendingTransition(R.anim.animation_enter_right, R.anim.animation_exit_left);
            }
        }

        if (id == R.id.weatherForecast) { /**IMPLEMENT WEATHER ACTIVITY **/
            startActivity(new Intent(this, WeatherActivity.class));
            overridePendingTransition(R.anim.animation_enter_right, R.anim.animation_exit_left);

        }

        if (id == R.id.thingsToBring) { /**IMPLEMENT PACKING LIST ACTIVITY **/
            startActivity(new Intent(this, ToBringActivity.class));
            overridePendingTransition(R.anim.animation_enter_right, R.anim.animation_exit_left);

        }

        return super.onOptionsItemSelected(item);
    }

    public void onLocationClick(View v) { /** FOR STARTING WEBVIEW INTENT **/
        String location = ((Button) v).getText().toString();
        Intent detailIntent = new Intent(this, WhatToDo.class);
        detailIntent.putExtra(LOCATION, location);
        startActivity(detailIntent);

    }

    class MyPagerAdapter extends FragmentStatePagerAdapter {

        int icons[] = {R.drawable.ic_directions, R.drawable.ic_place, R.drawable.ic_favorite};
        String[] tabText = getResources().getStringArray(R.array.tabs);

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabText = getResources().getStringArray(R.array.tabs);
        }

        @Override
        public Fragment getItem(int position) {
            //dummy fragment
            Fragment fragment = null;

            if (position == 0)
                fragment = new CalcFragment();
            else if (position == 1)
                fragment = new MapFragment();
            else if (position == 2)
                fragment = new FavouritesFragment();

            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            Drawable drawable = getResources().getDrawable(icons[position]);
            drawable.setBounds(0,0,60,60);
            ImageSpan imageSpan = new ImageSpan(drawable);
            SpannableString spannableString = new SpannableString(" ");
            spannableString.setSpan(imageSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            return spannableString;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.slowAlgo:
                if (checked)

                    break;
            case R.id.fastAlgo:
                if (checked)

                    break;
        }
    }


    public void FindRouteActivate(View view) {
        try {
            ListOfSelectedPlacesAndModes.rememberBoole = 1 - ListOfSelectedPlacesAndModes.rememberBoole;
            EditText budgetIn = (EditText) findViewById(R.id.budgetText);

            double budget = Double.parseDouble(budgetIn.getText().toString());

            CheckBox ion = (CheckBox) findViewById(R.id.starION);
            CheckBox flyer = (CheckBox) findViewById(R.id.starFlyer);
            CheckBox sentosa = (CheckBox) findViewById(R.id.starSentosa);
            CheckBox temple = (CheckBox) findViewById(R.id.starTemple);
            CheckBox garden = (CheckBox) findViewById(R.id.starGarden);
            CheckBox museum = (CheckBox) findViewById(R.id.starMuseum);
            CheckBox vivo = (CheckBox) findViewById(R.id.starVivo);
            CheckBox zoo = (CheckBox) findViewById(R.id.starZoo);


            ArrayList<String> placesToVisit = new ArrayList<>();

            if (ion.isChecked()) {
                placesToVisit.add("ION Orchard");
            }
            if (flyer.isChecked()) {
                placesToVisit.add("Singapore Flyer");
            }
            if (sentosa.isChecked()) {
                placesToVisit.add("Resorts World Sentosa");
            }
            if (temple.isChecked()) {
                placesToVisit.add("Buddha Tooth Relic Temple");
            }
            if (museum.isChecked()) {
                placesToVisit.add("Peranakan Museum");
            }
            if (garden.isChecked()) {
                placesToVisit.add("Botanic Gardens");
            }
            if (vivo.isChecked()) {
                placesToVisit.add("Vivo City");
            }
            if (zoo.isChecked()) {
                placesToVisit.add("Zoo");
            }

            if (placesToVisit.size() == 0) { throw new IOException(); }

            String[] placesToVisitX = new String[placesToVisit.size()];
            placesToVisitX = placesToVisit.toArray(placesToVisitX);


            RadioButton fastSolver = (RadioButton) findViewById(R.id.fastAlgo);

            String[] result;
            if (!fastSolver.isChecked() && placesToVisit.size() >= 7) {
                Toast.makeText(MainActivity.this, "AINT NOBODY GOT TIEM FOR THAT", Toast.LENGTH_SHORT).show();
            } else {
                if (fastSolver.isChecked()) {
                    result = GetItinerary.calcCostTime((ApproxByTSP.generateRouteTSP(placesToVisitX)), budget);
                } else {
                    ArrayList<String> listOfPlaces = GetItinerary.setPlaces(placesToVisitX);
                    ArrayList<ArrayList<String>> possiblePermutations = GetItinerary.generateRoute(listOfPlaces);
                    result = GetItinerary.calcCostTime(possiblePermutations, budget);
                }

                String[] BestRoute = GetItinerary.getOptimalRoute(result);
                ListOfSelectedPlacesAndModes.setInterestedLocations(BestRoute);

                String[] Transport = GetItinerary.getTransMode(result);
                ListOfSelectedPlacesAndModes.setModeOfTransport(Transport);

                String totalcost = GetItinerary.getCost(result);
                ListOfSelectedPlacesAndModes.setTotalCost(totalcost);

                String totaltime = GetItinerary.getTime(result);
                ListOfSelectedPlacesAndModes.setTotalTime(totaltime);

                Intent myIntent = new Intent(this, RouteFinder.class).putExtra("best route", BestRoute);
                myIntent.putExtra("transport mode", Transport);
                myIntent.putExtra("total cost", totalcost);
                myIntent.putExtra("total time", totaltime);
                startActivity(myIntent);
            }
        } catch (NumberFormatException ex) {
            Toast.makeText(MainActivity.this, "Please enter your budget!", Toast.LENGTH_SHORT).show();
        } catch (IOException ex) {
            Toast.makeText(MainActivity.this, "Please select the places you're visiting!", Toast.LENGTH_SHORT).show();

        }

    }

}
