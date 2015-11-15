package com.example.shiping.materialtest.db;


import java.util.HashMap;

/** This class houses all the Traveling Distance and Cost Data between the 9 chosen locations. **/

public class MapData {

    // create a hashmap of the time and cost of all transport modes of all possible routes
    public static HashMap<String, double[]> createData() {
        HashMap<String, double[]> data = new HashMap<String, double[]> ();

        /**
         * FOR REFERENCE:
         * A --> Marina Bay Sands
         * B --> Singapore Flyer
         * C --> Vivo City
         * D --> Resorts World Sentosa
         * E --> Buddha Tooth Relic Temple
         * F --> Singapore Zoo/ Zoo
         * G --> Singapore Botanic Gardens/ Botanic Gardens
         * H --> Peranakan Museum
         * I --> ION Orchard
         * e.g. from A to B = {publicCost, publicTime, taxiCost, taxiTime, walkCost, walkTime}
         *
         * These travel times and costs are referenced from gothere.sg and Google Maps Directions tool. **/

        double[] listAB = {0.83, 17, 3.22, 3, 0, 14};
        double[] listAC = {1.18, 26, 6.96, 14, 0, 69};
        double[] listAD = {4.03, 35, 8.50, 19, 0, 76};
        double[] listAE = {0.88, 19, 4.98, 8, 0, 28};
        double[] listAF = {1.96, 84, 18.4, 30, 0, 269};
        double[] listAG = {1.38, 36, 15.80, 13, 0, 97};
        double[] listAH = {0.92, 24, 4.32, 7, 0, 31};
        double[] listAI = {1.13, 25, 13.05, 10, 0, 60};


        double[] listBA = {0.83, 17, 4.32, 6, 0, 14};
        double[] listBC = {1.26, 31, 7.84, 13, 0, 81};
        double[] listBD = {4.03, 38, 9.38, 18, 0, 88};
        double[] listBE = {0.98, 24, 4.76, 8, 0, 39};
        double[] listBF = {1.89, 85, 18.18, 29, 0, 264};
        double[] listBG = {1.18, 36, 12.25, 13, 0, 61};
        double[] listBH = {0.87, 20, 3.66, 5, 0, 23};
        double[] listBI = {1.02, 25, 9.22, 8, 0, 53};

        double[] listCA = {1.18, 24, 8.30, 12, 0, 69};
        double[] listCB = {1.26, 29, 7.96, 14, 0, 81};
        double[] listCD = {2.00, 10, 4.54, 9, 0, 12};
        double[] listCE = {0.98, 18, 6.42, 11, 0, 47};
        double[] listCF = {1.99, 85, 22.58, 31, 0, 270};
        double[] listCG = {1.66, 29, 11.17, 19, 0, 107};
        double[] listCH = {1.13, 22, 7.40, 13, 0, 69};
        double[] listCI = {1.31, 24, 8.70, 15, 0, 72};

        double[] listDA = {1.18, 33, 8.74, 13, 0, 76};
        double[] listDB = {1.26, 38, 8.40, 14, 0, 88};
        double[] listDC = {0.00, 10, 3.22, 4, 0, 12};
        double[] listDE = {0.98, 27, 6.64, 12, 0, 55};
        double[] listDF = {1.99, 92, 22.80, 32, 0, 285};
        double[] listDG = {1.66, 38, 11.45, 20, 0, 131};
        double[] listDH = {0.98, 36, 7.62, 13, 0, 93};
        double[] listDI = {3.31, 39, 9.25, 15, 0, 95};

        double[] listEA = {0.88, 18, 5.32, 7, 0, 28};
        double[] listEB = {0.98, 23, 4.76, 8, 0, 39};
        double[] listEC = {0.98, 19, 4.98, 9, 0, 47};
        double[] listED = {3.98, 28, 6.52, 14, 0, 55};
        double[] listEF = {1.91, 83, 18.4, 30, 0, 264};
        double[] listEG = {1.31, 36, 12.80, 14, 0, 87};
        double[] listEH = {0.92, 25, 3.88, 6, 0, 23};
        double[] listEI = {0.97, 22, 9.78, 9, 0, 46};

        double[] listFA = {1.88, 86, 22.48, 32, 0, 269};
        double[] listFB = {1.96, 87, 19.40, 29, 0, 264};
        double[] listFC = {2.11, 86, 21.48, 32, 0, 270};
        double[] listFD = {4.99, 96, 23.68, 36, 0, 285};
        double[] listFE = {1.91, 84, 21.60, 30, 0, 264};
        double[] listFG = {1.96, 70, 18.05, 20, 0, 214};
        double[] listFH = {1.90, 81, 18.90, 29, 0, 254};
        double[] listFI = {1.78, 66, 20.80, 26, 0, 245};

        double[] listGA = {1.38, 43, 10.62, 15, 0, 97};
        double[] listGB = {1.18, 43, 9.53, 14, 0, 92};
        double[] listGC = {1.66, 29, 12.28, 17, 0, 107};
        double[] listGD = {3.66, 48, 14.47, 21, 0, 132};
        double[] listGE = {1.31, 38, 10.62, 16, 0, 87};
        double[] listGF = {1.93, 70, 16.40, 18, 0, 217};
        double[] listGH = {1.21, 39, 8.50, 16, 0, 73};
        double[] listGI = {0.98, 43, 6.22, 10, 0, 43};

        double[] listHA = {0.92, 24, 4.54, 7, 0, 30};
        double[] listHB = {0.87, 20, 4.32, 6, 0, 23};
        double[] listHC = {1.13, 24, 7.62, 15, 0, 68};
        double[] listHD = {4.13, 33, 9.16, 19, 0, 93};
        double[] listHE = {0.82, 21, 4.54, 8, 0, 22};
        double[] listHF = {1.90, 80, 17.30, 27, 0, 256};
        double[] listHG = {1.39, 38, 6.74, 13, 0, 72};
        double[] listHI = {0.77, 17, 8.40, 6, 0, 34};

        double[] listIA = {1.13, 25, 10.05, 11, 0, 59};
        double[] listIB = {1.02, 25, 9.78, 10, 0, 53};
        double[] listIC = {1.31, 25, 10.88, 13, 0, 71};
        double[] listID = {5.37, 45, 12.53, 18, 0, 96};
        double[] listIE = {0.97, 22, 9.50, 9, 0, 45};
        double[] listIF = {1.78, 65, 22.43, 23, 0, 246};
        double[] listIG = {0.98, 40, 8.68, 8, 0, 42};
        double[] listIH = {0.82, 19, 8.95, 9, 0, 34};


        // Short-form keyword used here for neater coding.

        data.put("ab", listAB);
        data.put("ac", listAC);
        data.put("ad", listAD);
        data.put("ae", listAE);
        data.put("af", listAF);
        data.put("ag", listAG);
        data.put("ah", listAH);
        data.put("ai", listAI);

        data.put("ba", listBA);
        data.put("bc", listBC);
        data.put("bd", listBD);
        data.put("be", listBE);
        data.put("bf", listBF);
        data.put("bg", listBG);
        data.put("bh", listBH);
        data.put("bi", listBI);

        data.put("ca", listCA);
        data.put("cb", listCB);
        data.put("cd", listCD);
        data.put("ce", listCE);
        data.put("cf", listCF);
        data.put("cg", listCG);
        data.put("ch", listCH);
        data.put("ci", listCI);

        data.put("da", listDA);
        data.put("db", listDB);
        data.put("dc", listDC);
        data.put("de", listDE);
        data.put("df", listDF);
        data.put("dg", listDG);
        data.put("dh", listDH);
        data.put("di", listDI);

        data.put("ea", listEA);
        data.put("eb", listEB);
        data.put("ec", listEC);
        data.put("ed", listED);
        data.put("ef", listEF);
        data.put("eg", listEG);
        data.put("eh", listEH);
        data.put("ei", listEI);

        data.put("fa", listFA);
        data.put("fb", listFB);
        data.put("fc", listFC);
        data.put("fd", listFD);
        data.put("fe", listFE);
        data.put("fg", listFG);
        data.put("fh", listFH);
        data.put("fi", listFI);

        data.put("ga", listGA);
        data.put("gb", listGB);
        data.put("gc", listGC);
        data.put("gd", listGD);
        data.put("ge", listGE);
        data.put("gf", listGF);
        data.put("gh", listGH);
        data.put("gi", listGI);

        data.put("ha", listHA);
        data.put("hb", listHB);
        data.put("hc", listHC);
        data.put("hd", listHD);
        data.put("he", listHE);
        data.put("hf", listHF);
        data.put("hg", listHG);
        data.put("hi", listHI);

        data.put("ia", listIA);
        data.put("ib", listIB);
        data.put("ic", listIC);
        data.put("id", listID);
        data.put("ie", listIE);
        data.put("if", listIF);
        data.put("ig", listIG);
        data.put("ih", listIH);


        return data;
    }
}
