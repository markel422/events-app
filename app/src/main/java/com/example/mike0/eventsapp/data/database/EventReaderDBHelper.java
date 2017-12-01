package com.example.mike0.eventsapp.data.database;

import android.content.Context;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import com.example.mike0.eventsapp.data.database.EventReaderContract.EventEntry;

/**
 * Created by mike0 on 11/22/2017.
 */

public class EventReaderDBHelper extends SQLiteOpenHelper {
    private static EventReaderDBHelper instance;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "myEventReader.db";

    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + EventEntry.TABLE_NAME + " (" +
                                                                        EventEntry._ID + " INTEGER PRIMARY KEY," +
                                                                        EventEntry.COLUMN_NAME_TITLE + " TEXT," +
                                                                        EventEntry.COLUMN_NAME_DESCRIPTION + " TEXT," +
                                                                        EventEntry.COLUMN_NAME_TIME + " TEXT," +
                                                                        EventEntry.COLUMN_NAME_URL + " TEXT)";

    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + EventEntry.TABLE_NAME;

    public EventReaderDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    static public synchronized EventReaderDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new EventReaderDBHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
