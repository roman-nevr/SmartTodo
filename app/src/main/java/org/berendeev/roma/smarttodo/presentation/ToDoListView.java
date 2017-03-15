package org.berendeev.roma.smarttodo.presentation;

import org.berendeev.roma.smarttodo.domain.model.ToDo;
import org.berendeev.roma.smarttodo.domain.model.ToDoCategory;

import java.util.List;

public interface ToDoListView {
    void showToDoList(List<ToDoCategory> categories);

    void showAddCategory();
    void showRenameCategory(int categoryId);

    void showError();

    interface Router{
        void moveToToDoDetails(int id);
        void moveToAddNewToDo();
    }

    interface DialogListener{
        void onDialogComplete();
    }
}
