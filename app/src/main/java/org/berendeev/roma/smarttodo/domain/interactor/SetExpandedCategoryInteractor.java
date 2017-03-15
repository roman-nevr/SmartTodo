package org.berendeev.roma.smarttodo.domain.interactor;

import org.berendeev.roma.smarttodo.domain.Repository;
import org.berendeev.roma.smarttodo.domain.model.ToDoCategory;

import javax.inject.Inject;

import io.reactivex.Observable;

public class SetExpandedCategoryInteractor extends Interactor<Void, ToDoCategory> {

    @Inject Repository repository;

    @Inject
    public SetExpandedCategoryInteractor() {}

    @Override protected Observable<Void> buildObservable(ToDoCategory param) {
        return repository.getCategory(param.id()).flatMap(category ->
            repository.updateCategory(category.toBuilder().isExpanded(param.isExpanded()).build()).toObservable()
        );
    }
}
