package com.example.shiping.materialtest;

import java.util.HashMap;

/**
 * Created by Admin on 11/6/2015.
 */
public class TodoData {
    public HashMap<String, String> toDoList = new HashMap<>();
    public HashMap<String, String[]> toBringList = new HashMap<>();

    public TodoData() {
        toDoList.put("Marina Bay Sands", "http://www.yoursingapore.com/see-do-singapore/recreation-leisure/resorts/marina-bay-sands.html");
        toDoList.put("Singapore Flyer", "http://www.yoursingapore.com/see-do-singapore/recreation-leisure/viewpoints/singapore-flyer.html");
        toDoList.put("Buddha Tooth Relic Temple", "http://www.yoursingapore.com/see-do-singapore/culture-heritage/places-of-worship/buddha-tooth-relic-temple-museum.html");
        toDoList.put("Vivo City","http://www.yoursingapore.com/see-do-singapore/places-to-see/harbourfront.html");
        toDoList.put("Resorts World Sentosa","http://www.yoursingapore.com/see-do-singapore/recreation-leisure/resorts/resorts-world-sentosa.html");
        toDoList.put("Zoo","http://www.yoursingapore.com/see-do-singapore/nature-wildlife/fun-with-animals/singapore-zoo.html");
        toDoList.put("Botanic Gardens","http://www.yoursingapore.com/see-do-singapore/nature-wildlife/parks-gardens/singapore-botanic-gardens.html");
        toDoList.put("Peranakan Museum", "http://www.yoursingapore.com/see-do-singapore/culture-heritage/heritage-discovery/peranakan-museum.html");
        toDoList.put("ION Orchard","http://www.yoursingapore.com/see-do-singapore/places-to-see/orchard.html");



        toBringList.put("Marina Bay Sands", new String[]{});
        toBringList.put("Singapore Flyer", new String[] {"Lots of water as Singapore has humid weather!",
                "Shorts and light coloured t-shirts due to Humid weather",
                "Camera to retain your precious moments",
                "Power Bank as you will be travelling!",
                "Deodorant/Face cloth(Freshen up)",
                "Medication",
                "Extra cash/credit cards as things are relatively pricey"});
        toBringList.put("Buddha Tooth Relic Temple", new String[] {"Lots of water as Singapore has humid weather!",
                "Shorts and light coloured t-shirts due to Humid weather",
                "Camera to retain your precious moments",
                "Power Bank as you will be travelling!",
                "Ensure lightweight baggage and comfortable footgear(Lots of walking)",
                "Deodorant/Face cloth(Freshen up)",
                "Medication"});
        toBringList.put("Vivo City", new String[] {"Lots of water as Singapore has humid weather!",
                "Extra cash/credit cards as things are relatively pricey",
                "Camera to retain your precious moments",
                "Power Bank as you will be travelling!",
                "Shorts and light coloured t-shirts due to Humid weather",
                "Deodorant/Face cloth(Freshen up)",
                "Medication"});
        toBringList.put("Resorts World Sentosa", new String[] {"Lots of water as Singapore has humid weather!",
                "Camera to retain your precious moments",
                "Power Bank as you will be travelling!",
                "Shorts and light coloured t-shirts due to Humid weather",
                "Deodorant/Face cloth(Freshen up)",
                "Medication",
                "Swimwear"});
        toBringList.put("Zoo", new String[] {"Lots of water as Singapore has humid weather!",
                "Camera to retain your precious moments",
                "Power Bank as you will be travelling!",
                "Shorts and light coloured t-shirts due to Humid weather",
                "Deodorant/Face cloth(Freshen up)",
                "Medication",
                "Mosquito Repellent",
                "Change of clothes",
                "Umbrella(Tropical Downpours especially in June-Nov)",
                "Ensure lightweight baggage and comfortable footgear(Lots of walking)"});
        toBringList.put("Botanic Gardens", new String[] {"Lots of water as Singapore has humid weather!",
                "Camera to retain your precious moments",
                "Power Bank as you will be travelling!",
                "Shorts and light coloured t-shirts due to Humid weather",
                "Deodorant/Face cloth(Freshen up)",
                "Medication",
                "Mosquito Repellent","Change of clothes",
                "Umbrella(Tropical Downpours especially in June-Nov)",
                "Ensure lightweight baggage and comfortable footgear(Lots of walking)",
                "Baby powder to keep yourself sweat free",
                "Picnic mat for family gathering",
                "Sandwiches!",
                "Sunblock"});
        toBringList.put("Peranakan Museum", new String[] {"Lots of water as Singapore has humid weather!",
                "Camera to retain your precious moments",
                "Power Bank as you will be travelling!",
                "Shorts and light coloured t-shirts due to Humid weather",
                "Deodorant/Face cloth(Freshen up)",
                "Ensure lightweight baggage and comfortable footgear(Lots of walking)",
                "Medication"});
        toBringList.put("ION Orchard", new String[] {"Lots of water as Singapore has humid weather!",
                "Extra cash/credit cards as things are relatively pricey",
                "Power Bank as you will be travelling!",
                "Shorts and light coloured t-shirts due to Humid weather",
                "Deodorant/Face cloth(Freshen up)",
                "Medication"});    }
}