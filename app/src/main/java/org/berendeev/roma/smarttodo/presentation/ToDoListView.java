package org.berendeev.roma.smarttodo.presentation;

import org.berendeev.roma.smarttodo.domain.model.ToDo;

import java.util.List;

public interface ToDoListView {
    void showToDos(List<ToDo> toDos);

    void showError();

    interface Router{
        void moveToToDoDetails(int id);
        void moveToAddNewToDo();
        void showAddCategory();
        void showRenameCategory(int categoryId);
    }
}
