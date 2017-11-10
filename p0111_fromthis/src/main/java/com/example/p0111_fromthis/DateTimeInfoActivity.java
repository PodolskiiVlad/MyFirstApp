package com.example.p0111_fromthis;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeInfoActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time_info);

        Intent intent = getIntent();
        String action = intent.getAction();

        String format = "";
        String info = "";

        if (action != null) {
            switch (action) {
                case "android.intent.action.showdate":
                    format = "HH:mm:ss";
                    info = "Time: ";
                    break;
                case "android.intent.action.showtime":
                    format = "dd:MM;yyyy";
                    info = "Date: ";
                    break;
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        String datetime = sdf.format(new Date(System.currentTimeMillis()));

        TextView tvDate = findViewById(R.id.tvInfo);
        tvDate.setText(info + datetime);

        findViewById(R.id.backInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
