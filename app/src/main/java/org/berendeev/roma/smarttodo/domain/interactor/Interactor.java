package org.berendeev.roma.smarttodo.domain.interactor;

import java.util.concurrent.ThreadPoolExecutor;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class Interactor<Response, Request> {

    @Inject ThreadPoolExecutor workExecutor;
    @Inject Scheduler mainExecutor;

    public Disposable execute(DisposableObserver<Response> observer, Request param) {
        return buildObservable(param)
                .subscribeOn(Schedulers.from(workExecutor))
                .observeOn(mainExecutor)
                .subscribeWith(observer);
    }

    protected abstract Observable<Response> buildObservable(Request param);
}
