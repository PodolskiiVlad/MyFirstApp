package com.example.jsonresttest;

import android.app.Application;
import android.content.Context;

public final class PaperApplication extends Application {

    private PaperDatabase database;

    public static PaperApplication from(Context context) {
        return ((PaperApplication) context.getApplicationContext());
    }

    @Override
    public void onCreate() {
        super.onCreate();

        database = new PaperDatabase(this);
    }

    public PaperDatabase getDatabase() {
        return database;
    }
}
