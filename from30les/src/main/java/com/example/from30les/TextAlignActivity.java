package com.example.from30les;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;


public class TextAlignActivity extends AppCompatActivity {

    public static final String TEXT_ALIGN_KEY = "alignment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_align);

        findViewById(R.id.btnLeft).setOnClickListener(new OnClickListenerImpl());
        findViewById(R.id.btnCenter).setOnClickListener(new OnClickListenerImpl());
        findViewById(R.id.btnRight).setOnClickListener(new OnClickListenerImpl());
    }

    public final class OnClickListenerImpl implements View.OnClickListener{
        Intent intent = new Intent();
        @Override
        public void onClick(View view) {
           switch (view.getId()){
               case R.id.btnLeft:
                   intent.putExtra(TEXT_ALIGN_KEY, Gravity.LEFT);
                   break;
               case R.id.btnCenter:
                   intent.putExtra(TEXT_ALIGN_KEY, Gravity.LEFT);
                   break;
               case R.id.btnRight:
                   intent.putExtra(TEXT_ALIGN_KEY, Gravity.LEFT);
                   break;
           }
           setResult(RESULT_OK, intent);
           finish();
        }
    }
}
