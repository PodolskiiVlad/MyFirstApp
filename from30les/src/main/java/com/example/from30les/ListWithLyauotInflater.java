package com.example.from30les;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListWithLyauotInflater extends AppCompatActivity {

    private static final String LOG_TAG = "myLog";

    String[] name = { "Иван", "Марья", "Петр", "Антон", "Даша", "Борис",
            "Костя", "Игорь"};
    String[] position = { "Программер", "Бухгалтер", "Программер",
            "Программер", "Бухгалтер", "Директор", "Программер", "Охранник"};
    int salary[] = { 13000, 10000, 13000, 13000, 10000, 15000, 13000, 8000,
            13000, 10000, 13000, 13000, 10000, 15000, 13000, 8000};

    int[] colors = new int[2];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pleople_container);

        colors[0] = Color.parseColor("#559966CC");
        colors[1] = Color.parseColor("#55336699");

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linLayout);

        LayoutInflater layoutInflater = getLayoutInflater();
        for (int i = 0; i < name.length; i++) {
            Log.d(LOG_TAG, "i = " + i);
            View item = layoutInflater.inflate(R.layout.item, linearLayout, false);

            TextView tvName = (TextView) item.findViewById(R.id.tvName);
            tvName.setText(name[i]);

            TextView tvPosition = (TextView) item.findViewById(R.id.tvPosition);
            tvPosition.setText(getString(R.string.worker_position, position[i]));

            TextView tvSalary = (TextView) item.findViewById(R.id.tvSalary);
            String workerSalary = String.valueOf(salary[i]);
            tvSalary.setText(getString(R.string.worker_salary, workerSalary));

            item.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            item.setBackgroundColor(colors[i % 2]);
            linearLayout.addView(item);
        }
    }
}
