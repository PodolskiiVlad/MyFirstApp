package com.example.from30les;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MySimpleListView extends AppCompatActivity {

    private String[] names = { "Иван", "Марья", "Петр", "Антон", "Даша", "Борис",
            "Костя", "Игорь", "Анна", "Денис", "Андрей" };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_with_listview);

        ListView listView = (ListView) findViewById(R.id.lvMain);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.my_list_item, names);

        listView.setAdapter(adapter);
    }
}
