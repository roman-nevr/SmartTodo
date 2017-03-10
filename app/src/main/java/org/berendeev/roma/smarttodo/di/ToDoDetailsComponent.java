package org.berendeev.roma.smarttodo.di;

import org.berendeev.roma.smarttodo.presentation.activity.ToDoDetailsActivity;

import dagger.Subcomponent;

@Subcomponent(modules = ToDoDetailsModule.class)
public interface ToDoDetailsComponent {
    void inject(ToDoDetailsActivity activity);
}
