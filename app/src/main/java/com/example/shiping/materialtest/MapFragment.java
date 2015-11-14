package com.example.shiping.materialtest;


import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.app.Fragment;
import android.os.StrictMode;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends android.support.v4.app.Fragment {

    private CustomMapFragment mapFragment = new CustomMapFragment(); // Creates new instance of CustomMapFragment i.e. Google Maps

    private String locations[];
    private String mode[];


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.map_container, mapFragment).commit();

        Button searchButton = (Button) rootView.findViewById(R.id.searchBtn);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search(v);
            }
        });

        ImageButton pathButton = (ImageButton) rootView.findViewById(R.id.pathFindBtn);
        pathButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                pathFind(v);
            }
        });


        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    /**
     * called when the search button is pressed in the google map fragment.
     * if the location entered in the EditText can be identified in the dict.txt,
     * the location is zoomed into and marked in the google map.
     * Hides the soft keyboard before the method is completed
     * @param view view of the current activity
     */
    public void search(View view) {
        GoogleMap mMap = mapFragment.getMap();

        //get location from search bar
        EditText location = (EditText) getActivity().findViewById(R.id.searchInput);
        String locstring = location.getText().toString().toLowerCase(); //location in lowercase
        String fuzzylocation = fuzzify(locstring);
        if (!fuzzylocation.equals("")) {
            mMap.clear(); //clear map first
            fuzzylocation = "Singapore" + fuzzylocation; // To ensure location found is a S'pore one.
            LatLng loc = getCoordinate(fuzzylocation);
            mMap.addMarker(new MarkerOptions().position(loc).title(fuzzylocation).anchor(0.5f,0.5f));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 17f));
        }
        else {
            //location is not a location listed in dict
            Toast.makeText(getActivity(), "Unfound location", Toast.LENGTH_SHORT).show();
        }

        //hide soft keyboard
        hideSoftKeyboard();
    }

    private void hideSoftKeyboard() {
        //hide soft keyboard
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(
                getActivity().getCurrentFocus().getWindowToken(), 0);
    }

    /**
     * Given a string of location, check if the string matches any of the location stored in dictionary
     *
     * @param location string of location keyed in by the user
     * @return the location name that matches the input, or null if there is none
     */
    private String fuzzify(String location) {
        ArrayList<String> compareTo = new ArrayList<String>();
        AssetManager am = getActivity().getAssets();
        try {
            InputStream assetIn = am.open("dict");
            BufferedReader reader = new BufferedReader(new InputStreamReader(assetIn));
            String line;
            while ((line = reader.readLine()) != null) {
                compareTo.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String e : compareTo) {
            if (compare(location, e) < 3) {
                Log.i("MapsActivity.java", "edit distance is less than 3");
                return e;
            }
        }

        return "";
    }

    /**
     * returns the editDistance value of two strings
     * @param s1
     * @param s2
     * @return
     */
    private int compare(String s1, String s2) { //s1 is input, s2 is loc name in dictionary
        //initial conditions
        int ls1 = s1.length();
        int ls2 = s2.length();
        int[][] distancematrix = new int[ls1+1][ls2+1];
        for (int i=0; i <= ls1; i++) {
            distancematrix[i][0] = i;
        }
        for (int j=0; j <= ls2; j++) {
            distancematrix[0][j] = j;
        }

        for (int i = 1; i <=ls1; i++) {
            for (int j = 1; j <=ls2; j++) {
                if (s1.charAt(i-1) == s2.charAt(j-1)) { //when the last char x and y are the same
                    distancematrix[i][j] = distancematrix[i-1][j-1];
                }
                else {
                    int insert = distancematrix[i][j-1]+1;
                    int delete = distancematrix[i-1][j]+1;
                    int replace = distancematrix[i-1][j-1]+1;
                    distancematrix[i][j] = Math.min(Math.min(insert, delete), replace);
                }
            }
        }
        return distancematrix[ls1][ls2];
    }

    /**
     * gets LatLng value from a string input using Geocoder
     * @param location
     * @return LatLng of the string location
     */
    private LatLng getCoordinate(String location) {
        Geocoder myGeo = new Geocoder(getActivity());
        List<Address> matchedList = null;
        Address address = null;
        LatLng loc = null;
        try {
            matchedList = myGeo.getFromLocationName(location, 1);
            address = matchedList.get(0);
        } catch (IOException e) {
            //geocoder cannot find the location
            Toast.makeText(getActivity(), "Invalid location" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        try {
            loc = new LatLng(address.getLatitude(), address.getLongitude());

        } catch (NullPointerException e) {
            Toast.makeText(getActivity(), "A problem occurred in retrieving location", Toast.LENGTH_SHORT).show();

        }
        return loc;
    }

    /**
     * called when the direction button is pressed in the google map fragment.
     * displays the optimum itinerary found in function 1 in the google map.
     * starting from MBS, the subsequent locations are directed with arrows and colour-coded
     * depending on the modes of transport for each location.
     * MBS is marked with a blue marker to indicate the starting point.
     * @param view view of the activity
     */
    public void pathFind(View view) {

        GoogleMap mMap = mapFragment.getMap();

        locations = ListOfSelectedPlacesAndModes.interestedLocations;
        mode = ListOfSelectedPlacesAndModes.modeOfTransport;

        if (locations.length == 0) {
            Toast.makeText(getActivity(), "You have no itinerary yet", Toast.LENGTH_SHORT).show();
        }

        else {

            for (int i = 0; i < locations.length; i++) {
                if (locations[i].equals("Zoo"))
                    locations[i] = "Singapore Zoo";
                else if (locations[i].equals("Botanic Gardens"))
                    locations[i] = "Singapore Botanic Gardens";

            }
            //clear map first
            mMap.clear();

            for (int i = 1; i < locations.length; i++) {
                PolylineOptions pathOptions = new PolylineOptions();
                LatLng loc1 = getCoordinate(locations[i - 1]);
                LatLng loc2 = getCoordinate(locations[i]);

                if (loc1 != null) {
                    mMap.addMarker(new MarkerOptions().position(loc1));
                    if (mode[i - 1].equals("walk")) {
                        pathOptions.add(loc1, loc2).width(7).color(Color.BLUE).geodesic(true);}
                    else if (mode[i - 1].equals("taxi")) {
                        pathOptions.add(loc1, loc2).width(7).color(Color.GREEN).geodesic(true);}
                    else if (mode[i - 1].equals("public")) {
                        pathOptions.add(loc1, loc2).width(7).color(Color.RED).geodesic(true);
                    }
                    mMap.addPolyline(pathOptions);
                    drawArrowHead(mMap, loc1, loc2);
                }
            }

            LatLng loc = getCoordinate(locations[0]);
            Marker marker = mMap.addMarker(new MarkerOptions().position(loc).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).title("Hotel"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 13f));
            marker.showInfoWindow();
            hideSoftKeyboard();
        }

    }

    /**
     * adds an arrow head to the tip of the polyline indicated by the two endpoints of the polyline
     * passed as arguments
     * @param mMap googleMap instance
     * @param from location A
     * @param to location B
     */
    private void drawArrowHead(GoogleMap mMap, LatLng from, LatLng to){
        // obtain the bearing between the last two points
        double bearing = getBearing(from, to);

        // round it to a multiple of 3 and cast out 120s
        double adjBearing = Math.round(bearing / 3) * 3;
        while (adjBearing >= 120) {
            adjBearing -= 120;
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Get the corresponding triangle marker from Google
        URL url;
        Bitmap image = null;

        try {
            url = new URL("http://www.google.com/intl/en_ALL/mapfiles/dir_" + String.valueOf((int)adjBearing) + ".png");
            try {
                image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (image != null){

            // Anchor is ratio in range [0..1] so value of 0.5 on x and y will center the marker image on the lat/long
            float anchorX = 0.5f;
            float anchorY = 0.5f;

            int offsetX = 0;
            int offsetY = 0;

            // images are 24px x 24px
            // so transformed image will be 48px x 48px

            //315 range -- 22.5 either side of 315
            if (bearing >= 292.5 && bearing < 335.5){
                offsetX = 24;
                offsetY = 24;
            }
            //270 range
            else if (bearing >= 247.5 && bearing < 292.5){
                offsetX = 24;
                offsetY = 12;
            }
            //225 range
            else if (bearing >= 202.5 && bearing < 247.5){
                offsetX = 24;
                offsetY = 0;
            }
            //180 range
            else if (bearing >= 157.5 && bearing < 202.5){
                offsetX = 12;
                offsetY = 0;
            }
            //135 range
            else if (bearing >= 112.5 && bearing < 157.5){
                offsetX = 0;
                offsetY = 0;
            }
            //90 range
            else if (bearing >= 67.5 && bearing < 112.5){
                offsetX = 0;
                offsetY = 12;
            }
            //45 range
            else if (bearing >= 22.5 && bearing < 67.5){
                offsetX = 0;
                offsetY = 24;
            }
            //0 range - 335.5 - 22.5
            else {
                offsetX = 12;
                offsetY = 24;
            }

            Bitmap wideBmp;
            Canvas wideBmpCanvas;
            Rect src, dest;

            // Create larger bitmap 4 times the size of arrow head image
            wideBmp = Bitmap.createBitmap(image.getWidth() * 2, image.getHeight() * 2, image.getConfig());

            wideBmpCanvas = new Canvas(wideBmp);

            src = new Rect(0, 0, image.getWidth(), image.getHeight());
            dest = new Rect(src);
            dest.offset(offsetX, offsetY);

            wideBmpCanvas.drawBitmap(image, src, dest, null);

            mMap.addMarker(new MarkerOptions()
                    .position(to)
                    .icon(BitmapDescriptorFactory.fromBitmap(wideBmp))
                    .anchor(anchorX, anchorY));
        }
    }

    /**
     * obtains the bearing from location A to location B to determine which direction the arrow points
     * to in drawArrowHead
     * @param from, location A
     * @param to, location B
     * @return bearing from A to B
     */
    private double getBearing(LatLng from, LatLng to){
        double degreesPerRadian = 180.0 / Math.PI;
        double lat1 = from.latitude * Math.PI / 180.0;
        double lon1 = from.longitude * Math.PI / 180.0;
        double lat2 = to.latitude * Math.PI / 180.0;
        double lon2 = to.longitude * Math.PI / 180.0;

        // Compute the angle.
        double angle = - Math.atan2( Math.sin( lon1 - lon2 ) * Math.cos( lat2 ), Math.cos( lat1 ) * Math.sin( lat2 ) - Math.sin( lat1 ) * Math.cos( lat2 ) * Math.cos( lon1 - lon2 ) );

        if (angle < 0.0)
            angle += Math.PI * 2.0;

        // And convert result to degrees.
        angle = angle * degreesPerRadian;

        return angle;
    }

}