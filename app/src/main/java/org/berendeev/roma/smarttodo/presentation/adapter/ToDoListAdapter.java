package org.berendeev.roma.smarttodo.presentation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.berendeev.roma.smarttodo.R;
import org.berendeev.roma.smarttodo.domain.model.ToDo;
import org.berendeev.roma.smarttodo.presentation.ToDoListPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ToDoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ToDoListPresenter presenter;
    private List<ToDo> visibleTodos;

    public ToDoListAdapter(ToDoListPresenter presenter, List<ToDo> visibleTodos) {
        this.presenter = presenter;
        this.visibleTodos = visibleTodos;
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new ToDoViewHolder(view);
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindToDoViewHolder((ToDoViewHolder)holder, position);
    }

    private void bindToDoViewHolder(ToDoViewHolder holder, int position) {
        holder.todoName.setText(visibleTodos.get(position).name());
        holder.id = visibleTodos.get(position).id();
    }

    @Override public int getItemCount() {
        return visibleTodos.size();
    }

    public void addToDos(List<ToDo> newTodos){
        int start = visibleTodos.size();
        visibleTodos.addAll(newTodos);
        notifyItemRangeInserted(start, visibleTodos.size() - start);
    }

    public void addTodo(ToDo toDo){
        visibleTodos.add(toDo);
        notifyItemInserted(visibleTodos.size() - 1);
    }


    public class ToDoHeadViewHolder extends RecyclerView.ViewHolder{

        public ToDoHeadViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ToDoViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.todo_name) TextView todoName;
        int id;

        public ToDoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
            todoName.setOnClickListener(v -> {
                presenter.onToDoClick(id);
            });
        }
    }

}
