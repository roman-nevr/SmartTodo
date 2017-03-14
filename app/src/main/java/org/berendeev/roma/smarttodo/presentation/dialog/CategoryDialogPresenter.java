package org.berendeev.roma.smarttodo.presentation.dialog;

import org.berendeev.roma.smarttodo.domain.interactor.SaveToDoCategoryInteractor;
import org.berendeev.roma.smarttodo.domain.interactor.VoidObserver;
import org.berendeev.roma.smarttodo.domain.model.ToDoCategory;

import javax.inject.Inject;

public class CategoryDialogPresenter {

    @Inject SaveToDoCategoryInteractor saveToDoCategoryInteractor;

    private CategoryDialogView view;

    @Inject
    public CategoryDialogPresenter() {}

    public void onConfirmClick() {
        saveToDoCategoryInteractor.execute(new VoidObserver(), buildToDoCategory());
    }

    public void start() {
    }

    private ToDoCategory buildToDoCategory(){
        return ToDoCategory.create(0, view.getCategoryName(), false);
    }

    public void setView(CategoryDialogView view) {
        this.view = view;
    }
}
