package org.berendeev.roma.smarttodo.presentation.presenter;

import org.berendeev.roma.smarttodo.domain.interactor.GetToDoCategoriesInteractor;
import org.berendeev.roma.smarttodo.domain.interactor.GetToDoInteractor;
import org.berendeev.roma.smarttodo.domain.interactor.SaveTodoInteractor;
import org.berendeev.roma.smarttodo.domain.interactor.VoidObserver;
import org.berendeev.roma.smarttodo.domain.model.ToDo;
import org.berendeev.roma.smarttodo.domain.model.ToDoCategory;
import org.berendeev.roma.smarttodo.presentation.ToDoDetailsView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class ToDoDetailsPresenter {
    @Inject GetToDoInteractor getToDoInteractor;
    @Inject SaveTodoInteractor saveTodoInteractor;
    @Inject GetToDoCategoriesInteractor getToDoCategoriesInteractor;

    public static final int NEW_TODO = -1;

    private ToDoDetailsView view;
    private ToDoDetailsView.Router router;
    private int todoId;
    private final CompositeDisposable disposable;

    @Inject
    public ToDoDetailsPresenter() {
        disposable = new CompositeDisposable();
    }

    public void start(){
        getToDoCategoriesInteractor.execute(new GetToDoCategoriesObservable(), null);
    }

    public void stop(){
        disposable.clear();
    }

    public void done() {
        //TODO: done action
        ToDo toDo = ToDo.builder()
                .name(view.getName())
                .description(view.getDescription())
                .categoryId(view.getCurrentCategoryId())
                .id(0)
                .isChecked(false)
                .build();
        saveTodoInteractor.execute(new VoidObserver(), toDo);
        router.moveToToDoList();
    }

    public void setView(ToDoDetailsView view) {
        this.view = view;
    }

    public void setTodoId(int todoId) {
        this.todoId = todoId;
    }

    public void setRouter(ToDoDetailsView.Router router) {
        this.router = router;
    }

    private class GetToDoObservable extends DisposableObserver<ToDo>{
        @Override public void onNext(ToDo toDo) {
            view.fillView(toDo);
            view.setCurrentCategory(ToDoCategory.create(toDo.categoryId(), "", false));
        }

        @Override public void onError(Throwable e) {
            view.showError();
        }

        @Override public void onComplete() {
            getToDoCategoriesInteractor.dispose();
            if (todoId != NEW_TODO){
                getToDoInteractor.execute(new GetToDoObservable(), todoId);
            }
        }
    }

    private class GetToDoCategoriesObservable extends DisposableObserver<List<ToDoCategory>>{
        @Override public void onNext(List<ToDoCategory> categories) {
            view.setCategories(categories);
        }

        @Override public void onError(Throwable e) {
            view.showError();
        }

        @Override public void onComplete() {
            this.dispose();
        }
    }
}
