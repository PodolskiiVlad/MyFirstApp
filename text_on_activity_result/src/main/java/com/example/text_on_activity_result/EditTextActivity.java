package com.example.text_on_activity_result;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditTextActivity extends AppCompatActivity {

    public static final String TEXT_CHANGE_KEY = "text";
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
        editText = (EditText) findViewById(R.id.text_to_edit);

        findViewById(R.id.button_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDataBack();
            }
        });
    }

    private void sendDataBack() {
        Intent data = new Intent();
        String result = editText.getText().toString();
        data.putExtra(TEXT_CHANGE_KEY, result);
        setResult(RESULT_OK, data);
        finish();
    }
}
