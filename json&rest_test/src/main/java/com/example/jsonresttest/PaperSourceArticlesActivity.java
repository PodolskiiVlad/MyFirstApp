package com.example.jsonresttest;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PaperSourceArticlesActivity extends AppCompatActivity {

    private static final String INTENT_SOURCE_ID_KEY = "sourceName";

    private ArticlesAdapter adapter;
    private ProgressBar progressBar;
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String url = ((Article) parent.getItemAtPosition(position)).getUrl();
            if (url == null) {
                Toast.makeText(PaperSourceArticlesActivity.this, "Url to article is missing", Toast.LENGTH_LONG).show();
                return;
            }
            if (url.equalsIgnoreCase("")) {
                return;
            }
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                PaperSourceArticlesActivity.this.startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                Toast.makeText(PaperSourceArticlesActivity.this,
                        "No application can handle this request."
                                + " Please install a web browser",
                        Toast.LENGTH_LONG).show();
                ex.printStackTrace();
            }
        }
    };

    public static void launch(Context context, String sourceIdKey) {
        Intent intent = new Intent(context, PaperSourceArticlesActivity.class);
        intent.putExtra(INTENT_SOURCE_ID_KEY, sourceIdKey);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);

        ListView articlesListView = findViewById(R.id.articleListView);
        progressBar = findViewById(R.id.artileListProgressBar);

        progressBar.setVisibility(View.VISIBLE);

        adapter = new ArticlesAdapter(this, new ArrayList<Article>());
        articlesListView.setAdapter(adapter);
        articlesListView.setOnItemClickListener(onItemClickListener);
        getResponse();
    }

    void getResponse() {
        final String sourceID = getIntent().getStringExtra(INTENT_SOURCE_ID_KEY);
        NetworkUtils response = new NetworkUtils();
        if (!response.isOnline(this)) {
            updateUi(sourceID);
            return;
        }

        response.getArticles(response.makeURLToRequest(getIntent(), INTENT_SOURCE_ID_KEY),
                new Callback<Article>() {
                    @Override
                    public void onResult(final List<Article> articleList) {
                        PaperDatabase database = PaperApplication.from(PaperSourceArticlesActivity.this).getDatabase();
                        database.updateArticleTable(articleList, sourceID);
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                updateUi(sourceID);
                            }
                        });
                    }
        });
    }

    void updateUi(String sourceID) {
        progressBar.setVisibility(View.INVISIBLE);
        PaperDatabase database = PaperApplication.from(this).getDatabase();
        List<Article> articleList = database.getArticleList(sourceID);
        adapter.clear();
        adapter.addAll(articleList);
    }
}
