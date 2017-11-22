package com.example.from58lesdialog;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NetworkUtil {

    private Executor executor = Executors.newSingleThreadExecutor();
    void getJsonAsync(final Callback callback){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                String jsonString = getText();
                if(callback != null){
                    callback.onResult(jsonString);
                }
            }
        });
    }

    String getText(){
        String result = "";
        HttpURLConnection connection = null;
        URL targ;
        BufferedReader br;

        try {
            targ = new URL("https://newsapi.org/sources");
            connection = (HttpURLConnection) targ.openConnection();
            connection.setRequestMethod("GET");

            InputStream is = connection.getInputStream();
            StringBuilder sb = new StringBuilder();

            br = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = br.readLine()) != null){
                sb.append(line);
            }

            result = sb.toString();
            Log.d("\nmyLogs", result);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return result;
    }
}
