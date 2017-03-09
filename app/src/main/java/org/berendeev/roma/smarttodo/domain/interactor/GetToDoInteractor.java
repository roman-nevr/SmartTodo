package org.berendeev.roma.smarttodo.domain.interactor;

import org.berendeev.roma.smarttodo.domain.Repository;
import org.berendeev.roma.smarttodo.domain.model.ToDo;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetToDoInteractor extends Interactor<ToDo, Integer> {

    @Inject Repository repository;

    @Override protected Observable<ToDo> buildObservable(Integer param) {
        return repository.getTodo(param);
    }
}
