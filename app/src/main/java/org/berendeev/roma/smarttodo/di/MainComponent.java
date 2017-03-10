package org.berendeev.roma.smarttodo.di;

import org.berendeev.roma.smarttodo.domain.Repository;
import org.berendeev.roma.smarttodo.presentation.activity.ToDoListActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = MainModule.class)
public interface MainComponent {

    void inject(ToDoListActivity activity);
    ToDoDetailsComponent plusToDoDetailsComponent();
}
