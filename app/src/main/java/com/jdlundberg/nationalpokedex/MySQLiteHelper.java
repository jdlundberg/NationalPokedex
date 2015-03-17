package com.jdlundberg.nationalpokedex;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Architect on 3/14/2015.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_POKEMON = "pokemon";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SPECIES = "species";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_HEIGHT = "height";
    public static final String COLUMN_WEIGHT = "weight";

    private static final String DATABASE_NAME = "pokedex.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_POKEMON + "(" + COLUMN_ID + " integer primary key autoincrement, "
                                  + COLUMN_NAME + " text not null, "
                                  + COLUMN_SPECIES + " text not null, "
                                  + COLUMN_TYPE + " text not null, "
                                  + COLUMN_HEIGHT + " int not null, "
                                  + COLUMN_WEIGHT + " int not null);";

    public MySQLiteHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        database.execSQL(DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.w("Upgrading database",
            "Upgrading database from version " + oldVersion + " to "
                + newVersion);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POKEMON);

        onCreate(db);

    }

}
