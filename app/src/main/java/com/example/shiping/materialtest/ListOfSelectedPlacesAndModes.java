package com.example.shiping.materialtest;

import android.app.Application;

/**
 * Created by Admin on 11/6/2015.
 */
public class ListOfSelectedPlacesAndModes extends Application {

    public static String[] interestedLocations = new String[] {};
    public static String[] modeOfTransport = new String[] {};

    public static String totalCost;
    public static String totalTime;

    public static int rememberBoole;
    public static int compareBoole;

    public static String getTotalCost() {
        return totalCost;
    }

    public static void setTotalCost(String totalCost) {
        ListOfSelectedPlacesAndModes.totalCost = totalCost;
    }

    public static String getTotalTime() {
        return totalTime;
    }

    public static void setTotalTime(String totalTime) {
        ListOfSelectedPlacesAndModes.totalTime = totalTime;
    }

    public static String[] getModeOfTransport() {
        return modeOfTransport;
    }

    public static void setModeOfTransport(String[] modeOfTransport) {
        ListOfSelectedPlacesAndModes.modeOfTransport = modeOfTransport;
    }

    public static String[] getInterestedLocations() {
        return interestedLocations;
    }

    public static void setInterestedLocations(String[] locations) {
        interestedLocations = locations;
    }
}
