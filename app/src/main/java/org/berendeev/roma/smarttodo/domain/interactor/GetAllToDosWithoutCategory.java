package org.berendeev.roma.smarttodo.domain.interactor;

import org.berendeev.roma.smarttodo.domain.Repository;
import org.berendeev.roma.smarttodo.domain.model.ToDo;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetAllToDosWithoutCategory extends Interactor<List<ToDo>, Void> {

    @Inject Repository repository;

    @Override protected Observable<List<ToDo>> buildObservable(Void param) {
        return repository.getAllFromCategory(0);
    }
}
