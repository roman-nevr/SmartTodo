package org.berendeev.roma.smarttodo.presentation.presenter;

import org.berendeev.roma.smarttodo.domain.interactor.GetAllToDoCategoriesInteractor;
import org.berendeev.roma.smarttodo.domain.interactor.GetToDoInteractor;
import org.berendeev.roma.smarttodo.domain.interactor.SaveTodoInteractor;
import org.berendeev.roma.smarttodo.domain.interactor.UpdateToDoInteractor;
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
    @Inject GetAllToDoCategoriesInteractor getAllToDoCategoriesInteractor;
    @Inject UpdateToDoInteractor updateToDoInteractor;

    public static final int NEW_TODO = -1;

    private ToDoDetailsView view;
    private ToDoDetailsView.Router router;
    private int todoId;

    @Inject
    public ToDoDetailsPresenter() {
    }

    public void start(){
        getAllToDoCategoriesInteractor.execute(new GetToDoCategoriesObserver(), null);
        if (todoId != NEW_TODO){
            getToDoInteractor.execute(new GetToDoObserver(), todoId);
        }
    }

    public void stop(){
    }

    public void done() {
        //TODO: done action
        if (todoId == NEW_TODO){
            saveTodoInteractor.execute(new VoidObserver(), buildToDo());
        }else {
            updateToDoInteractor.execute(new VoidObserver(), buildToDo(todoId));
        }
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

    private class GetToDoCategoriesObserver extends DisposableObserver<List<ToDoCategory>>{
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

    private class GetToDoObserver extends DisposableObserver<ToDo>{

        @Override public void onNext(ToDo toDo) {
            view.fillView(toDo);
        }

        @Override public void onError(Throwable e) {

        }

        @Override public void onComplete() {
            this.dispose();
        }
    }

    private ToDo buildToDo(int todoId){
        return ToDo.builder()
                .name(view.getName())
                .description(view.getDescription())
                .categoryId(view.getCurrentCategoryId())
                .id(todoId)
                .isChecked(false)
                .build();
    }

    private ToDo buildToDo(){
        return buildToDo(0);
    }
}
