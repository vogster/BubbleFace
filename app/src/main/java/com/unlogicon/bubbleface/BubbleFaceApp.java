package com.unlogicon.bubbleface;

import android.app.Application;

import com.unlogicon.bubbleface.di.Components;

public class BubbleFaceApp extends Application {
    public static BubbleFaceApp instance;

    private Components components;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        components = new Components(getApplicationContext());

    }

    public static BubbleFaceApp getInstance() {
        return instance;
    }

    public Components getComponents() {
        return components;
    }
}
