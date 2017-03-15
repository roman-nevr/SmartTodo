package org.berendeev.roma.smarttodo.data;

import org.berendeev.roma.smarttodo.data.datasource.SQLiteDatasource;
import org.berendeev.roma.smarttodo.domain.Repository;
import org.berendeev.roma.smarttodo.domain.model.Product;
import org.berendeev.roma.smarttodo.domain.model.PurchasingList;
import org.berendeev.roma.smarttodo.domain.model.ToDo;
import org.berendeev.roma.smarttodo.domain.model.ToDoCategory;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;


public class RepositoryImpl implements Repository {

    private SQLiteDatasource sqLiteDatasource;

    public RepositoryImpl(SQLiteDatasource sqLiteDatasource) {
        this.sqLiteDatasource = sqLiteDatasource;
    }

    @Override public Completable saveToDo(ToDo toDo) {
        return sqLiteDatasource.saveToDo(toDo);
    }

    @Override public Completable updateToDo(ToDo toDo) {
        return sqLiteDatasource.updateToDo(toDo);
    }

    @Override public Completable deleteToDo(ToDo toDo) {
        return sqLiteDatasource.deleteToDo(toDo);
    }

    @Override public Observable<ToDo> getTodo(int id) {
        return sqLiteDatasource.getTodo(id);
    }

    @Override public Observable<List<ToDo>> getAllFromCategory(int categoryId) {
        return sqLiteDatasource.getAllFromCategory(categoryId);
    }

    @Override public Observable<List<ToDo>> getAllToDos() {
        return sqLiteDatasource.getAllToDos();
    }

    @Override public Completable saveCategory(ToDoCategory category) {
        return sqLiteDatasource.saveCategory(category);
    }

    @Override public Completable updateCategory(ToDoCategory category) {
        return sqLiteDatasource.updateCategory(category);
    }

    @Override public Completable deleteCategory(ToDoCategory category) {
        return sqLiteDatasource.deleteCategory(category);
    }

    @Override public Observable<ToDoCategory> getCategory(int id) {
        return sqLiteDatasource.getCategory(id);
    }

    @Override public Observable<List<ToDoCategory>> getAllCategories() {
        return sqLiteDatasource.getAllCategories();
    }

    @Override public Completable saveProduct(Product product) {
        return null;
    }

    @Override public Observable<List<String>> findProducts(String nameSnippet) {
        return null;
    }

    @Override public Completable savePurchasingList(PurchasingList list) {
        return null;
    }

    @Override public Observable<List<PurchasingList>> getAllPurchasingLists() {
        return null;
    }
}
