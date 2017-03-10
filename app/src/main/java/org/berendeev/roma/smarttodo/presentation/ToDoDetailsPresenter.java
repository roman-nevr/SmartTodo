package org.berendeev.roma.smarttodo.presentation;

import org.berendeev.roma.smarttodo.domain.interactor.GetToDoInteractor;

import javax.inject.Inject;

public class ToDoDetailsPresenter {
    @Inject GetToDoInteractor getToDoInteractor;

    private ToDoDetailsView view;
}
