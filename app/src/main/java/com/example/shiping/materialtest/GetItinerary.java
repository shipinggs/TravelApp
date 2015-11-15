package com.example.shiping.materialtest;

import com.example.shiping.materialtest.db.MapData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

// exhaustive enumeration
public class GetItinerary {

    /**
     * codify the input list of places into a string of characters
     * generate all the possible permutations of the list of places
     *
     * @param string[] of the list of places the user would like to visit
     * @return ArrayList<String> of all possible permutations of character-coded list of places
     */
    public static ArrayList<String> setPlaces(String[] placeList) {
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

        String places = "";
        for (String temp : placeList) {
            places += String.valueOf(codeNames.get(temp));
        }
        ArrayList<String> output = new ArrayList<String>();
        permutation(output, "a", places, "a"); // permute and append the hotel 'a' at the front and back
        return output;
    }


    /**
     * permute a string
     *
     * @param output array which is first initiailised by the user
     * @param choice of prefix to be appended before each permutation
     * @param input string to be permuted
     * @param choice of suffix to be appended after each permutation
     */
    private static void permutation(ArrayList<String> output, String prefix, String str, String suffix) {
        int n = str.length();
        if (n == 0) {
            output.add(prefix + suffix);
        } else {
            for (int i = 0; i < n; i++)
                permutation(output, prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n), suffix);
        }
    }


    /**
     * generate all the tree edges of all the possible routes
     *
     * @param ArrayList<String> of all possible permutations of character-coded list of places
     * @return ArrayList<ArrayList<String>> with each nested arraylist containing the tree edges of one possible route
     */
    public static ArrayList<ArrayList<String>> generateRoute(ArrayList<String> places) {
        ArrayList<ArrayList<String>> route = new ArrayList<ArrayList<String>>();
        for (String temp : places) {
            ArrayList<String> subRoute = new ArrayList<String>();
            for (int i = 0; i < temp.length() - 1; i++) {
                subRoute.add(String.valueOf(temp.charAt(i)) + String.valueOf(temp.charAt(i + 1)));
            }
            route.add(subRoute);
        }
        return route;
    }


    /**
     * find the optimal route (with the shortest travelling time within a given budget)
     * generate the associated transport modes
     *
     * @param ArrayList<ArrayList<String>> of the tree edges of all possible routes
     * @param input budget by the user
     * @return ArrayList<String> of the optimal route, the corresponding transport modes, total cost and total time
     */
    public static String[] calcCostTime(ArrayList<ArrayList<String>> route, double budget) {
        HashMap<String, double[]> data = MapData.createData();
        ArrayList<String> traceRoute = new ArrayList<String>();
        ArrayList<Integer> finalTime = new ArrayList<Integer>();
        ArrayList<Double> finalCost = new ArrayList<Double>();

        for (ArrayList<String> subRoute : route) {
            int value = (int) Math.pow(3.0, (double) subRoute.size());
            double[] totalCost = new double[value];
            double[] totalTime = new double[value];

            for (int i = 0; i < subRoute.size(); i++) {
                int k = 0;

                while (k < value) {

                    for (int j = 0; j < 6; j += 2) {
                        int count = 0;

                        while (count < value / ((int) Math.pow(3.0, (double) (i + 1)))) {
                            totalCost[k] += data.get(subRoute.get(i))[j];
                            totalTime[k] += data.get(subRoute.get(i))[j + 1];
                            count++;
                            k++;
                        }
                    }
                }
            }

            double minTime = Double.MAX_VALUE;
            double minCost = 0;
            int trace = 0;
            for (int j = 0; j < totalTime.length; j++) {
                if (totalTime[j] < minTime) {
                    if (totalCost[j] <= budget) {
                        minTime = totalTime[j];
                        minCost = totalCost[j];
                        trace = j;
                    }
                }
            }

            String traceS = Integer.toString(asBase3(trace));
            while (traceS.length() < subRoute.size()) {
                traceS = "0" + traceS;
            }
            traceRoute.add(traceS);
            finalTime.add((int) minTime);
            finalCost.add(minCost);
        }

        int outputTime = Integer.MAX_VALUE;
        for (int temp : finalTime) {
            if (temp < outputTime) {
                outputTime = temp;
            }
        }
        int k = finalTime.indexOf(outputTime);
        String finalRoute = "";
        for (int i = 0; i < route.get(k).size(); i++) {
            if (i == route.get(k).size() - 1) {
                finalRoute += route.get(k).get(i);
            } else {
                finalRoute += route.get(k).get(i) + ",";
            }
        }

        String[] solution = {finalRoute, traceRoute.get(k), Double.toString(finalCost.get(k)), Integer.toString(outputTime)};
        return solution;
    }


    /**
     * convert input number from base 10 to base 3
     *
     * @param integer in base 10
     * @return integer in base 3
     */
    private static int asBase3(int num) {
        int ret = 0, factor = 1;
        while (num > 0) {
            ret += num % 3 * factor;
            num /= 3;
            factor *= 10;
        }
        return ret;
    }


    /**
     * return the associated transport modes (in order) based on the optimal route
     *
     * @param ArrayList<String> of the optimal route, the corresponding transport modes, total cost and total time
     * @return String[] of the associated transport modes (in order) based on the optimal route
     */
    public static String[] getTransMode(String[] solution) {
        String[] modes = {"public", "taxi", "walk"};
        String[] output = new String[solution[1].length()];
        for (int i = 0; i < solution[1].length(); i++) {
            output[i] = modes[Integer.parseInt(String.valueOf((solution[1].charAt(i))))];
        }
        return output;
    }


    /**
     * return the optimal route (places to visit in order)
     *
     * @param ArrayList<String> of the optimal route, the corresponding transport modes, total cost and total time
     * @return String[] of the optimal route (places to visit in order)
     */
    public static String[] getOptimalRoute(String[] solution) {
        HashMap<Character, String> codeNames = new HashMap<Character, String>();
        codeNames.put('a', "Marina Bay Sands");
        codeNames.put('b', "Singapore Flyer");
        codeNames.put('c', "Vivo City");
        codeNames.put('d', "Resorts World Sentosa");
        codeNames.put('e', "Buddha Tooth Relic Temple");
        codeNames.put('f', "Zoo");
        codeNames.put('g', "Botanic Gardens");
        codeNames.put('h', "Peranakan Museum");
        codeNames.put('i', "ION Orchard");

        char[] output = new char[solution[1].length() + 1];
        String[] places = new String[solution[1].length() + 1];
        int count = 0;
        for (int i = 0; i < solution[0].length(); i += 3) {
            output[count] = solution[0].charAt(i);
            count++;
        }
        output[solution[1].length()] = 'a';
        for (int j = 0; j < output.length; j++) {
            places[j] = codeNames.get(output[j]);
        }
        return places;
    }


    /**
     * return the mode of transport from one individual location to the other
     *
     * @param ArrayList<String> of the optimal route, the corresponding transport modes, total cost and total time
     * @param String input of the starting location
     * @return String output of the mode of transport from the starting location
     */
    public static String getIndiMode(String[] solution, String startLocation) {
        String[] optimalRoute = getOptimalRoute(solution);
        String[] transModes = getTransMode(solution);
        int index = Arrays.asList(optimalRoute).indexOf(startLocation);
        return startLocation + " to " + optimalRoute[index + 1] + ": by " + transModes[index];
    }

    
    /**
     * return the total travelling cost
     *
     * @param ArrayList<String> of the optimal route, the corresponding transport modes, total cost and total time
     * @return String output of the total travelling cost
     */
    public static String getCost(String[] solution) {
        double roundOff = Math.round(Double.parseDouble(solution[2]) * 100.0) / 100.0;
        return "Total cost: S$" + String.valueOf(roundOff);
    }


    /**
     * return the total travelling time
     *
     * @param ArrayList<String> of the optimal route, the corresponding transport modes, total cost and total time
     * @return String output of the total travelling time
     */
    public static String getTime(String[] solution) {
        return "Total time: " + solution[3] + " min";
    }
}
