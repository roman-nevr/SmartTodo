package org.berendeev.roma.smarttodo;

import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.Cancellable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class RxTest {
    @Test
    public void test() throws InterruptedException {
        try {
            Observable<Integer> observable = Observable.just(1, 2, 3);
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

        } catch (Throwable t) {
            t.printStackTrace();
        }
        System.out.println(1);
    }

    @Test
    public void test2() {
        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            Subscription subscription;

            @Override public void onSubscribe(Subscription s) {
                subscription = s;
            }

            @Override public void onNext(Integer integer) {
                if (integer == 2){
                    subscription.cancel();
                }
            }

            @Override public void onError(Throwable t) {

            }

            @Override public void onComplete() {
            }
        };
        Observable<Integer> observable = Observable.create(e -> {
            e.setDisposable(Disposables.fromAction(this::doSmth));
            e.setCancellable(new Cancellable() {
                @Override public void cancel() throws Exception {
                    int a = 1;
                }
            });
            e.onNext(2);
        });

        Flowable<Integer> flowable = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                e.onNext(2);
            }
        }, BackpressureStrategy.BUFFER);

        flowable.subscribeWith(subscriber);
        flowable.subscribe(integer -> {
            System.out.println(integer);
        });

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

//        observable.subscribeWith(observer);
        Subscription subscription = new Subscription() {
            @Override public void request(long n) {

            }

            @Override public void cancel() {

            }
        };

//        Disposable disposable = observable.subscribe(integer -> {
//            System.out.println(integer);
//        });
        //disposable.dispose();

    }

    private void doSmth() {
        int a = 0;
    }

    @Test
    public void rxTest(){
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    if(Thread.currentThread().isInterrupted()){
                        Timber.d("interupted");
                    }
                }
                if (emitter.isDisposed()){
                    System.out.println("disposed");
                }
                emitter.onNext(2);
            }
        })
        .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.computation());

        Disposable disposable = observable.subscribe(integer -> {
            System.out.println(integer);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        disposable.dispose();
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
