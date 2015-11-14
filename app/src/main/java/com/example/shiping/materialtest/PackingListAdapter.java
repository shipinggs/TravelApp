package com.example.shiping.materialtest;

/**
 * Created by Admin on 11/1/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import java.util.ArrayList;

/**
 * Custom Adapter class for the packing list ListView.
 * This Custom Adapter takes data as an ArrayList of String[] where each String array is in the
 * format [(item to bring), (is_checked status)]. For example, {"water bottle", "Y"} means the item
 * to bring is water bottle, and the user has already checked this item off.
 */

public class PackingListAdapter extends ArrayAdapter<ArrayList<String[]>> {
    ArrayList<String[]> modelItems = null;
    Context context;

    public PackingListAdapter(Context context, ArrayList<String[]> resource) {
        super(context, R.layout.item_view, (ArrayList) resource);

        this.context = context;
        this.modelItems = resource;
    }

    /**
     * Populate the ListView by showing the item and the checkbox' checked status accordingly.
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.item_view, parent, false);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.itemCheckBox);
        cb.setText(modelItems.get(position)[0]);
        if (modelItems.get(position)[1].equals("Y")) {
            cb.setChecked(true);
            cb.setTypeface(null, Typeface.BOLD_ITALIC);
        } else {
            cb.setChecked(false);
            cb.setTypeface(null, Typeface.NORMAL);
        }
        return convertView;
    }

    public void updateListView (ArrayList<String[]> list) {
        modelItems = list;
        notifyDataSetChanged();
    }
}

