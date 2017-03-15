package org.berendeev.roma.smarttodo.domain.interactor;

import org.berendeev.roma.smarttodo.domain.Repository;
import org.berendeev.roma.smarttodo.domain.model.ToDoCategory;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetAllToDoCategoriesInteractor extends Interactor<List<ToDoCategory>, Void> {

    @Inject Repository repository;

    @Inject
    public GetAllToDoCategoriesInteractor() {}

    @Override protected Observable<List<ToDoCategory>> buildObservable(Void param) {
        return repository.getAllCategories();
    }
}
