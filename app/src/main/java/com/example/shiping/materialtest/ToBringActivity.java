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

import com.example.shiping.materialtest.db.ItemContract;
import com.example.shiping.materialtest.db.ItemDBHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Activity that works like a checklist app.
 * According to the locations users want to visit, a list of items is generated, recommending them
 * what to bring. User can then edit this list by adding more items, deleting items off, or tick
 * the checkbox as they go along doing their packing.
 * This data is stored in a SQLite database so that when the activity is stopped or the app is
 * closed, the checklist is retrieved, and users can continue working on it.
 * However, when the user edit the list of locations to visit, the existing database is deleted
 * and a new packing list is generated and proposed to them.
 * Packing list is shown using a ListView, where members of this ListView contains a checkbox
 * and a delete button.
 */

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

    /**
     * Method to delete existing data in database and create a new packing list according to locations
     * to be visited.
     */

    private void initiateItemList() {
        helper = new ItemDBHelper(ToBringActivity.this);
        SQLiteDatabase sqlDB = helper.getWritableDatabase();
        sqlDB.delete(ItemContract.TABLE, null, null);

        for (Map.Entry<String, String[]> entry : (new TodoData()).toBringList.entrySet()) {
            if (Arrays.asList(ListOfSelectedPlacesAndModes.interestedLocations).contains(entry.getKey()))
                for (String item : entry.getValue()) {
                    ContentValues values = new ContentValues();
                    values.clear();
                    values.put(ItemContract.Columns.ITEM, item);
                    values.put(ItemContract.Columns.CHECKED, "N");

                    sqlDB.insertWithOnConflict(ItemContract.TABLE, null, values,
                            SQLiteDatabase.CONFLICT_REPLACE);
            }
        }
    }

    /**
     * Every time the data in the SQLite table database is changed (including when an item is added,
     * removed) updateUI() method is called to re-draw the ListView, to display
     * new data.
     */

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

    /**
     * Method to refresh the UI when a checkbox is checked, by notifying the adapter that its data
     * has changed.
     */

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

    /**
     * Handling user tapping on the (+) action bar menu item.
     * Allow user to add items to the checklist. When is happens, a dialogue pops up, promting
     * user to type in the item. They can then confirm adding action, or cancel.
     * Upon confirmation, item is added to database, and UI is updated to reflect this addition.
     * @param item menu item selected, which is (+) in this case
     * @return
     */

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

    /**
     * When the trash icon next to a packing item is tapped, that item is removed from database and
     * UI is updated to reflect this change.
     * @param view the ButtonView of this delete button that was tapped.
     */
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

    /**
     * Handling when a checkbox for an item is selected.
     * If the box is checked, the font for the accompanying item is bolded and italicized.
     * If it is unchecked, the font is normal.
     * Database is updated to reflect the checked status of this item.
     * UI is updated to reflect the change.
     * @param view
     */
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

