package com.example.text_on_activity_result;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int TEXT_CHANGE_INT_KEY = 0;

    private TextView textToChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textToChange = (TextView) findViewById(R.id.text);

        findViewById(R.id.change_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityToTakeResult();
            }
        });
    }

    private void openActivityToTakeResult() {
        startActivityForResult(new Intent(this, EditTextActivity.class), TEXT_CHANGE_INT_KEY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK && requestCode != TEXT_CHANGE_INT_KEY){
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
        String resultData = data.getStringExtra(EditTextActivity.TEXT_CHANGE_KEY);
        textToChange.setText(resultData);
    }
}
