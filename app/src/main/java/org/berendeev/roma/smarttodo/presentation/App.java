package org.berendeev.roma.smarttodo.presentation;

import android.app.Application;

import com.facebook.stetho.Stetho;

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
        initStetho();
    }

    private void initDI() {
        mainComponent = DaggerMainComponent.builder().mainModule(new MainModule(getApplicationContext())).build();
    }

    public static App getApplication(){
        return instance;
    }

    private void initStetho(){
        // Create an InitializerBuilder
        Stetho.InitializerBuilder initializerBuilder =
                Stetho.newInitializerBuilder(this);

        // Enable Chrome DevTools
        initializerBuilder.enableWebKitInspector(
                Stetho.defaultInspectorModulesProvider(this)
        );

        // Enable command line interface
        initializerBuilder.enableDumpapp(
                Stetho.defaultDumperPluginsProvider(this)
        );

        // Use the InitializerBuilder to generate an Initializer
        Stetho.Initializer initializer = initializerBuilder.build();

        // Initialize Stetho with the Initializer
        Stetho.initialize(initializer);
    }
}
