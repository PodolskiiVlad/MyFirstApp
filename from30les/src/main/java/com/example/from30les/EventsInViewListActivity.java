package com.example.from30les;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EventsInViewListActivity extends AppCompatActivity{

    private static final String LOG_TAG = "meLog";

    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_in_viewlist);

        listView = (ListView) findViewById(R.id.lvEventsInViewList);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.names,
                android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(LOG_TAG, "itemClick: position = " + i + ", id = "
                        + l);
            }
        });

        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(LOG_TAG, "itemSelect: position = " + i + ", id = "
                        + l);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.d(LOG_TAG, "itemSelect: nothing");
            }
        });
    }
}
