package com.example.p0111_fromthis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

public class MainActivity extends AppCompatActivity implements SearchView.OnClickListener{

    Button btnTime;
    Button btnDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.les_26_main);

        btnTime = findViewById(R.id.btnTime);
        btnDate = findViewById(R.id.btnDate);

        btnTime.setOnClickListener(this);
        btnDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();

        switch (v.getId()){
            case R.id.btnTime:
                intent.setAction("android.intent.action.showtime");
                startActivity(intent);
                break;
            case R.id.btnDate:
                intent.setAction("android.intent.action.showdate");
                startActivity(intent);
                break;
        }
    }
}
