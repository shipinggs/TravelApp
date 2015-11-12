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
        if (id == R.id.help) { /** IMPLEMENT HELP ACTIVITY **/
            startActivity(new Intent(this, HelpActivity.class));
            overridePendingTransition(R.anim.animation_enter_right,R.anim.animation_exit_left);
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

//    public void search(View view) {
//        //get location from search bar
//        EditText location = (EditText) findViewById(R.id.editText1);
//        String locstring = location.getText().toString().toLowerCase(); //location in lowercase
//        String fuzzylocation = fuzzify(locstring);
//        if (!fuzzylocation.equals("")) {
//            Log.i("MapsActivity.java", fuzzylocation + " in if block");
//
//            LatLng loc = getCoordinate(fuzzylocation);
//            mMap.addMarker(new MarkerOptions().position(loc).title("Marker in SG").anchor(0.5f,0.5f));
//            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 17f));
//
//        }
//        else {
//            //location is not a location listed in dict
//            Log.i("MapsActivity.java", fuzzylocation+" in else block");
//            Toast.makeText(this, "Unfound location", Toast.LENGTH_SHORT).show();
//        }
//
//        //hide soft keyboard
//        InputMethodManager inputManager = (InputMethodManager) getSystemService
//                (Context.INPUT_METHOD_SERVICE);
//        inputManager.hideSoftInputFromWindow(
//                this.getCurrentFocus().getWindowToken(), 0);
//    }
//
//    private LatLng getCoordinate(String location) {
//        Geocoder myGeo = new Geocoder(this);
//        List<Address> matchedList= null;
//        Address address = null;
//        try {
//            matchedList = myGeo.getFromLocationName(location, 1);
//            address = matchedList.get(0);
//        } catch (IOException e) {
//            //geocoder cannot find the location
//            Toast.makeText(this, "Invalid location" + e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//
//        LatLng loc = new LatLng(address.getLatitude(), address.getLongitude());
//        return loc;
//    }
//
//    /**
//     * Given a string of location, check if the string matches any of the location stored in dictionary
//     *
//     * @param location string of location keyed in by the user
//     * @return the location name that matches the input, or null if there is none
//     */
//    private String fuzzify(String location) {
//        ArrayList<String> compareTo = new ArrayList<String>();
//        AssetManager am = getAssets();
//        try {
//            InputStream assetIn = am.open("dict");
//            BufferedReader reader = new BufferedReader(new InputStreamReader(assetIn));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                compareTo.add(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        for (String e : compareTo) {
//            if (compare(location, e) < 3) {
//                Log.i("MapsActivity.java", "edit distance is less than 3");
//                return e;
//            }
//        }
//
//        return "";
//    }
//
//    private int compare(String s1, String s2) { //s1 is input, s2 is loc name in dictionary
//        //initial conditions
//        int ls1 = s1.length();
//        int ls2 = s2.length();
//        int[][] distancematrix = new int[ls1+1][ls2+1];
//        for (int i=0; i <= ls1; i++) {
//            distancematrix[i][0] = i;
//        }
//        for (int j=0; j <= ls2; j++) {
//            distancematrix[0][j] = j;
//        }
//        /////////////////////////////////////////////////
//
//
//        for (int i = 1; i <=ls1; i++) {
//            for (int j = 1; j <=ls2; j++) {
//                if (s1.charAt(i-1) == s2.charAt(j-1)) { //when the last char x and y are the same
//                    distancematrix[i][j] = distancematrix[i-1][j-1];
//                }
//                else {
//                    int insert = distancematrix[i][j-1]+1;
//                    int delete = distancematrix[i-1][j]+1;
//                    int replace = distancematrix[i-1][j-1]+1;
//                    distancematrix[i][j] = Math.min(Math.min(insert, delete), replace);
//                }
//            }
//        }
//        return distancematrix[ls1][ls2];
//    }


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
                fragment = new TestFragment();
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

}
