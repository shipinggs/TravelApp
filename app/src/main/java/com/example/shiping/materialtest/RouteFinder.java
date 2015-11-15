package com.example.shiping.materialtest;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * The activity displays the itinerary that is generated from the respective algorithm selected by the user.
 * It shows the total time, total cost of the travelling, and which mode of transport to take to which destination
 * in which order,
 */

public class RouteFinder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_finder);

            // get the solution data from MainActivity
        String[] s1 = getIntent().getStringArrayExtra("best route");
        String[] s2 = getIntent().getStringArrayExtra("transport mode");
        String s3 = getIntent().getStringExtra("total cost");
        String s4 = getIntent().getStringExtra("total time");


        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("Itinerary");
        setSupportActionBar(toolbar);

            // create an instance of the route adapter
        ArrayList<RouteTransport> arrayofroutes = new ArrayList<RouteTransport>();
        RouteAdapter adapter = new RouteAdapter(this, arrayofroutes);

            // create the ListView with adapter set to the route adapter
        ListView listview = (ListView) findViewById(R.id.list_view);
        listview.setAdapter(adapter);

            // add string content of the TextView to the adapter
        for (int i = 0; i < s2.length; i++) {
            RouteTransport rt = new RouteTransport(s1[i+1],s2[i]);
            adapter.add(rt);
        }

            // set the content of the TextView on top to be total cost and total time taken
        TextView cost = (TextView) findViewById(R.id.cost);
        TextView time = (TextView) findViewById(R.id.time);

        cost.setText(s3);
        time.setText(s4);





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_route_finder, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

