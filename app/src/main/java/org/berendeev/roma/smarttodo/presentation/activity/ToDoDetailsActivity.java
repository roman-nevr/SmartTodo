package org.berendeev.roma.smarttodo.presentation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import org.berendeev.roma.smarttodo.R;
import org.berendeev.roma.smarttodo.domain.model.ToDo;
import org.berendeev.roma.smarttodo.presentation.App;
import org.berendeev.roma.smarttodo.presentation.ToDoDetailsView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ToDoDetailsActivity extends AppCompatActivity implements ToDoDetailsView {

    public static final String ID = "id";
    @BindView(R.id.todo_name) EditText etToDoName;
    @BindView(R.id.todo_description) EditText etToDoDescription;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initDI();
    }

    private void initDI() {
    }

    private void initUI() {
        setContentView(R.layout.todo_details);
        ButterKnife.bind(this);
    }

    @Override public void fillView(ToDo toDo) {

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

    private int getIdFromIntent(Intent intent){
        return intent.getIntExtra(ID, -1);
    }
}
