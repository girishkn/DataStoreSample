package com.sample.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class DataStoreProvider extends ContentProvider {

	private static final String TAG = "DataStoreProvider";
    public static final Uri URI_CONTACTS_STORE = Uri.parse( "content://com.gkn.datastore.datastoreprovider/contacts_tbl");

    private static DataStore mDataStore = null;

    @Override
    public boolean onCreate() {
        mDataStore = new DataStore(getContext(), null, null, 0);
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (uri.compareTo(URI_CONTACTS_STORE) == 0) {
            return mDataStore.query(projection, selection, selectionArgs, null, null, sortOrder);
        }
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, "DataStoreProvider::insert");
        if (uri.compareTo(URI_CONTACTS_STORE) == 0)
            mDataStore.insert(values);
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

}
