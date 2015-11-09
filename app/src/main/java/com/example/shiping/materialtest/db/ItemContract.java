package com.example.shiping.materialtest.db;

import android.provider.BaseColumns;

/**
 * Created by Admin on 10/31/2015.
 */
public class ItemContract {
    public static final String DB_NAME = "com.example.shiping.materialtest.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "packing_list";

    public class Columns {
        public static final String ITEM = "items";
        public static final String CHECKED = "checked";
        public static final String _ID = BaseColumns._ID;
    }

}
