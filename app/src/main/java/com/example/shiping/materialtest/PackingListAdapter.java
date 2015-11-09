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

public class PackingListAdapter extends ArrayAdapter<ArrayList<String[]>> {
    ArrayList<String[]> modelItems = null;
    Context context;

    public PackingListAdapter(Context context, ArrayList<String[]> resource) {
        super(context, R.layout.item_view, (ArrayList) resource);

        // TODO Auto-generated constructor stub
        this.context = context;
        this.modelItems = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
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

