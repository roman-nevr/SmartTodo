package org.berendeev.roma.smarttodo.domain.interactor;

import org.berendeev.roma.smarttodo.domain.Repository;
import org.berendeev.roma.smarttodo.domain.model.ToDoCategory;

import javax.inject.Inject;

import io.reactivex.Observable;

public class SaveToDoCategoryInteractor extends Interactor<Void, ToDoCategory> {

    @Inject Repository repository;

    @Inject
    public SaveToDoCategoryInteractor() {}

    @Override protected Observable<Void> buildObservable(ToDoCategory category) {
        return repository.saveCategory(category).toObservable();
    }
}
