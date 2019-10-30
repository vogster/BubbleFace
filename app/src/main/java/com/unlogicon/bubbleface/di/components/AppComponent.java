package com.unlogicon.bubbleface.di.components;

import com.unlogicon.bubbleface.di.modules.AppModule;
import com.unlogicon.bubbleface.di.modules.NetworkModule;
import com.unlogicon.bubbleface.presenters.MainPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, NetworkModule.class})
@Singleton
public interface AppComponent {
    void inject(MainPresenter mainPresenter);
}
