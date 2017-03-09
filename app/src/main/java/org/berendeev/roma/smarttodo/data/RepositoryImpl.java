package org.berendeev.roma.smarttodo.data;

import org.berendeev.roma.smarttodo.data.datasource.SQLiteDatasource;
import org.berendeev.roma.smarttodo.domain.Repository;
import org.berendeev.roma.smarttodo.domain.model.Product;
import org.berendeev.roma.smarttodo.domain.model.PurchasingList;
import org.berendeev.roma.smarttodo.domain.model.ToDo;
import org.berendeev.roma.smarttodo.domain.model.ToDoCategory;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


public class RepositoryImpl implements Repository {

    @Inject  SQLiteDatasource sqLiteDatasource;

    @Override public Observable<Void> saveToDo(ToDo toDo) {
        return sqLiteDatasource.saveToDo(toDo);
    }

    @Override public Observable<ToDo> getTodo(int id) {
        return sqLiteDatasource.getTodo(id);
    }

    @Override public Observable<List<ToDo>> getAllFromCategory(int categoryId) {
        return null;
    }

    @Override public Observable<Void> saveCategory(ToDoCategory category) {
        return null;
    }

    @Override public Observable<ToDoCategory> getCategory(int id) {
        return null;
    }

    @Override public Observable<List<ToDoCategory>> getAllCategories() {
        return null;
    }

    @Override public Observable<Void> saveProduct(Product product) {
        return null;
    }

    @Override public Observable<List<String>> findProducts(String nameSnippet) {
        return null;
    }

    @Override public Observable<Void> savePurchasingList(PurchasingList list) {
        return null;
    }

    @Override public Observable<List<PurchasingList>> getAllPurchasingLists() {
        return null;
    }
}
