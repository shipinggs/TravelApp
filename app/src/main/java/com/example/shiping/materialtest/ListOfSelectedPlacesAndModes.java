package com.example.shiping.materialtest;

import android.app.Application;


/** This is the global variables class. It stores the 2 important String[]s from the Traveling Salesman
 * Problem algorithms from Function 1. It is static so that the other functions can reference them
 * without having to create an instance of them. **/


public class ListOfSelectedPlacesAndModes extends Application {

    public static String[] interestedLocations = new String[] {};
    public static String[] modeOfTransport = new String[] {};

    public static String totalCost;
    public static String totalTime;

    public static int rememberBoole = 0;
    public static int compareBoole = 0;

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
