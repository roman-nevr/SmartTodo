package org.berendeev.roma.smarttodo.domain.interactor;

import org.berendeev.roma.smarttodo.domain.Repository;
import org.berendeev.roma.smarttodo.domain.model.ToDo;

import javax.inject.Inject;

import io.reactivex.Observable;

public class UpdateToDoInteractor extends Interactor<Void, ToDo> {

    @Inject Repository repository;

    @Inject
    public UpdateToDoInteractor() {}

    @Override protected Observable<Void> buildObservable(ToDo param) {
        return repository.updateToDo(param).toObservable();
    }
}
