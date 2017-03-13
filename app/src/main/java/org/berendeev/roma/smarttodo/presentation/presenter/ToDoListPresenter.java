package org.berendeev.roma.smarttodo.presentation.presenter;

import org.berendeev.roma.smarttodo.domain.interactor.GetAllToDosWithoutCategory;
import org.berendeev.roma.smarttodo.domain.interactor.GetToDoInteractor;
import org.berendeev.roma.smarttodo.domain.model.ToDo;
import org.berendeev.roma.smarttodo.presentation.ToDoListView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class ToDoListPresenter {
    @Inject GetToDoInteractor getToDoInteractor;
    @Inject GetAllToDosWithoutCategory getAllToDosWithoutCategory;
    private final CompositeDisposable disposable;

    private ToDoListView view;
    private ToDoListView.Router router;

    @Inject
    public ToDoListPresenter() {
        disposable = new CompositeDisposable();
    }

    public void start(){
        getAllToDosWithoutCategory.execute(new DisposableObserver<List<ToDo>>() {
            @Override public void onNext(List<ToDo> toDos) {
                view.showToDos(toDos);
            }

            @Override public void onError(Throwable e) {
                view.showError();
            }

            @Override public void onComplete() {

            }
        }, null);
    }

    public void stop(){
        disposable.clear();
    }

    public void onToDoClick(int id) {
        router.moveToToDoDetails(id);
    }

    public void onAddButtonClick(){
        router.moveToAddNewToDo();
    }

    public void setView(ToDoListView view) {
        this.view = view;
    }

    public void setRouter(ToDoListView.Router router) {
        this.router = router;
    }

    public void onCreateCategoryClick() {

    }

    public void clearDoneTasks() {

    }
}
