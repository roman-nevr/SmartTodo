package org.berendeev.roma.smarttodo.presentation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.berendeev.roma.smarttodo.R;
import org.berendeev.roma.smarttodo.domain.model.ToDo;
import org.berendeev.roma.smarttodo.presentation.App;
import org.berendeev.roma.smarttodo.presentation.ToDoListPresenter;
import org.berendeev.roma.smarttodo.presentation.ToDoListView;

import java.util.List;

import javax.inject.Inject;


public class ToDoListActivity extends AppCompatActivity implements ToDoListView, ToDoListView.Router {

    @Inject ToDoListPresenter presenter;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initDI();
    }

    private void initUI() {
        setContentView(R.layout.activity_main);
    }

    private void initDI() {
        ((App)getApplication()).getMainComponent().inject(this);
    }

    @Override public void showToDos(List<ToDo> toDos) {

    }

    @Override public void moveToToDoDetails(int id) {

    }
}
