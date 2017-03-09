package org.berendeev.roma.smarttodo.domain.interactor;

import java.util.concurrent.ThreadPoolExecutor;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class Interactor<T, Param> {

    private ThreadPoolExecutor workExecutor;
    private Scheduler mainExecutor;
    private CompositeDisposable disposable;

    public Interactor(ThreadPoolExecutor workExecutor, Scheduler mainExecutor) {
        this.workExecutor = workExecutor;
        this.mainExecutor = mainExecutor;
        disposable = new CompositeDisposable();
    }

    public void execute(DisposableObserver<T> observer, Param param){
        disposable.add(
                buildObservable(param)
                .subscribeOn(Schedulers.from(workExecutor))
                .observeOn(mainExecutor)
                .subscribeWith(observer));
    }

    protected abstract Observable<T> buildObservable(Param param);

    public void dispose(){
        disposable.clear();
    }
}
