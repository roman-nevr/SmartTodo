package org.berendeev.roma.smarttodo.domain.interactor;

import org.berendeev.roma.smarttodo.domain.Repository;
import org.berendeev.roma.smarttodo.domain.model.ToDo;

import javax.inject.Inject;

import io.reactivex.Observable;

public class SetCheckedToDoInteractor extends Interactor<Void, ToDo> {

    @Inject Repository repository;
    @Inject UpdateToDoInteractor updateToDoInteractor;

    @Inject
    public SetCheckedToDoInteractor() {}

    @Override protected Observable<Void> buildObservable(ToDo paramToDo) {
        return repository.getTodo(paramToDo.id())
                .flatMap(toDo -> repository.updateToDo(toDo).toObservable());
    }


}
