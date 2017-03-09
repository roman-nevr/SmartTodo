package org.berendeev.roma.smarttodo.domain.interactor;

import org.berendeev.roma.smarttodo.domain.Repository;
import org.berendeev.roma.smarttodo.domain.model.ToDo;

import javax.inject.Inject;

import io.reactivex.Observable;

public class SaveTodoInteractor extends Interactor<Void, ToDo> {

    @Inject Repository repository;

    @Override protected Observable<Void> buildObservable(ToDo param) {
        return repository.saveToDo(param);
    }
}
