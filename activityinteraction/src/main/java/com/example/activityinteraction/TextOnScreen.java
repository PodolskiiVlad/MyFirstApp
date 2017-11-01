package com.example.activityinteraction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TextOnScreen extends AppCompatActivity {

    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_on_screen);

        text = findViewById(R.id.text);

        Intent intent = getIntent();

        text.setText(intent.getStringExtra("text"));
    }
}
