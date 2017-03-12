package org.berendeev.roma.smarttodo.di;

import android.content.Context;

import org.berendeev.roma.smarttodo.data.RepositoryImpl;
import org.berendeev.roma.smarttodo.data.datasource.DatabaseOpenHelper;
import org.berendeev.roma.smarttodo.data.datasource.SQLiteDatasource;
import org.berendeev.roma.smarttodo.domain.Repository;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

@Module
public class MainModule {

    private Context context;

    public MainModule(Context context) {
        this.context = context.getApplicationContext();
    }

    @Singleton
    @Provides
    Repository provideRepository(SQLiteDatasource datasource){
        return new RepositoryImpl(datasource);
    }

    @Singleton
    @Provides
    SQLiteDatasource provideSQLiteDatasource(DatabaseOpenHelper openHelper){
        return new SQLiteDatasource(openHelper);
    }

    @Singleton
    @Provides
    DatabaseOpenHelper provideDatabaseOpenHelper(Context context){
        return new DatabaseOpenHelper(context);
    }

    @Singleton
    @Provides
    Context provideContext(){
        return context;
    }

    @Singleton
    @Provides
    ThreadPoolExecutor provideThreadPoolExecutor(){
        int poolSize = 2;
        int maxPoolSize = 4;
        int timeout = 30;
        return new ThreadPoolExecutor(poolSize, maxPoolSize, timeout,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(poolSize));
    }

    @Singleton
    @Provides Scheduler provideScheduler(){
        return AndroidSchedulers.mainThread();
    }
}
