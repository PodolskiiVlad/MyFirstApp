package com.example.from30les;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SimpleAdapterActivity extends AppCompatActivity{

    private static final String ATTRIBUTE_NAME_TEXT = "text";
    private static final String ATTRIBUTE_NAME_CHECKED = "checked";
    private static final String ATTRIBUTE_NAME_IMAGE = "image";

    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_adapter_main);

        String[] texts = {"sometext1", "sometext2", "sometext3", "sometext4", "sometext5", };
        boolean[] checked = {true, false, true, false, true};
        int img = R.drawable.ic_launcher_background;

        ArrayList<Map<String, Object>> data = new ArrayList<>(texts.length);
        Map<String, Object> map;

        for (int i = 0; i < texts.length - 1; i++) {
            map = new HashMap<>();
            map.put(ATTRIBUTE_NAME_TEXT, texts[i]);
            map.put(ATTRIBUTE_NAME_CHECKED, checked[i]);
            map.put(ATTRIBUTE_NAME_IMAGE, img);
            data.add(map);
        }

        String[] from = { ATTRIBUTE_NAME_TEXT, ATTRIBUTE_NAME_CHECKED, ATTRIBUTE_NAME_IMAGE };
        int[] to = { R.id.list_item_tvText, R.id.cbChecked, R.id.ivImg };

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.list_item, from, to);

        listView = findViewById(R.id.lvSimple);
        listView.setAdapter(simpleAdapter);
    }
}
