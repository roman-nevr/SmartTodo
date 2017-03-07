package org.berendeev.roma.smarttodo.domain.interactor;


import android.os.Handler;
import android.os.Looper;

public class MainThreadInteractorExecutor implements InteractorExecutor {

    private Handler mainThreadHandler;

    public MainThreadInteractorExecutor() {
        this.mainThreadHandler = new Handler(Looper.getMainLooper());
    }

    @Override public void execute(Runnable runnable) {
        mainThreadHandler.post(runnable);
    }

    private static InteractorExecutor instance;

    public static InteractorExecutor getInstance(){
        if (instance == null){
            instance = new MainThreadInteractorExecutor();
        }
        return instance;
    }
}
