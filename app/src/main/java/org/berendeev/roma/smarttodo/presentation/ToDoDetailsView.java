package org.berendeev.roma.smarttodo.presentation;

import org.berendeev.roma.smarttodo.domain.model.ToDo;

public interface ToDoDetailsView {
    void fillView(ToDo toDo);

    void showError();

    String getName();

    String getDescription();

    interface Router{
        void moveToToDoList();
    }
}
