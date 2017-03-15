package org.berendeev.roma.smarttodo.domain.interactor;

import org.berendeev.roma.smarttodo.domain.Repository;
import org.berendeev.roma.smarttodo.domain.model.ToDoCategory;

import javax.inject.Inject;

import io.reactivex.Observable;

public class UpdateToDoCategoryInteractor extends Interactor<Void, ToDoCategory> {

    @Inject Repository repository;

    @Inject
    public UpdateToDoCategoryInteractor() {}

    @Override protected Observable<Void> buildObservable(ToDoCategory param) {
        return repository.updateCategory(param).toObservable();
    }
}
