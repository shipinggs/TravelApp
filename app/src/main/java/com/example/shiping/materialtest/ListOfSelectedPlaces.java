package com.example.shiping.materialtest;

import android.app.Application;

/**
 * Created by Admin on 11/6/2015.
 */
public class ListOfSelectedPlaces extends Application {
    private String[] interestedLocations = new String[] {"Singapore Flyer", "Botanic Gardens", "ION Orchard", "Vivo City", "Zoo", "Peranakan Museum", "Marina Bay Sands",
            "Buddha Tooth Relic Temple"};

    public String[] getInterestedLocations() {
        return interestedLocations;
    }

    public void setInterestedLocations(String[] locations) {
        interestedLocations = locations;
    }
}
