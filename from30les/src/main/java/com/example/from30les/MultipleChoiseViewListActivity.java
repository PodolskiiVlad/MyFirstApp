package com.example.from30les;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MultipleChoiseViewListActivity extends AppCompatActivity{


    private final static String LOG_TAG = "myLog";

    ListView listView;
    String[] names;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choise_viewlist_main);

        listView = (ListView) findViewById(R.id.lvMain2);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.names, android.R.layout.simple_list_item_single_choice);
        listView.setAdapter(adapter);

        findViewById(R.id.btnChecked).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG, "checked: " + names[listView.getCheckedItemPosition()]);
            }
        });

        names = getResources().getStringArray(R.array.names);
    }
}
