package org.berendeev.roma.smarttodo.presentation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.berendeev.roma.smarttodo.R;
import org.berendeev.roma.smarttodo.presentation.App;
import org.berendeev.roma.smarttodo.presentation.ToDoListPresenter;

import javax.inject.Inject;


public class ToDoListActivity extends AppCompatActivity {

    @Inject ToDoListPresenter presenter;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDI();
    }

    private void initDI() {
        ((App)getApplication()).getMainComponent().inject(this);
    }
}
