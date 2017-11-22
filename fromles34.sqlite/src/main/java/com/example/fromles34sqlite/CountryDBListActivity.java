package com.example.fromles34sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class CountryDBListActivity extends AppCompatActivity{

    private final static String LOG_TAG = "myLog";
    private final static String DB_TABLE_NAME = "myTable";
    private final static String DB_COLUMN_NAME = "name";
    private final static String DB_COLUMN_PEOPLE = "people";
    private final static String DB_COLUMN_REGION = "region";

    EditText etFunc;
    EditText etPeople;
    EditText etRegionPeople;

    RadioGroup rgSort;

    DBHelper dbHelper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_list_db);

        findViewById(R.id.btnAll).setOnClickListener(new OnClickListenerImpl());
        findViewById(R.id.btnFunc).setOnClickListener(new OnClickListenerImpl());
        findViewById(R.id.btnPeople).setOnClickListener(new OnClickListenerImpl());
        findViewById(R.id.btnSort).setOnClickListener(new OnClickListenerImpl());
        findViewById(R.id.btnGroup).setOnClickListener(new OnClickListenerImpl());
        findViewById(R.id.btnHaving).setOnClickListener(new OnClickListenerImpl());

        etFunc = findViewById(R.id.etFunc);
        etPeople = findViewById(R.id.etPeople);
        etRegionPeople = findViewById(R.id.etRegionPeople);

        rgSort = findViewById(R.id.rgSort);

        dbHelper = new DBHelper(this);

        database = dbHelper.getWritableDatabase();

        Cursor cursor = database.query(DB_TABLE_NAME, null, null, null, null, null, null);

        String [] name = getResources().getStringArray(R.array.name);
        String [] region = getResources().getStringArray(R.array.region);
        int [] people = getResources().getIntArray(R.array.people);


        if (cursor.getCount() == 0){
            ContentValues contentValues = new ContentValues();
            for (int i = 0; i < 10; i++) {
                contentValues.put(DB_COLUMN_NAME, name[i]);
                contentValues.put(DB_COLUMN_PEOPLE, people[i]);
                contentValues.put(DB_COLUMN_REGION, region[i]);
                Log.d(LOG_TAG, "id = " + database.insert(DB_TABLE_NAME, null, contentValues));
            }
        }
        cursor.close();
        dbHelper.close();
        new OnClickListenerImpl().onClick(findViewById(R.id.btnAll));
    }

    private class OnClickListenerImpl implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            database = dbHelper.getWritableDatabase();

            String sFunc = etFunc.getText().toString();
            String sPeople = etPeople.getText().toString();
            String sRegionPeople = etRegionPeople.getText().toString();

            String [] columns;
            String selection;
            String [] selectionArgs;
            String groupBy;
            String having;
            String orderBy = null;

            Cursor listenerCursor = null;

//            try {
//                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("https://newsapi.org/v1/articles?source=techcrunch&apiKey=7f86686ea9ab4834a54630556f7c14ec").openConnection();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            switch (view.getId()){
                case R.id.btnAll:
                    Log.d(LOG_TAG, "--- All records ---");
                    listenerCursor = database.query(DB_TABLE_NAME, null, null, null, null, null, null);
                    break;
                case R.id.btnFunc:
                    Log.d(LOG_TAG, "--- Функция " + sFunc + " ---");
                    columns = new String[] { sFunc };
                    listenerCursor = database.query(DB_TABLE_NAME, columns, null, null, null, null, null);
                    break;
                case R.id.btnPeople:
                    Log.d(LOG_TAG, "--- Population bigger than " + sPeople + " ---");
                    selection = "people > ?";
                    selectionArgs = new String [] {sPeople};
                    listenerCursor = database.query(DB_TABLE_NAME, null, selection, selectionArgs, null, null, null);
                    break;
                case R.id.btnGroup:
                    Log.d(LOG_TAG, "--- Region population ---");
                    columns = new String [] {"region", "sum(people) as people"};
                    groupBy = "region";
                    listenerCursor = database.query(DB_TABLE_NAME, columns, null, null, groupBy, null, null);
                    break;
                case R.id.btnHaving:
                    Log.d(LOG_TAG, "--- Regions with population bigger than " + sRegionPeople + " ---");
                    columns = new String[] {"region", "sum(people) as people"};
                    groupBy = "region";
                    having = "sum(people) > " + sRegionPeople;
                    listenerCursor = database.query(DB_TABLE_NAME, columns, null, null, groupBy, having, null);
                    break;
                case R.id.btnSort:
                    switch (rgSort.getCheckedRadioButtonId()){
                        case R.id.rName:
                            Log.d(LOG_TAG, "--- Sorting by name ---");
                            orderBy = DB_COLUMN_NAME;
                            break;
                        case R.id.rPeople:
                            Log.d(LOG_TAG, "--- Sorting by population ---");
                            orderBy = DB_COLUMN_PEOPLE;
                            break;
                        case R.id.rRegion:
                            Log.d(LOG_TAG, "--- Sorting by region ---");
                            orderBy = DB_COLUMN_REGION;
                            break;
                    }
                    listenerCursor = database.query(DB_TABLE_NAME, null, null, null, null, null, orderBy);
                    break;
            }
            if ( listenerCursor != null){
                if (listenerCursor.moveToFirst()){
                    String str;
                    do {
                        str = "";
                        for (String cn : listenerCursor.getColumnNames()) {
                            str = str.concat(cn + " = " + listenerCursor.getString(listenerCursor.getColumnIndex(cn)) + "; ");
                        }
                        Log.d(LOG_TAG, str);
                    } while (listenerCursor.moveToNext());
                    listenerCursor.close();
                } else {
                    Log.d(LOG_TAG, "Cursor is null");
                    dbHelper.close();
                }
            }
        }
    }

    private class DBHelper extends SQLiteOpenHelper{

        final static String DB_NAME = "myDB";

        DBHelper(Context context) {
            super(context, DB_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            Log.d(LOG_TAG, "--- onCreate database ---");
            sqLiteDatabase.execSQL("create table myTable ("
                    + "id integer primary key autoincrement," + "name text,"
                    + "people integer," + "region text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}
