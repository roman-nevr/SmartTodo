package org.berendeev.roma.smarttodo.domain.interactor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WorkInteractorExecutor implements InteractorExecutor {

    private ThreadPoolExecutor threadPoolExecutor;

    public static final int TIMEOUT = 30;

    private WorkInteractorExecutor(int poolSize, int maxPoolSize) {
        threadPoolExecutor = new ThreadPoolExecutor(poolSize, maxPoolSize, TIMEOUT,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(poolSize));
    }

    @Override public void execute(Runnable runnable) {
        threadPoolExecutor.execute(runnable);
    }

    private static InteractorExecutor instance;

    public static InteractorExecutor getInstance(){
        if (instance == null){
            instance = new WorkInteractorExecutor(2,4);
        }
        return instance;
    }
}
