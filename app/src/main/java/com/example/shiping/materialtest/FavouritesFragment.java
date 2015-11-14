package com.example.shiping.materialtest;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouritesFragment extends android.support.v4.app.Fragment {

    ListView locationListView;
    String[] getLocations = ListOfSelectedPlacesAndModes.interestedLocations;


    public FavouritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getLocations.length == 0) {
            ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(getActivity(),
                    R.layout.location_view,
                    R.id.location_button, getLocations);
            locationListView = (ListView) getActivity().findViewById(R.id.location_list);
            locationListView.setAdapter(locationAdapter);
        } else {
            String[] locations = Arrays.copyOf(getLocations, getLocations.length - 1);
            ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(getActivity(),
                    R.layout.location_view,
                    R.id.location_button, locations);
            locationListView = (ListView) getActivity().findViewById(R.id.location_list);
            locationListView.setAdapter(locationAdapter);
        }

    }
}

