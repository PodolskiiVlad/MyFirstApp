package com.example.from58lesdialog;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    Matcher m;
    Pattern p;
    String s;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog2);

        new NetworkUtil().getJsonAsync(new Callback() {
            @Override
            public void onResult(String jsonString) {
                s = jsonString;
                go();
            }
        });
    }

    void go(){
        Map <String, String> map = new HashMap<>();

        p = Pattern.compile("(?<name>data-name=\"[\\w ]*\")[ \\w\\s\\d\\W]*(?<url>https[^\"]*)");
        m = p.matcher(s);
        while(m.find()){
            Log.d("myLogs", "***************************\n" + "Red: " + m.group("name") + ", url: " + m.group("url"));
            String key = m.group("name");
            String value = m.group("url");
            Log.d("\nmyLogs", "" + m.group("url"));
            map.put(key, value);
        }
    }
}

