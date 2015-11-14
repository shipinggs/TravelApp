package com.example.shiping.materialtest.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Helper class for the SQLite database that handle creating and upgrading the data table.
 */
public class ItemDBHelper extends SQLiteOpenHelper {
    public ItemDBHelper(Context context) {
        super(context, ItemContract.DB_NAME, null, ItemContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqlDB) {
        String sqlQuery =
                String.format("CREATE TABLE %s (" +
                                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "%s TEXT UNIQUE ON CONFLICT FAIL, %s TEXT)", ItemContract.TABLE,
                        ItemContract.Columns.ITEM, ItemContract.Columns.CHECKED);

        Log.d("TaskDBHelper", "Query to form table: " + sqlQuery);
        sqlDB.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlDB, int i, int i2) {
        sqlDB.execSQL("DROP TABLE IF EXISTS "+ItemContract.TABLE);
        onCreate(sqlDB);
    }

}
