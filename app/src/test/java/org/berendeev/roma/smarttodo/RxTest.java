package org.berendeev.roma.smarttodo;

import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RxTest {
    @Test
    public void test() throws InterruptedException {
        try {
            Observable<Integer> observable = Observable.just(1 ,2 ,3);
            observable
                    .subscribe(integer -> {
                        int a = integer / 0;
                    });
        }catch (Throwable t){
            t.printStackTrace();
        }
        System.out.println(1);
    }

}
