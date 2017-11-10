package com.example.activityinteraction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int TAKE_PHOTO_REQUEST_CODE = 100;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editable_text);
        findViewById(R.id.button).setOnClickListener(this);

        startActivityForResult(new Intent(this, TextOnScreen.class), TAKE_PHOTO_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode != TAKE_PHOTO_REQUEST_CODE) {
            return;
        }

    }

    @Override
    public void onClick(View view) {
        TextOnScreen.startActivityWithText(this, editText.getText().toString());
    }
}
