package org.berendeev.roma.smarttodo.presentation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import org.berendeev.roma.smarttodo.R;
import org.berendeev.roma.smarttodo.domain.model.ToDo;
import org.berendeev.roma.smarttodo.presentation.App;
import org.berendeev.roma.smarttodo.presentation.ToDoDetailsPresenter;
import org.berendeev.roma.smarttodo.presentation.ToDoDetailsView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ToDoDetailsActivity extends AppCompatActivity implements ToDoDetailsView {

    public static final String ID = "id";
    @BindView(R.id.todo_name) EditText etToDoName;
    @BindView(R.id.todo_description) EditText etToDoDescription;
    @BindView(R.id.complete_action_fab) FloatingActionButton doneFab;

    @Inject ToDoDetailsPresenter presenter;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initDI();
    }

    private void initDI() {
        App.getApplication().getMainComponent().plusToDoDetailsComponent().inject(this);
    }

    @Override protected void onStart() {
        super.onStart();
        presenter.setView(this);
        presenter.setTodoId(getIdFromIntent());
    }

    @Override protected void onStop() {
        super.onStop();
        presenter.stop();
    }

    private void initUI() {
        setContentView(R.layout.todo_details);
        ButterKnife.bind(this);
        doneFab.setOnClickListener(v -> {
            presenter.done();
        });
    }

    @Override public void fillView(ToDo toDo) {
        etToDoName.setText(toDo.name());
        etToDoDescription.setText(toDo.description());
    }

    @Override public void showError() {
        Snackbar.make(etToDoDescription, "Error!!!", Snackbar.LENGTH_INDEFINITE);
    }

    @Override public String getName() {
        return etToDoName.getText().toString();
    }

    @Override public String getDescription() {
        return etToDoDescription.getText().toString();
    }

    public static void start(Context context, int toDoId){
        Intent intent = new Intent(context, ToDoDetailsActivity.class);
        intent.putExtra(ID, toDoId);
        context.startActivity(intent);
    }

    public static void start(Context context){
        Intent intent = new Intent(context, ToDoDetailsActivity.class);
//        intent.putExtra(ID, -1);
        context.startActivity(intent);
    }

    private int getIdFromIntent(){
        return getIntent().getIntExtra(ID, -1);
    }
}
