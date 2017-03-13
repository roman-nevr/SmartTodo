package org.berendeev.roma.smarttodo.domain;

import org.berendeev.roma.smarttodo.domain.model.Product;
import org.berendeev.roma.smarttodo.domain.model.PurchasingList;
import org.berendeev.roma.smarttodo.domain.model.ToDo;
import org.berendeev.roma.smarttodo.domain.model.ToDoCategory;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface Repository {
    Completable saveToDo(ToDo toDo);
    Completable updateToDo(ToDo toDo);
    Completable deleteToDo(ToDo toDo);
    Observable<ToDo> getTodo(int id);
    Observable<List<ToDo>> getAllFromCategory(int categoryId);

    Completable saveCategory(ToDoCategory category);
    Completable updateCategory(ToDoCategory category);
    Completable deleteCategory(ToDoCategory category);
    Observable<ToDoCategory> getCategory(int id);
    Observable<List<ToDoCategory>> getAllCategories();

    Completable saveProduct(Product product);
    Observable<List<String>> findProducts(String nameSnippet);

    Completable savePurchasingList(PurchasingList list);
    Observable<List<PurchasingList>> getAllPurchasingLists();
}
