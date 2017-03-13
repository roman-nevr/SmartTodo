package org.berendeev.roma.smarttodo.presentation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import org.berendeev.roma.smarttodo.R;
import org.berendeev.roma.smarttodo.domain.model.ToDo;
import org.berendeev.roma.smarttodo.presentation.App;
import org.berendeev.roma.smarttodo.presentation.presenter.ToDoListPresenter;
import org.berendeev.roma.smarttodo.presentation.ToDoListView;
import org.berendeev.roma.smarttodo.presentation.adapter.ToDoListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ToDoListActivity extends AppCompatActivity implements ToDoListView, ToDoListView.Router {

    @Inject ToDoListPresenter presenter;

    @BindView(R.id.no_todos) LinearLayout noToDos;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.add_todo_fab) FloatingActionButton addToDo;

    private ToDoListAdapter adapter;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initDI();
    }

    private void initUI() {
        setContentView(R.layout.todo_list_layout);
        ButterKnife.bind(this);
        addToDo.setOnClickListener(v -> {
            presenter.onAddButtonClick();
        });
    }

    private void initDI() {
        App.getApplication().getMainComponent().plusToDoListComponent().inject(this);
    }

    @Override protected void onStart() {
        super.onStart();
        presenter.setView(this);
        presenter.setRouter(this);
        presenter.start();
    }

    @Override protected void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override public void showToDos(List<ToDo> toDos) {
        if(toDos.isEmpty()){
            noToDos.setVisibility(View.VISIBLE);
        }else {
            noToDos.setVisibility(View.GONE);
        }
        if (adapter == null){
            adapter = new ToDoListAdapter(presenter, toDos);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                    layoutManager.getOrientation());
            recyclerView.addItemDecoration(dividerItemDecoration);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }else {
            adapter.update(toDos);
        }
    }

    @Override public void showError() {
        recyclerView.setVisibility(View.INVISIBLE);
        addToDo.setVisibility(View.GONE);
        noToDos.setVisibility(View.VISIBLE);
        Snackbar.make(recyclerView, R.string.todo_list_loading_error, Snackbar.LENGTH_INDEFINITE).show();
    }

    @Override public void moveToToDoDetails(int id) {
        ToDoDetailsActivity.start(this, id);
    }

    @Override public void moveToAddNewToDo() {
        ToDoDetailsActivity.start(this);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.todo_menu, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.create_new_category:{
                presenter.onCreateCategoryClick();
                break;
            }
            case R.id.clear_done_tasks:{
                presenter.clearDoneTasks();
                break;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
