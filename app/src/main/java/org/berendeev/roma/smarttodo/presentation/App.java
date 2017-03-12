package org.berendeev.roma.smarttodo.presentation;

import android.app.Application;

import org.berendeev.roma.smarttodo.BuildConfig;
import org.berendeev.roma.smarttodo.di.DaggerMainComponent;
import org.berendeev.roma.smarttodo.di.MainComponent;
import org.berendeev.roma.smarttodo.di.MainModule;

import timber.log.Timber;


public class App extends Application {
    private MainComponent mainComponent;

    public MainComponent getMainComponent() {
        return mainComponent;
    }

    private static App instance;

    @Override public void onCreate() {
        super.onCreate();
        instance = this;
        if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());
        initDI();
    }

    private void initDI() {
        mainComponent = DaggerMainComponent.builder().mainModule(new MainModule(getApplicationContext())).build();
    }

    public static App getApplication(){
        return instance;
    }
}
