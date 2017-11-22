package com.example.jsonresttest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

public class PaperMainActivity extends AppCompatActivity {

    private ListView sourcesListView;
    private SourcesAdapter adapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.mainProgressBar);
        sourcesListView = findViewById(R.id.sourcesListView);

        progressBar.setVisibility(View.VISIBLE);

        getResponse();
    }

    void getResponse() {
        ServerResponse response = new ServerResponse(this);
        if (response.isOnline()) {
            response.getSources(new SourcesCallback() {
                @Override
                public void onResult(final List<Source> sourcesList) {
                    PaperDatabase database = new PaperDatabase(PaperMainActivity.this, sourcesList);
                    try {
                        adapter = makeAdapter(database.getSourceList());
                    } catch (NoDatabaseException e) {
                        e.printStackTrace();
                    }
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.INVISIBLE);
                            sourcesListView.setAdapter(adapter);

                            sourcesListView.setOnItemClickListener(adapter.onItemClickListener);
                        }
                    });
                }
            });
        } else {
            try {
                adapter = makeAdapter(new PaperDatabase(this).getSourceList());
                progressBar.setVisibility(View.INVISIBLE);
                sourcesListView.setAdapter(adapter);
                sourcesListView.setOnItemClickListener(adapter.onItemClickListener);
            } catch (NoDatabaseException e) {
                Toast.makeText(this, "No data found. Please connect the internet", Toast.LENGTH_SHORT).show();
            }
        }
    }

    SourcesAdapter makeAdapter(List<Source> sourceList) {
        return new SourcesAdapter(this, sourceList);
    }
}
