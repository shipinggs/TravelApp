package com.example.shiping.materialtest;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class RouteFinder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_finder);

        String[] s1 = getIntent().getStringArrayExtra("best route");
        String[] s2 = getIntent().getStringArrayExtra("transport mode");
        String s3 = getIntent().getStringExtra("total cost");
        String s4 = getIntent().getStringExtra("total time");

//        List<String> route = Arrays.asList(s1);
//        List<String> transport = Arrays.asList(s2);


        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("Itinerary");
        setSupportActionBar(toolbar);


        ArrayList<RouteTransport> arrayofroutes = new ArrayList<RouteTransport>();
        RouteAdapter adapter = new RouteAdapter(this, arrayofroutes);

        ListView listview = (ListView) findViewById(R.id.list_view);
        listview.setAdapter(adapter);

        for (int i = 0; i < s2.length; i++) {
            RouteTransport lol = new RouteTransport(s1[i+1],s2[i]);
            adapter.add(lol);
        }

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

