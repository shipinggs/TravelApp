package com.example.shiping.materialtest;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shiping.materialtest.db.ItemContract;
import com.example.shiping.materialtest.db.ItemDBHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ToBringActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private ItemDBHelper helper;
    PackingListAdapter packingListAdapter;
    ArrayList<String[]> resource;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_bring);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("Packing List");
        setSupportActionBar(toolbar);

        //TODO: call initiateItemList() only when there is a change in locations

        if (ListOfSelectedPlacesAndModes.rememberBoole != ListOfSelectedPlacesAndModes.compareBoole) {
            initiateItemList();
            ListOfSelectedPlacesAndModes.compareBoole = ListOfSelectedPlacesAndModes.rememberBoole;
        }
        updateUI();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_things_to_bring, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void initiateItemList() {
        helper = new ItemDBHelper(ToBringActivity.this);
        SQLiteDatabase sqlDB = helper.getWritableDatabase();
        sqlDB.delete(ItemContract.TABLE, null, null);



        for (Map.Entry<String, String[]> entry : (new TodoData()).toBringList.entrySet()) {
            for (String item : entry.getValue()) {
                if (Arrays.asList(ListOfSelectedPlacesAndModes.interestedLocations).contains(entry.getKey())) {
                    ContentValues values = new ContentValues();
                    values.clear();
                    values.put(ItemContract.Columns.ITEM, item);
                    values.put(ItemContract.Columns.CHECKED, "N");

                    sqlDB.insertWithOnConflict(ItemContract.TABLE, null, values,
                            SQLiteDatabase.CONFLICT_REPLACE);
                }
            }
        }
    }

    private void updateUI() {
        helper = new ItemDBHelper(ToBringActivity.this);
        SQLiteDatabase sqlDB = helper.getReadableDatabase();
        Cursor cursor = sqlDB.query(ItemContract.TABLE,
                new String[]{ItemContract.Columns._ID, ItemContract.Columns.ITEM, ItemContract.Columns.CHECKED},
                null, null, null, null, null);

        cursor.moveToFirst();

        resource = new ArrayList<>();

        while (!cursor.isAfterLast()) {
            String item = cursor.getString(1);
            String checked = cursor.getString(2);
            cursor.moveToNext();

            resource.add(new String[]{item, checked});
        }

        packingListAdapter = new PackingListAdapter(this, resource);

        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(packingListAdapter);
    }

    private void refreshAdapter() {
        helper = new ItemDBHelper(ToBringActivity.this);
        SQLiteDatabase sqlDB = helper.getReadableDatabase();
        Cursor cursor = sqlDB.query(ItemContract.TABLE,
                new String[]{ItemContract.Columns._ID, ItemContract.Columns.ITEM, ItemContract.Columns.CHECKED},
                null, null, null, null, null);

        cursor.moveToFirst();

        resource = new ArrayList<>();

        while (!cursor.isAfterLast()) {
            String item = cursor.getString(1);
            String checked = cursor.getString(2);
            cursor.moveToNext();
            resource.add(new String[]{item, checked});
        }

        packingListAdapter.updateListView(resource);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_item:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Add a packing item");
                builder.setMessage("What do you want to add to packing list?");
                final EditText inputField = new EditText(this);
                builder.setView(inputField);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String item = inputField.getText().toString();

                        ItemDBHelper helper = new ItemDBHelper(ToBringActivity.this);
                        SQLiteDatabase db = helper.getWritableDatabase();
                        ContentValues values = new ContentValues();

                        values.clear();
                        values.put(ItemContract.Columns.ITEM, item);
                        values.put(ItemContract.Columns.CHECKED, "N");

                        db.insertWithOnConflict(ItemContract.TABLE, null, values,
                                SQLiteDatabase.CONFLICT_IGNORE);

                        updateUI();
                    }
                });

                builder.setNegativeButton("Cancel",null);

                builder.create().show();
                return true;

            default:
                return false;
        }
    }

    public void onDeleteButtonClick(View view) {
        View v = (View) view.getParent();
        CheckBox itemCheckBox = (CheckBox) v.findViewById(R.id.itemCheckBox);
        String item = itemCheckBox.getText().toString();

        String sql = String.format("DELETE FROM %s WHERE %s = '%s'",
                ItemContract.TABLE,
                ItemContract.Columns.ITEM,
                item);


        helper = new ItemDBHelper(ToBringActivity.this);
        SQLiteDatabase sqlDB = helper.getWritableDatabase();
        sqlDB.execSQL(sql);
        updateUI();

    }

    public void onCheckboxCheck (View view) {
        View v = (View) view.getParent();
        CheckBox itemCheckBox = (CheckBox) v.findViewById(R.id.itemCheckBox);
        String item = itemCheckBox.getText().toString();

        String checked;
        if (itemCheckBox.isChecked()) {
            itemCheckBox.setTypeface(null, Typeface.BOLD_ITALIC);
            checked = "Y";
        }
        else {
            itemCheckBox.setTypeface(null, Typeface.NORMAL);
            checked = "N";
        }

        ContentValues values = new ContentValues();

        values.clear();
        values.put(ItemContract.Columns.CHECKED, checked);

        String where = String.format("%s = '%s'", ItemContract.Columns.ITEM, item);
        helper = new ItemDBHelper(ToBringActivity.this);
        SQLiteDatabase sqlDB = helper.getWritableDatabase();
        sqlDB.update(ItemContract.TABLE, values, where, null);
        refreshAdapter();
    }
}

