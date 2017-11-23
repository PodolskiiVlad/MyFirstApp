package com.example.jsonresttest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class PaperMainActivity extends AppCompatActivity {

    private ListView sourcesListView;
    private SourcesAdapter adapter;
    private ProgressBar progressBar;
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            PaperSourceArticlesActivity.launch(
                    PaperMainActivity.this,
                    ((Source) parent.getItemAtPosition(position)).getId());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.mainProgressBar);
        sourcesListView = findViewById(R.id.sourcesListView);

        progressBar.setVisibility(View.VISIBLE);
        adapter = new SourcesAdapter(this, new ArrayList<Source>());
        sourcesListView.setAdapter(adapter);
        getResponse();
    }

    void getResponse() {
        NetworkUtils response = new NetworkUtils();
        if (!response.isOnline(this)) {
            updateUi();
            return;
        }

        response.getSources(new Callback<Source>() {
            @Override
            public void onResult(final List<Source> sourcesList) {
                PaperApplication.from(PaperMainActivity.this).getDatabase().updateSourceTable(sourcesList);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        updateUi();
                    }
                });
            }
        });
    }

    private void updateUi() {
        progressBar.setVisibility(View.INVISIBLE);
        List<Source> sourceList = PaperApplication.from(this).getDatabase().getSourceList();
        adapter.clear();
        adapter.addAll(sourceList);
        sourcesListView.setOnItemClickListener(onItemClickListener);
    }
}
