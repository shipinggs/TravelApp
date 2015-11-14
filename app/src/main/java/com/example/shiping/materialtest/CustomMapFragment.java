package com.example.shiping.materialtest;

import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * This fragment extends SupportMapFragment. This is where the map is "generated".
 */

public class CustomMapFragment extends com.google.android.gms.maps.SupportMapFragment {

    public CustomMapFragment() {
        // Required empty public constructor
    }


    private final LatLng SINGAPORE = new LatLng(1.2826, 103.8584);

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        GoogleMap mMap = getMap();

        // Initiates starting position to be MBS
        mMap.addMarker(new MarkerOptions().position(SINGAPORE).title("Marina Bay Sands"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SINGAPORE, 15));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12), 2000, null);

    }




}
