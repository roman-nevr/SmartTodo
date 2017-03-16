package org.berendeev.roma.smarttodo.presentation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import org.berendeev.roma.smarttodo.R;
import org.berendeev.roma.smarttodo.domain.model.ToDo;
import org.berendeev.roma.smarttodo.domain.model.ToDoCategory;
import org.berendeev.roma.smarttodo.presentation.App;
import org.berendeev.roma.smarttodo.presentation.adapter.CategorySpinnerAdapter;
import org.berendeev.roma.smarttodo.presentation.presenter.ToDoDetailsPresenter;
import org.berendeev.roma.smarttodo.presentation.ToDoDetailsView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ToDoDetailsActivity extends AppCompatActivity implements ToDoDetailsView, ToDoDetailsView.Router {

    public static final String ID = "id";
    @BindView(R.id.todo_name) EditText etToDoName;
    @BindView(R.id.todo_description) EditText etToDoDescription;
    @BindView(R.id.complete_action_fab) FloatingActionButton doneFab;
    @BindView(R.id.spinner) Spinner spinner;

    @Inject ToDoDetailsPresenter presenter;

    private CategorySpinnerAdapter spinnerAdapter;

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
        showFAB();
        presenter.setView(this);
        presenter.setRouter(this);
        presenter.setTodoId(getIdFromIntent());
        presenter.start();
    }

    @Override protected void onStop() {
        super.onStop();
        presenter.stop();
        hideFAB();
    }

    private void initUI() {
        setContentView(R.layout.todo_details);
        ButterKnife.bind(this);
        doneFab.setSize(0);
        doneFab.setOnClickListener(v -> {
            presenter.done();
        });
    }

    @Override public void fillView(ToDo toDo) {
        etToDoName.setText(toDo.name());
        etToDoDescription.setText(toDo.description());
        spinner.setSelection(spinnerAdapter.getPositionByCategoryId(toDo.categoryId()));
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

    @Override public void setCategories(List<ToDoCategory> categories) {
        spinnerAdapter = new CategorySpinnerAdapter(this, R.layout.spinner_simple_item, categories, getResources().getString(R.string.default_category_name));
        spinner.setAdapter(spinnerAdapter);
    }

    @Override public void setCurrentCategory(ToDoCategory category) {
        spinner.setSelection(spinnerAdapter.getPosition(category));
    }

    @Override public int getCurrentCategoryId() {
        return (int)spinnerAdapter.getItemId(spinner.getSelectedItemPosition());
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

    @Override public void moveToToDoList() {
        finish();
    }

    private void hideFAB(){
        doneFab.animate().setDuration(500).scaleX(0).scaleY(0);
    }

    private void showFAB(){
        doneFab.animate().setDuration(500).scaleX(1).scaleY(1).setInterpolator(new OvershootInterpolator());
    }
}
