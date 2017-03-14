package org.berendeev.roma.smarttodo.presentation;

import org.berendeev.roma.smarttodo.domain.model.ToDo;
import org.berendeev.roma.smarttodo.domain.model.ToDoCategory;

import java.util.List;

public interface ToDoDetailsView {
    void fillView(ToDo toDo);

    void showError();

    String getName();

    String getDescription();

    void setCategories(List<ToDoCategory> categories);

    void setCurrentCategory(ToDoCategory category);

    int getCurrentCategoryId();

    interface Router{
        void moveToToDoList();
    }
}
