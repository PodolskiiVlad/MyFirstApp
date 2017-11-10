package com.example.from30les;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SimpleAdapterSettersActivity extends AppCompatActivity{

    private static final String ATTRIBUTE_NAME_TEXT = "text";
    private static final String ATTRIBUTE_NAME_VALUE = "value";
    private static final String ATTRIBUTE_NAME_IMAGE = "image";

    public static final int POSITIVE = android.R.drawable.arrow_up_float;
    public static final int NEGATIVE = android.R.drawable.arrow_down_float;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_adapter_with_setters);

        int [] values = { 8, 4, -3, 2, -5, 0, 3, -6, 1, -1 };

        ArrayList<Map<String, Object>> data = new ArrayList<>(
                values.length);
        Map<String, Object> m;
        int img;
        for (int i = 0; i < values.length; i++) {
            m = new HashMap<String, Object>();
            m.put(ATTRIBUTE_NAME_TEXT, "Day " + (i + 1));
            m.put(ATTRIBUTE_NAME_VALUE, values[i]);
            if (values[i] == 0) img = 0; else
                img = (values[i] > 0) ? POSITIVE : NEGATIVE;
            m.put(ATTRIBUTE_NAME_IMAGE, img);
            data.add(m);
        }

        // массив имен атрибутов, из которых будут читаться данные
        String[] from = { ATTRIBUTE_NAME_TEXT, ATTRIBUTE_NAME_VALUE,
                ATTRIBUTE_NAME_IMAGE };
        // массив ID View-компонентов, в которые будут вставлять данные
        int[] to = { R.id.tvAdapterText, R.id.tvValue, R.id.ivImg1 };

        // создаем адаптер
        MySimpleAdapter sAdapter = new MySimpleAdapter(this, data, R.layout.activity_simple_adapter_with_setters, from, to);

        // определяем список и присваиваем ему адаптер
        listView = findViewById(R.id.lvSmplAdWithStrs);
        listView.setAdapter(sAdapter);
    }
}
