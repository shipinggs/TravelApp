package com.example.shiping.materialtest;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
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
        mMap.addMarker(new MarkerOptions().position(SINGAPORE).title("Marina Bay Sands"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SINGAPORE, 15));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12), 2000, null);

    }




}
