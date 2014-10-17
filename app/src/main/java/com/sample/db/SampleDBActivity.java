package com.sample.db;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class SampleDBActivity extends Activity {
	private static final String TAG = "DataStoreActivity";
    private DataStore mDataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		createDB();
        initEventHandlers();
    }

    private void initEventHandlers() {
        findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addRecordToDB();
//                updateDB();

//                addRecordToCP();
//                updateCPRecord();
            }
        });

        findViewById(R.id.showDBButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDBContents();
//                showCPContents();
            }
        });
    }

    private void createDB() {
        if (mDataStore == null) {
            mDataStore = new DataStore(this, null, null, 0);
        }
    }

    private void addRecordToDB() {
        long ret = mDataStore.insert(getContentValuesFromUserInput());

        if (ret != -1) {
            Toast.makeText(this, "Added to DB at: " + ret, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Failed to add to DB!", Toast.LENGTH_LONG).show();
        }
    }

    private void showDBContents() {
        if (mDataStore != null) {
            updateListFromCursor(mDataStore.query(null, null, null, null, null, null));

            /*TextView resultsTextView = (TextView) findViewById(R.id.results_text);
            Cursor cursor = mDataStore.query(null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                resultsTextView.setText(cursor.toString());
            }*/
        }
    }

    private void updateListFromCursor(Cursor cursor) {
        ListView resultsListView = (ListView) findViewById(R.id.results_list);
        if (cursor.moveToFirst()) {
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item, cursor,
                    new String[]{DataStore.COLUMN_NAME, DataStore.COLUMN_PHONE},
                    new int[]{android.R.id.text1, android.R.id.text2});
            resultsListView.setAdapter(adapter);
        }
    }

    private ContentValues getContentValuesFromUserInput() {
        EditText id = (EditText) findViewById(R.id.idField);
        String idStr = id.getText().toString();
        EditText nameFld = (EditText) findViewById(R.id.nameField);
        String name = nameFld.getText().toString();
        EditText phFld = (EditText) findViewById(R.id.phField);
        String phone = phFld.getText().toString();
        EditText emailFld = (EditText) findViewById(R.id.emailField);
        String email = emailFld.getText().toString();

        ContentValues values = new ContentValues();
        values.put(DataStore.COLUMN_CODE, idStr);
        values.put(DataStore.COLUMN_NAME, name);
        values.put(DataStore.COLUMN_PHONE, phone);
        values.put(DataStore.COLUMN_EMAILID, email);

        return values;
    }

    private void updateDB() {
        String where = "name like 'name%'";
        ContentValues values = new ContentValues();
        values.put(DataStore.COLUMN_PHONE, "9876543210");
        int ret = mDataStore.update(values, where);
        Log.d(TAG, "Updated record: " + ret);
    }

	/*private void clearTextFields() {
        ((EditText) findViewById(R.id.idField)).setText("");
		((EditText) findViewById(R.id.nameField)).setText("");
		((EditText) findViewById(R.id.phField)).setText("");
		((EditText) findViewById(R.id.emailField)).setText("");
	} */


    /* Using CP */
    private void addRecordToCP() {
        ContentResolver cr = getContentResolver();
        cr.insert(DataStoreProvider.URI_CONTACTS_STORE, getContentValuesFromUserInput());
    }

    private void showCPContents() {
        Cursor cursor = getContentResolver().query(DataStoreProvider.URI_CONTACTS_STORE, null, null, null, null);
        updateListFromCursor(cursor);
    }

    private void updateCPRecord() {
        String where = "name like 'name%'";
        ContentValues values = new ContentValues();
        values.put(DataStore.COLUMN_PHONE, "9876543210");
        ContentResolver cr = getContentResolver();
        int ret = cr.update(DataStoreProvider.URI_CONTACTS_STORE, values, where, null);
        Log.d(TAG, "Updated CP record: " + ret);
    }
}