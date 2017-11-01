package com.example.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button textView = findViewById(R.id.hello);
        textView.setText(String.valueOf(43));
        textView.getText();
        textView.setClickable(true);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "onClick: ");
            }
        });

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("text", textView.getText());
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
