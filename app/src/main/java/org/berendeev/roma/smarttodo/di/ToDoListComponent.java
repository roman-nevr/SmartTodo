package org.berendeev.roma.smarttodo.di;

import org.berendeev.roma.smarttodo.presentation.activity.ToDoListActivity;

import dagger.Subcomponent;

@Subcomponent
public interface ToDoListComponent {
    void inject(ToDoListActivity activity);
}
