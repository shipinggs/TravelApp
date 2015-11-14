package com.example.shiping.materialtest;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * The fragment for displaying weather forecast for the next day in Singapore
 * Weather information includes weather description, temperature, wind speed, humidity and pressure
 */


public class WeatherFragment extends android.support.v4.app.Fragment {
    Typeface weatherFont;
    TextView cityField;
    TextView updatedField;
    TextView detailsField;
    TextView currentTemperatureField;
    TextView weatherIcon;

    Handler handler;

    public WeatherFragment(){
        handler = new Handler();
    }

    /**
     * weather.ttf is a font containing different weather icons.
     * onCreate method also calls the updateWeatherData() method, which does an API call to fetch
     * weather data from OpenWeatherMap.org
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weather.ttf");
        updateWeatherData();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);
        cityField = (TextView)rootView.findViewById(R.id.city_field);
        updatedField = (TextView)rootView.findViewById(R.id.updated_field);
        detailsField = (TextView)rootView.findViewById(R.id.details_field);
        currentTemperatureField = (TextView)rootView.findViewById(R.id.current_temperature_field);
        weatherIcon = (TextView)rootView.findViewById(R.id.weather_icon);

        weatherIcon.setTypeface(weatherFont);
        return rootView;
    }

    /**
     * Call the FetchWeather.getJASON() method which returns a JSON object containing weather
     * forecast information and save it in a JSONObject called json
     * Check that this JSON object is not null (a toast message is displayed if it is), and
     * then call a method renderWeather() to process this JSON object, extracting out the
     * relevant weather information.
     */

    private void updateWeatherData(){
        new Thread(){
            public void run(){
                final JSONObject json = FetchWeather.getJSON(getActivity());
                if(json == null){
                    handler.post(new Runnable(){
                        public void run(){
                            Toast.makeText(getActivity(),
                                    getActivity().getString(R.string.place_not_found),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable(){
                        public void run(){
                            renderWeather(json);
                        }
                    });
                }
            }
        }.start();
    }

    /**
     * Given a JSON, extract out relevant information like City name, country, weather description,
     * humidity, pressure, temperature, etc. and display the information on the views on the UI.
     * Also extract sunrise and sunset time to display icons accordingly. If the current time is
     * after sunrise, before sunset, then the sun icons are used. Otherwise, moon icons are used,
     * where relevant.
     * @param json the JSONObject fetched from OpenWeatherMap.org API call
     */

    private void renderWeather(JSONObject json){
        try {
            cityField.setText(json.getJSONObject("city").getString("name").toUpperCase(Locale.US)
                    +", " +
                    json.getJSONObject("city").getString("country"));

            JSONObject day1 =json.getJSONArray("list").getJSONObject(1);
            JSONObject details = day1.getJSONArray("weather").getJSONObject(0);
            JSONObject main = day1.getJSONObject("main");
            detailsField.setText(
                    details.getString("description").toUpperCase(Locale.US) +
                            "\n" + "Humidity: " + main.getString("humidity") + "%" +
                            "\n" + "Pressure: " + main.getString("pressure") + " hPa");

            currentTemperatureField.setText(
                    String.format("%.1f", main.getDouble("temp")/10)+ " ℃");

            DateFormat df = DateFormat.getDateTimeInstance();
            String updatedOn = df.format(new Date());
            updatedField.setText("Last update: " + updatedOn);

            setWeatherIcon(details.getInt("id"),
                    1446849974,  //sunrise time
                    1446893411); //sunset time

        }catch(Exception e){
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }
    }

    /**
     * method to display the weather icon
     * @param actualId ID of the weather condition. Each weather condition is given a unique ID.
     *                 We use this ID to display the correct weather icon.
     * @param sunrise sunrise time
     * @param sunset sunset time
     */

    private void setWeatherIcon(int actualId, long sunrise, long sunset){
        int id = actualId / 100;
        String icon = "";
        if(actualId == 800){
            long currentTime = new Date().getTime();
            if(currentTime>=sunrise && currentTime<sunset) {
                icon = getActivity().getString(R.string.weather_sunny);
            } else {
                icon = getActivity().getString(R.string.weather_clear_night);
            }
        } else {
            switch(id) {
                case 2 : icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 3 : icon = getActivity().getString(R.string.weather_drizzle);
                    break;
                case 7 : icon = getActivity().getString(R.string.weather_foggy);
                    break;
                case 8 : icon = getActivity().getString(R.string.weather_cloudy);
                    break;
                case 6 : icon = getActivity().getString(R.string.weather_snowy);
                    break;
                case 5 : icon = getActivity().getString(R.string.weather_rainy);
                    break;
            }
        }
        weatherIcon.setText(icon);
    }





}
