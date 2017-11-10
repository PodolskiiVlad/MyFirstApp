package com.example.from30les;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class TextColorActivity extends AppCompatActivity{

    public static final String TEXT_COLOR_KEY = "color";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_color);

        findViewById(R.id.btnRed).setOnClickListener(new OnClickListenerImpl());
        findViewById(R.id.btnGreen).setOnClickListener(new OnClickListenerImpl());
        findViewById(R.id.btnBlue).setOnClickListener(new OnClickListenerImpl());
    }

    public final class OnClickListenerImpl implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            switch (view.getId()){
                case R.id.btnRed:
                    intent.putExtra(TEXT_COLOR_KEY, Color.RED);
                    break;
                case R.id.btnGreen:
                    intent.putExtra(TEXT_COLOR_KEY, Color.GREEN);
                    break;
                case R.id.btnBlue:
                    intent.putExtra(TEXT_COLOR_KEY, Color.BLUE);
                    break;
            }
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
