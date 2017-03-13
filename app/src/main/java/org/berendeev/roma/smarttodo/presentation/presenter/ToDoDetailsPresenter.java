package org.berendeev.roma.smarttodo.presentation.presenter;

import org.berendeev.roma.smarttodo.domain.interactor.GetToDoInteractor;
import org.berendeev.roma.smarttodo.domain.interactor.SaveTodoInteractor;
import org.berendeev.roma.smarttodo.domain.model.ToDo;
import org.berendeev.roma.smarttodo.presentation.ToDoDetailsView;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

public class ToDoDetailsPresenter {
    @Inject GetToDoInteractor getToDoInteractor;
    @Inject SaveTodoInteractor saveTodoInteractor;

    public static final int NEW_TODO = -1;

    private ToDoDetailsView view;
    private ToDoDetailsView.Router router;
    private int todoId;

    @Inject
    public ToDoDetailsPresenter() {}

    public void start(){
        if (todoId != NEW_TODO){
            getToDoInteractor.execute(new GetToDoObservable(), todoId);
        }
    }

    public void stop(){
        getToDoInteractor.dispose();
    }

    public void done() {
        //TODO: done action
        ToDo toDo = ToDo.builder()
                .name(view.getName())
                .description(view.getDescription())
                .categoryId(0)
                .id(0)
                .build();
        saveTodoInteractor.execute(new SaveToDoObservable(), toDo);
        router.moveToToDoList();
    }

    public void setView(ToDoDetailsView view) {
        this.view = view;
    }

    public void setTodoId(int todoId) {
        this.todoId = todoId;
    }

    private class GetToDoObservable extends DisposableObserver<ToDo>{
        @Override public void onNext(ToDo toDo) {
            view.fillView(toDo);
        }

        @Override public void onError(Throwable e) {
            view.showError();
        }

        @Override public void onComplete() {
        }
    }

    private class SaveToDoObservable extends DisposableObserver<Void>{
        @Override public void onNext(Void aVoid) {

        }

        @Override public void onError(Throwable e) {
            view.showError();
        }

        @Override public void onComplete() {

        }
    }
}
