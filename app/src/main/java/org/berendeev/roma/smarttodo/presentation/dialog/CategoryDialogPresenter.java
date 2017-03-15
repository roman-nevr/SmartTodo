package org.berendeev.roma.smarttodo.presentation.dialog;

import org.berendeev.roma.smarttodo.domain.interactor.DeleteCategoryInteractor;
import org.berendeev.roma.smarttodo.domain.interactor.GetAllToDoCategoriesInteractor;
import org.berendeev.roma.smarttodo.domain.interactor.GetToDoCategoryInteractor;
import org.berendeev.roma.smarttodo.domain.interactor.SaveToDoCategoryInteractor;
import org.berendeev.roma.smarttodo.domain.interactor.UpdateToDoCategoryInteractor;
import org.berendeev.roma.smarttodo.domain.interactor.VoidObserver;
import org.berendeev.roma.smarttodo.domain.model.ToDoCategory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

import static org.berendeev.roma.smarttodo.presentation.dialog.CategoryDialog.NEW_CATEGORY;

public class CategoryDialogPresenter {

    @Inject SaveToDoCategoryInteractor saveToDoCategoryInteractor;
    @Inject GetToDoCategoryInteractor getToDoCategoryInteractor;
    @Inject UpdateToDoCategoryInteractor updateToDoCategoryInteractor;
    @Inject DeleteCategoryInteractor deleteCategoryInteractor;

    private CategoryDialogView view;
    private int categoryId;

    @Inject
    public CategoryDialogPresenter() {}

    public void onConfirmClick() {
        if(categoryId == 0){
            saveToDoCategoryInteractor.execute(new VoidObserver(), buildToDoCategory());
        }else {
            updateToDoCategoryInteractor.execute(new VoidObserver(), buildToDoCategory(categoryId));
        }
        view.onComplete();
    }

    public void start() {
        if (categoryId != NEW_CATEGORY){
            getToDoCategoryInteractor.execute(new LoadToDoCategoriesObserver(), categoryId);
        }
    }

    public void stop(){
        saveToDoCategoryInteractor.dispose();
        saveToDoCategoryInteractor.dispose();
    }

    private ToDoCategory buildToDoCategory(){
        return buildToDoCategory(0);
    }

    private ToDoCategory buildToDoCategory(int id){
        return ToDoCategory.create(id, view.getCategoryName(), false, new ArrayList<>());
    }

    public void setView(CategoryDialogView view) {
        this.view = view;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void onDeleteClick() {
        deleteCategoryInteractor.execute(new VoidObserver(), buildToDoCategory(categoryId));
        view.onComplete();
    }

    private class LoadToDoCategoriesObserver extends DisposableObserver<ToDoCategory>{

        @Override public void onNext(ToDoCategory category) {
            view.setCategoryName(category.name());
        }

        @Override public void onError(Throwable e) {

        }

        @Override public void onComplete() {

        }
    }
}
