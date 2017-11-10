package com.example.activityinteraction;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TextOnScreen extends AppCompatActivity implements View.OnClickListener {

    private static final String TEXT_KEY = "text";

    TextView text;
    Button back;

    public static void startActivityWithText(@NonNull final Context context, @NonNull final String text) {
        Intent intent = new Intent(context, TextOnScreen.class);
        intent.putExtra(TEXT_KEY, text);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_on_screen);

        text = findViewById(R.id.text);
        back = findViewById(R.id.back);

        Intent intent = getIntent();

        text.setText(intent.getStringExtra(TEXT_KEY));
        OnClickListenerImpl listener = new OnClickListenerImpl();
        back.setOnClickListener(listener);
    }

    @Override
    public void onClick(View view) {
        setResult(RESULT_OK, new Intent());
        finish();
    }

    private final class OnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
            }
            onBackPressed();
        }
    }
}
