package com.example.cookou;

import android.app.Application;
import android.content.Context;

public class CookouApp extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        // ...
    }

    public static Context getAppContext() {
        return context;
    }
}
