package com.example.jsonresttest;

import android.content.Context;
import android.net.ConnectivityManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

class ServerResponse{

    private static final String SOURCES_URL = "https://newsapi.org/v2/sources?apiKey=d5aee91e5a984227a641457c7dd1ce50";
    private static final int SOURCES_GSON_KEY = 1;
    private static final int ARTICLES_GSON_KEY = 2;

    private static final String REQUEST_METHOD_GET = "GET";
    private Context context;

    private Executor executor = Executors.newSingleThreadExecutor();

    ServerResponse(Context context) {
        this.context = context;
    }

    private String getServerResponse(String url){
        String jsonResult = "";
        HttpURLConnection httpURLConnection = null;
        URL target;
        BufferedReader bufferedReader;

        try {
            target = new URL(url);

            httpURLConnection = (HttpURLConnection) target.openConnection();
            httpURLConnection.setRequestMethod(REQUEST_METHOD_GET);
            httpURLConnection.connect();

            InputStream inputStream = httpURLConnection.getInputStream();
            StringBuilder stringBuffer = new StringBuilder();

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = bufferedReader.readLine()) != null){
                stringBuffer.append(line);
            }

            jsonResult = stringBuffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return jsonResult;
    }

    private Gson getGson(int key){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        switch (key){
            case SOURCES_GSON_KEY:
                break;
            case ARTICLES_GSON_KEY:
                break;
        }
        return gson;
    }

    void getSources (final SourcesCallback sourcesCallback) {
        executor.execute(new Runnable() {
            @Override
            public void run(){
                String response = getServerResponse(SOURCES_URL);
                Gson gson = getGson(SOURCES_GSON_KEY);
                if (sourcesCallback != null) {
                    SourcesList sourcesList = gson.fromJson(response, SourcesList.class);
                    sourcesCallback.onResult(sourcesList.getSources());
                }
            }
        });
    }

    void getArticles(final ArticlesCallback articlesCallback, final String requestURL){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                String response = getServerResponse(requestURL);
                Gson gson= getGson(ARTICLES_GSON_KEY);
                if (articlesCallback != null){
                    ArticleList articleList = gson.fromJson(response, ArticleList.class);
                    articlesCallback.onResult(articleList.getArticlesList());
                }
            }
        });
    }

    boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }
}
