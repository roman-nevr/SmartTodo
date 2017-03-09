package org.berendeev.roma.smarttodo.presentation;

import org.berendeev.roma.smarttodo.domain.interactor.GetAllToDosWithoutCategory;
import org.berendeev.roma.smarttodo.domain.interactor.GetToDoInteractor;
import org.berendeev.roma.smarttodo.domain.model.ToDo;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class ToDoListPresenter {
    @Inject GetToDoInteractor getToDoInteractor;
    @Inject GetAllToDosWithoutCategory getAllToDosWithoutCategory;
    private final CompositeDisposable disposable;

    @Inject
    public ToDoListPresenter() {
        disposable = new CompositeDisposable();
    }

    public void start(){
        getAllToDosWithoutCategory.execute(new DisposableObserver<List<ToDo>>() {
            @Override public void onNext(List<ToDo> toDos) {

            }

            @Override public void onError(Throwable e) {

            }

            @Override public void onComplete() {

            }
        }, null);
    }

    public void stop(){
        disposable.clear();
    }
}
