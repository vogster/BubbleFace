package com.unlogicon.bubbleface.di;

import android.content.Context;

import com.unlogicon.bubbleface.di.components.AppComponent;
import com.unlogicon.bubbleface.di.components.DaggerAppComponent;
import com.unlogicon.bubbleface.di.modules.AppModule;

public class Components {
    private AppComponent appComponent;

    public Components(Context context) {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(context))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
