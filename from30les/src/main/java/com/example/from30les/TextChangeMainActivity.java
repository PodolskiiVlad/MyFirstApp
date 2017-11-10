package com.example.from30les;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class TextChangeMainActivity extends AppCompatActivity{

    TextView tv;

    final int REQUEST_CODE_COLOR = 1;
    final int REQUEST_CODE_ALIGN = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tvMainText);

        findViewById(R.id.btnColor).setOnClickListener(new OnClickListenerImpl(this));
        findViewById(R.id.btnAlignment).setOnClickListener(new OnClickListenerImpl(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK){
            super.onActivityResult(requestCode, resultCode, data);
            Toast.makeText(this, "Wrong result", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (requestCode){
            case REQUEST_CODE_COLOR:
                int color = data.getIntExtra(TextColorActivity.TEXT_COLOR_KEY, Color.WHITE);
                tv.setTextColor(color);
                break;
            case REQUEST_CODE_ALIGN:
                int align = data.getIntExtra(TextAlignActivity.TEXT_ALIGN_KEY, Gravity.LEFT);
                tv.setGravity(align);
                break;
        }
    }

    public final class OnClickListenerImpl implements View.OnClickListener{

        Context context;

        public OnClickListenerImpl(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            switch (view.getId()){
                case R.id.btnColor:
                    intent.setClass(context, TextColorActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_COLOR);
                    break;
                case R.id.btnAlignment:
                    intent.setClass(context, TextAlignActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_ALIGN);
                    break;
            }
        }
    }
}
