package com.example.p0111_fromthis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateExActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_ex);

        SimpleDateFormat sfd = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault());
        Date date = new Date(System.currentTimeMillis());
        String time = sfd.format(date);

        TextView tv = findViewById(R.id.tvDate);
        tv.setText(time);
    }
}
