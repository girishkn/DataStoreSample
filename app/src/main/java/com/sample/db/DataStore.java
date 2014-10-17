package com.sample.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataStore extends SQLiteOpenHelper {

	public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CODE = "code";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE = "phone";
    //	public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_EMAILID = "email_id";
    public static final String COLUMN_IMAGE = "image";
    private static final String TAG = "DataStore";
    private static final String DB_NAME = "contacts_db";
    private static final int DB_VERSION = 1;
    public static final String CONTACTS_TABLE_NAME = "contacts_tbl";

    public DataStore(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "DataStore: onCreate()");
        createContactsTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "DataStore::onUpgrade(), oldVersion: " + oldVersion + ", newVersion: " + newVersion);
        updateDatabaseTable(db);
    }

    private void createContactsTable(SQLiteDatabase db) {
        Log.d(TAG, "Creating table: " + CONTACTS_TABLE_NAME);
        String statement = "Create table " + CONTACTS_TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CODE + " TEXT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PHONE + " TEXT, " +
//                COLUMN_ADDRESS + " TEXT, "+
                COLUMN_EMAILID + " TEXT " + ");";
        try {
            db.execSQL(statement);
            Log.d(TAG, "Table created: " + CONTACTS_TABLE_NAME);
        } catch (SQLException e) {
            // TODO: handle exception
            Log.d(TAG, "Failed creating table: " + CONTACTS_TABLE_NAME);
        }
    }

    private void updateDatabaseTable(SQLiteDatabase db) {
        String statement = "Alter table " + CONTACTS_TABLE_NAME + " Add " + COLUMN_IMAGE + " BLOB;";
        try {
            db.execSQL(statement);
            Log.d(TAG, "Table updated: " + CONTACTS_TABLE_NAME);
        } catch (SQLException e) {
            // TODO: handle exception
        }
    }

    public long insert(ContentValues values) {
        long ret = -1;
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            ret = db.insert(CONTACTS_TABLE_NAME, null, values);
        }
        return ret;
    }

    public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        SQLiteDatabase db = getReadableDatabase();
        if (db != null) {
            return db.query(CONTACTS_TABLE_NAME, columns, selection, selectionArgs, null, null, orderBy);
        }

        return null;
    }

    public int update(ContentValues values, String whereClause) {
        int i = 0;
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            i = db.update(CONTACTS_TABLE_NAME, values, whereClause, null);
            Log.d(TAG, "Updated: " + i);
        }
        return i;
    }
}