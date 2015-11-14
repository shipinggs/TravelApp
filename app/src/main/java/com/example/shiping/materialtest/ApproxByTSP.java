package com.example.shiping.materialtest;

import com.example.shiping.materialtest.db.MapData;

import java.util.ArrayList;
import java.util.HashMap;


public class ApproxByTSP {

    public static ArrayList<ArrayList<String>> generateRouteTSP(String[] placeList) {
        HashMap<String, double[]> data = MapData.createData();
        HashMap<String, Character> codeNames = new HashMap<String, Character>();
        codeNames.put("Marina Bay Sands", 'a');
        codeNames.put("Singapore Flyer", 'b');
        codeNames.put("Vivo City", 'c');
        codeNames.put("Resorts World Sentosa", 'd');
        codeNames.put("Buddha Tooth Relic Temple", 'e');
        codeNames.put("Zoo", 'f');
        codeNames.put("Botanic Gardens", 'g');
        codeNames.put("Peranakan Museum", 'h');
        codeNames.put("ION Orchard", 'i');


        ArrayList<String> route = new ArrayList<String>();
        String places = "";
        for (String temp : placeList) {
            places += String.valueOf(codeNames.get(temp));
        }

        int pointer = 0;
        int placeNumber = places.length();
        String minSubroute = "";

        for (int j = 0; j < placeNumber; j++) {
            int minTime = Integer.MAX_VALUE;
            String temp;
            for (int i = 0; i < places.length(); i++) {
                if (j == 0) {
                    temp = "a" + String.valueOf(places.charAt(i));
                } else {
                    temp = String.valueOf(route.get(j - 1).charAt(1)) + String.valueOf(places.charAt(i));
                }
                int testTime = (int) data.get(temp)[5];
                if (testTime < minTime) {
                    minTime = testTime;
                    minSubroute = temp;
                    pointer = i;
                }
            }
            route.add(minSubroute);
            places = places.substring(0, pointer) + places.substring(pointer + 1);
        }
        route.add(String.valueOf(route.get(placeNumber - 1).charAt(1)) + "a");
        ArrayList<ArrayList<String>> output = new ArrayList<ArrayList<String>>();
        output.add(route);

        return output;
    }
}
