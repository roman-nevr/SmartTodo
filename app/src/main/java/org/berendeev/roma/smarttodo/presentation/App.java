package org.berendeev.roma.smarttodo.presentation;

import android.app.Application;

import org.berendeev.roma.smarttodo.di.DaggerMainComponent;
import org.berendeev.roma.smarttodo.di.MainComponent;
import org.berendeev.roma.smarttodo.di.MainModule;


public class App extends Application {
    private MainComponent mainComponent;

    public MainComponent getMainComponent() {
        return mainComponent;
    }

    @Override public void onCreate() {
        super.onCreate();
        initDI();
    }

    private void initDI() {
        mainComponent = DaggerMainComponent.builder().mainModule(new MainModule(getApplicationContext())).build();
    }
}
