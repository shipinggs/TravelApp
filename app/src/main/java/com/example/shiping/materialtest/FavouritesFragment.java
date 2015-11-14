package com.example.shiping.materialtest;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;


/**
 * This fragment houses the ListView that displays the buttons of selected locations.
 * The buttons bring the user to a new activity that give them more information about the location.
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

    /** When the activity has been created, set up adapter and list view.**/

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getLocations.length == 0) {
            /**  If no itinerary yet, go ahead and display empty ListView.
             *   Put a notice that says no itinerary has been created. **/

            TextView getToKnow = (TextView) getActivity().findViewById(R.id.getToKnow);
            getToKnow.setText("You have no itinerary yet");
            ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(getActivity(),
                    R.layout.location_view,
                    R.id.location_button, getLocations);
            locationListView = (ListView) getActivity().findViewById(R.id.location_list);
            locationListView.setAdapter(locationAdapter);
        } else {
        /** If itinerary exists, copy new array without last element, which is MBS again.
        /*  We don't want MBS to repeat in ListView. **/

            String[] locations = Arrays.copyOf(getLocations, getLocations.length - 1);
            ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(getActivity(),
                    R.layout.location_view,
                    R.id.location_button, locations);
            locationListView = (ListView) getActivity().findViewById(R.id.location_list);
            locationListView.setAdapter(locationAdapter);
        }

    }
}

