package com.example.shiping.materialtest;


import android.content.Context;
import android.content.res.AssetManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends android.support.v4.app.Fragment {

    private CustomMapFragment mapFragment = new CustomMapFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.map_container, mapFragment).commit();



        Button button = (Button) rootView.findViewById(R.id.searchbt);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                search(v);
            }
        });


        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void search(View view) {
        GoogleMap mMap = mapFragment.getMap();

        //get location from search bar
        EditText location = (EditText) getActivity().findViewById(R.id.searchInput);
        String locstring = location.getText().toString().toLowerCase(); //location in lowercase
        String fuzzylocation = fuzzify(locstring);
        if (!fuzzylocation.equals("")) {
            Log.i("MapsActivity.java", fuzzylocation + " in if block");

            LatLng loc = getCoordinate(fuzzylocation);
            mMap.addMarker(new MarkerOptions().position(loc).title(fuzzylocation).anchor(0.5f,0.5f));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 17f));

        }
        else {
            //location is not a location listed in dict
            Log.i("MapsActivity.java", fuzzylocation+" in else block");
            Toast.makeText(getActivity(), "Unfound location", Toast.LENGTH_SHORT).show();
        }

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
        /////////////////////////////////////////////////


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

    private LatLng getCoordinate(String location) {
        Geocoder myGeo = new Geocoder(getActivity());
        List<Address> matchedList= null;
        Address address = null;
        try {
            matchedList = myGeo.getFromLocationName(location, 1);
            address = matchedList.get(0);
        } catch (IOException e) {
            //geocoder cannot find the location
            Toast.makeText(getActivity(), "Invalid location" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        LatLng loc = new LatLng(address.getLatitude(), address.getLongitude());
        return loc;
    }





}