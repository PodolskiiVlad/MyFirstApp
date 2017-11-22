package com.example.jsonresttest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;

public class PaperSourceArticlesActivity extends AppCompatActivity{

    public static final String INTENT_SOURCE_ID_KEY = "sourceName";
    public static final String INTENT_PRIMARY_KEY = "dbID";

    ArticlesAdapter adapter;
    ProgressBar progressBar;
    ListView articlesListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);

        articlesListView = findViewById(R.id.articleListView);
        progressBar = findViewById(R.id.artileListProgressBar);

        progressBar.setVisibility(View.VISIBLE);

        getResponse();
    }

    void getResponse(){
        ServerResponse response = new ServerResponse(this);
        final String dbID = getIntent().getStringExtra(INTENT_PRIMARY_KEY);
        if (response.isOnline()) {
            response.getArticles(new ArticlesCallback() {
                @Override
                public void onResult(final List <Article> articleList) {
                    adapter = makeAdapter(articleList);

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            articlesListView.setAdapter(adapter);
                            new PaperDatabase(PaperSourceArticlesActivity.this).updateArticleTable(articleList, dbID);
                            articlesListView.setOnItemClickListener(adapter.onItemClickListener);
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }, makeURLToRequest());
        } else {
            try {
                adapter = makeAdapter(new PaperDatabase(this).getArticleList(dbID));
                articlesListView.setAdapter(adapter);
                articlesListView.setOnItemClickListener(adapter.onItemClickListener);
                progressBar.setVisibility(View.INVISIBLE);
            } catch (NoDatabaseException ex){
                ex.printStackTrace();
            }
        }
    }

    ArticlesAdapter makeAdapter(List<Article> articleList){
        return new ArticlesAdapter(this, articleList);
    }

    private String makeURLToRequest(){
        String id = getIntent().getStringExtra(INTENT_SOURCE_ID_KEY);
        return "https://newsapi.org/v2/top-headlines?sources="
                    + id
                    + "&apiKey=d5aee91e5a984227a641457c7dd1ce50";
    }
}
