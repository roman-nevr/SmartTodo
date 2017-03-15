package org.berendeev.roma.smarttodo.domain.interactor;

import org.berendeev.roma.smarttodo.domain.Repository;
import org.berendeev.roma.smarttodo.domain.model.ToDoCategory;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetToDoCategoryInteractor extends Interactor<ToDoCategory, Integer> {

    @Inject Repository repository;

    @Inject
    public GetToDoCategoryInteractor() {}

    @Override protected Observable<ToDoCategory> buildObservable(Integer param) {
        return repository.getCategory(param);
    }
}
