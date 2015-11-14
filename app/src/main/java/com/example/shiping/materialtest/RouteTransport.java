package com.example.shiping.materialtest;



public class RouteTransport {
    public  String route;
    public  String transport;

    public RouteTransport(String route, String transport) {
        this.route = route;
        this.transport = transport;
    }



    public String inputText(String route, String transport) {
        String out = "";
        if (route.equals("Marina Bay Sands")) {
            route = "hotel at Marina Bay Sands";
        }
        if (transport.equals("walk")) {
            out += "Walk to " + route;
        }
        if (transport.equals("public")) {
            out += "Take public transport to " + route;
        }
        if (transport.equals("taxi")) {
            out += "Get a taxi to " + route;
        }
        return out;
    }
}
