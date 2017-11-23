package com.example.jsonresttest;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

class NetworkUtils {

    private static final String SOURCES_URL = "https://newsapi.org/v2/sources?apiKey=d5aee91e5a984227a641457c7dd1ce50";

    private static final String REQUEST_METHOD_GET = "GET";

    private Executor executor = Executors.newSingleThreadExecutor();
    private final Gson gson;

    NetworkUtils() {
        gson = new Gson();
    }

    private String getServerResponse(String url) {
        String jsonResult = "";
        HttpURLConnection httpURLConnection = null;
        URL target;
        BufferedReader bufferedReader = null;

        try {
            target = new URL(url);

            httpURLConnection = (HttpURLConnection) target.openConnection();
            httpURLConnection.setRequestMethod(REQUEST_METHOD_GET);
            httpURLConnection.connect();

            InputStream inputStream = httpURLConnection.getInputStream();
            StringBuilder stringBuffer = new StringBuilder();

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }

            jsonResult = stringBuffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }

            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                }
            }
        }
        return jsonResult;
    }

    void getSources(final Callback<Source> sourcesCallback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                String response = getServerResponse(SOURCES_URL);
                if (sourcesCallback != null) {
                    SourcesList sourcesList = gson.fromJson(response, SourcesList.class);
                    sourcesCallback.onResult(sourcesList.getSources());
                }
            }
        });
    }

    void getArticles(final String requestURL, final Callback<Article> callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                String response = getServerResponse(requestURL);
                if (callback != null) {
                    ArticleList articleList = gson.fromJson(response, ArticleList.class);
                    callback.onResult(articleList.getArticlesList());
                }
            }
        });
    }

    boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        }

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    String makeURLToRequest(Intent intent, String intentKey){
        String id = intent.getStringExtra(intentKey);
        return "https://newsapi.org/v2/top-headlines?sources="
                + id
                + "&apiKey=d5aee91e5a984227a641457c7dd1ce50";
    }
}
