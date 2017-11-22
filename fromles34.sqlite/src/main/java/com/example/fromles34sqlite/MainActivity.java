package com.example.fromles34sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = "myLog";

    public static final String DB_TABLE_NAME = "myTable";
    public static final String DB_COLUMN_NAME = "name";
    public static final String DB_COLUMN_EMAIL = "email";
    public static final String DB_COLUMN_ID = "id";

    private EditText etName;
    private EditText etEmail;
    private EditText etID;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnAdd).setOnClickListener(new OnClickListenerImpl());
        findViewById(R.id.btnRead).setOnClickListener(new OnClickListenerImpl());
        findViewById(R.id.btnClear).setOnClickListener(new OnClickListenerImpl());
        findViewById(R.id.btnUpd).setOnClickListener(new OnClickListenerImpl());
        findViewById(R.id.btnDel).setOnClickListener(new OnClickListenerImpl());

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etID = findViewById(R.id.etID);

        dbHelper = new DBHelper(this);
    }

    private class OnClickListenerImpl implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            ContentValues contentValues = new ContentValues();

            String name = etName.getText().toString();
            String email = etEmail.getText().toString();
            String id = etID.getText().toString();

            SQLiteDatabase database = dbHelper.getWritableDatabase();

            switch (view.getId()){
                case R.id.btnAdd:
                    Log.d(LOG_TAG, "--- Insert in myTable: ---");

                    contentValues.put(DB_COLUMN_NAME, name);
                    contentValues.put(DB_COLUMN_EMAIL, email);

                    long rowID = database.insert(DB_TABLE_NAME, null, contentValues);
                    Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                    break;
                case R.id.btnRead:
                    Log.d(LOG_TAG, "--- Rows in myTable ---");

                    Cursor cursor = database.query(DB_TABLE_NAME, null, null, null, null, null, null);

                    if (cursor.moveToFirst()){

                        int idColIndex = cursor.getColumnIndex(DB_COLUMN_ID);
                        int nameColIndex = cursor.getColumnIndex(DB_COLUMN_NAME);
                        int emailColIndex = cursor.getColumnIndex(DB_COLUMN_EMAIL);

                        do {
                            Log.d(LOG_TAG,
                                    "ID = " + cursor.getInt(idColIndex) +
                                        ", name = " + cursor.getString(nameColIndex) +
                                        ", email = " + cursor.getString(emailColIndex));
                        } while (cursor.moveToNext());
                    } else {
                        Log.d(LOG_TAG, "0 rows");
                        cursor.close();
                    }
                    break;
                case R.id.btnClear:
                    Log.d(LOG_TAG, "--- Clear myTable ---");

                    int clearCount = database.delete(DB_TABLE_NAME, null, null);
                    Log.d(LOG_TAG, "deleted rows count = " + clearCount);
                    break;
                case R.id.btnUpd:
                    if (id.equalsIgnoreCase("")) {
                        break;
                    }
                    Log.d(LOG_TAG, "--- Update myTable: ---");
                    contentValues.put(DB_COLUMN_NAME, name);
                    contentValues.put(DB_COLUMN_EMAIL, email);
                    int updCount = database.update(DB_TABLE_NAME, contentValues, "id = ?",
                            new String[] { id });
                    Log.d(LOG_TAG, "updated rows count = " + updCount);
                    break;
                case R.id.btnDel:
                    if (id.equalsIgnoreCase("")){
                        break;
                    }
                    Log.d(LOG_TAG, "--- Delete from myTable ---");

                    int delCount = database.delete(DB_TABLE_NAME, "id = " + id , null);
                    Log.d(LOG_TAG, "deleted rows count = " + delCount);
                    break;
            }
        }
    }

    private class DBHelper extends SQLiteOpenHelper{

        final static String DB_NAME = "myDB";

        DBHelper(Context context) {
            super(context, DB_NAME, null, 1);
            getWritableDatabase();
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            Log.d(MainActivity.LOG_TAG, "--- onCreate database ---");

            sqLiteDatabase.execSQL("create table mytable ("
                    +"id integer primary key autoincrement,"
                    +"name text,"
                    +"email text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}
