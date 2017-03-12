package org.berendeev.roma.smarttodo.domain;

import org.berendeev.roma.smarttodo.domain.model.Product;
import org.berendeev.roma.smarttodo.domain.model.PurchasingList;
import org.berendeev.roma.smarttodo.domain.model.ToDo;
import org.berendeev.roma.smarttodo.domain.model.ToDoCategory;

import java.util.List;

import io.reactivex.Observable;

public interface Repository {
    Observable<Void> saveToDo(ToDo toDo);
    Observable<Void> updateToDo(ToDo toDo);
    Observable<ToDo> getTodo(int id);
    Observable<List<ToDo>> getAllFromCategory(int categoryId);

    Observable<Void> saveCategory(ToDoCategory category);
    Observable<ToDoCategory> getCategory(int id);
    Observable<List<ToDoCategory>> getAllCategories();

    Observable<Void> saveProduct(Product product);
    Observable<List<String>> findProducts(String nameSnippet);

    Observable<Void> savePurchasingList(PurchasingList list);
    Observable<List<PurchasingList>> getAllPurchasingLists();
}
