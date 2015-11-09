package com.example.shiping.materialtest;

import java.util.HashMap;

/**
 * Created by Admin on 11/6/2015.
 */
public class TodoData {
    public HashMap<String, String> toDoList = new HashMap<>(20);
    public HashMap<String, String[]> toBringList = new HashMap<>();

    public TodoData() {
        toDoList.put("Marina Bay Sands", "http://www.yoursingapore.com/see-do-singapore/recreation-leisure/resorts/marina-bay-sands.html");
        toDoList.put("Merlion Park", "http://www.yoursingapore.com/see-do-singapore/recreation-leisure/viewpoints/merlion-park.html");
        toDoList.put("The Buddha Tooth Relic Temple", "http://www.yoursingapore.com/see-do-singapore/culture-heritage/places-of-worship/buddha-tooth-relic-temple-museum.html");


        toBringList.put("Marina Bay Sands", new String[]{"Swimwear to immerse yourself in Infinity Pool",
                "Extra cash/credit cards as things are relatively pricey",
                "Formal Wear and Passport to enter Casino in MBS",
                "Medication"});
        toBringList.put("Merlion Park", new String[] {"Shorts and light coloured t-shirts due to Singapore’s Humid weather",
                "Camera to take breath-taking pictures!",
                "Power Bank as you will be travelling!",
                "Lots of water, Singapore has humid weather!",
                "Deodorant/Face cloth(Freshen up)",
                "Medication"});
        toBringList.put("The Buddha Tooth Relic Temple", new String[] {"Lots of water, Singapore has humid weather!",
                "Camera to retain your precious moments",
                "Power Bank as you will be travelling!",
                "Shorts and light coloured t-shirts due to Singapore’s Humid weather",
                "Deodorant/Face cloth(Freshen up)",
                "Medication"});
    }
}