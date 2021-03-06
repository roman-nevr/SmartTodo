package org.berendeev.roma.smarttodo.domain.interactor;

import org.berendeev.roma.smarttodo.domain.Repository;
import org.berendeev.roma.smarttodo.domain.model.ToDo;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetAllFromCategoryInteractor extends Interactor<List<ToDo>, Integer> {

    @Inject Repository repository;

    @Inject
    public GetAllFromCategoryInteractor() {}

    @Override protected Observable<List<ToDo>> buildObservable(Integer param) {
        return Observable.create(e -> {
            repository.getAllFromCategory(param);
        });
    }
}
