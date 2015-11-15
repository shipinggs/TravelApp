package com.example.shiping.materialtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Creates a class that serves as an Array adapter in order to generate a ListView with the solution
 * generated from function 1. It adds or assign content to the route layout of, which consist of
 * a TextView and an ImageView
 */
public class RouteAdapter extends ArrayAdapter<RouteTransport> {

    public RouteAdapter(Context context, ArrayList<RouteTransport> users) {
        super(context, 0, users);
    }

    /**
     * Populate the ListView by showing the transportation-destination text and transportation icons .
     * @param position
     * @param convertView
     * @param parent
     * @return
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.route, parent, false);
        }
        RouteTransport item = getItem(position);


        TextView test = (TextView) convertView.findViewById(R.id.textView2);
        ImageView test3 = (ImageView) convertView.findViewById(R.id.imageView);
        // Populate the data into the template view using the data object
        test.setText(item.inputText(item.route, item.transport));

        if (item.transport .equals("walk")) {
            test3.setImageResource(R.drawable.walking);
        } if (item.transport.equals("public")){
            test3.setImageResource(R.drawable.bus);
        } if(item.transport.equals("taxi")) {
            test3.setImageResource(R.drawable.taxi);
        }
        // Return the completed view to render on screen
        return convertView;
    }
}
