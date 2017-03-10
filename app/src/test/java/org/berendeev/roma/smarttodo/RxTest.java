package org.berendeev.roma.smarttodo;

import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RxTest {
    @Test
    public void test() throws InterruptedException {
        try {
            Observable<Integer> observable = Observable.just(1 ,2 ,3);
//            observable
//                    .subscribe(integer -> {
//                        int a = integer / 0;
//                    });

            Subscriber<Integer> subscriber = new Subscriber<Integer>() {
                @Override public void onSubscribe(Subscription s) {

                }

                @Override public void onNext(Integer integer) {

                }

                @Override public void onError(Throwable t) {

                }

                @Override public void onComplete() {

                }
            };

            Observer<Integer> observer = new Observer<Integer>() {
                @Override public void onSubscribe(Disposable d) {

                }

                @Override public void onNext(Integer integer) {

                }

                @Override public void onError(Throwable e) {

                }

                @Override public void onComplete() {

                }
            };

            Disposable disposable = new Disposable() {
                @Override public void dispose() {

                }

                @Override public boolean isDisposed() {
                    return false;
                }
            };

            Subscription subscription = new Subscription() {
                @Override public void request(long n) {

                }

                @Override public void cancel() {

                }
            };

        }catch (Throwable t){
            t.printStackTrace();
        }
        System.out.println(1);
    }

}
