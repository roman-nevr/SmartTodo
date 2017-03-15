package org.berendeev.roma.smarttodo.domain.interactor;

import org.berendeev.roma.smarttodo.domain.Repository;
import org.berendeev.roma.smarttodo.domain.model.ToDoCategory;

import javax.inject.Inject;

import io.reactivex.Observable;

public class DeleteCategoryInteractor extends Interactor<Void, ToDoCategory> {

    @Inject Repository repository;

    @Inject
    public DeleteCategoryInteractor() {}

    @Override protected Observable<Void> buildObservable(ToDoCategory param) {
        return repository.deleteCategory(param).toObservable();
    }
}
