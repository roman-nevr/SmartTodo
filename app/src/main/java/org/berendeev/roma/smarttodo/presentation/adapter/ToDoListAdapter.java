package org.berendeev.roma.smarttodo.presentation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import org.berendeev.roma.smarttodo.BuildConfig;
import org.berendeev.roma.smarttodo.R;
import org.berendeev.roma.smarttodo.domain.model.ToDo;
import org.berendeev.roma.smarttodo.domain.model.ToDoCategory;
import org.berendeev.roma.smarttodo.presentation.presenter.ToDoListPresenter;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ToDoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ToDoListPresenter presenter;
    private List<ToDoCategory> categories;

    private static final int ITEM = 0;
    private static final int CATEGORY = 1;

    public ToDoListAdapter(ToDoListPresenter presenter, List<ToDoCategory> categories ) {
        this.presenter = presenter;
        this.categories = categories;
        setHasStableIds(true);
    }

    @Override public long getItemId(int position) {
        return getItem(position).hashCode();

    }

    @Override public int getItemViewType(int position) {
        Object item = getItem(position);
        if (item != null){
            if (item instanceof ToDoCategory){
                return CATEGORY;
            }else {
                return ITEM;
            }
        }else {
            return -1;
        }
    }

    private Object getItem(int position){

        if (position == 0 && categories.get(0).toDos().size() == 0){
            return categories.get(1);
        }

        if(position < categories.get(0).toDos().size()){
            return categories.get(0).toDos().get(position);
        }

        position = position - categories.get(0).toDos().size();

        int categoryIndex = 1;
        while (categoryIndex < categories.size()){
            if (position == 0){
                return categories.get(categoryIndex);
            }
            if (!categories.get(categoryIndex).isExpanded() || categories.get(categoryIndex).toDos().size() == 0){
                position--;
                categoryIndex++;
            }else {
                if (position < categories.get(categoryIndex).toDos().size() + 1){
                    return categories.get(categoryIndex).toDos().get(position - 1);
                }else {
                    position = position - categories.get(0).toDos().size() - 1;
                }
            }
        }
        if (BuildConfig.DEBUG){
            throw new IllegalArgumentException();
        }
        return null;
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
            return new ToDoViewHolder(view);

        }
        if (viewType == CATEGORY){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_head_item, parent, false);
            return new ToDoCategoryHolder(view);
        }
        return null;
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object item = getItem(position);
        if(item instanceof ToDo){
            bindToDoViewHolder((ToDoViewHolder)holder, (ToDo)item);
        }else {
            bindToDoCategoryViewHolder((ToDoCategoryHolder)holder, (ToDoCategory)item);
        }
    }

    private void bindToDoViewHolder(ToDoViewHolder holder, ToDo todo) {
        holder.todoName.setText(todo.name());
        holder.id = todo.id();
        holder.todoCheckBox.setChecked(todo.isChecked());
    }

    private void bindToDoCategoryViewHolder(ToDoCategoryHolder holder, ToDoCategory category){
        holder.categoryName.setText(category.name());
        holder.categoryId = category.id();
        holder.isExpanded = category.isExpanded();
        if (category.isExpanded()){
            holder.arrow.setRotation(90);
        }
    }

    @Override public int getItemCount() {
        int count = 0;
        for (ToDoCategory category : categories) {
            if (category.isExpanded()) {
                count = count + category.toDos().size() + 1;
            }else {
                count++;
            }
        }
        return count - 1;
    }

    public void update(List<ToDoCategory> categories){
        this.categories = categories;
        notifyDataSetChanged();
    }

    private void collapse(View arrow, int categoryId, boolean immidiatly) {
        int duration = 200;
        if (immidiatly){
            duration = 0;
        }
        arrow.animate().setDuration(duration).rotation(0);
        int start = -1;
        int begin = 0;
        int count = 0;
        for (ToDoCategory category : categories) {
            start = start + (category.isExpanded() ? category.toDos().size() + 1 : 1);
            if (category.id() == categoryId){
                int index = categories.indexOf(category);
                categories.set(index, category.toBuilder().isExpanded(false).build());
                begin = start;
                count = category.toDos().size();
            }
        }
        notifyItemRangeInserted(begin, count);
    }

    private void expand(View arrow, int categoryId, boolean immidiatly) {
        int duration = 200;
        if (immidiatly){
            duration = 0;
        }
        arrow.animate().setDuration(duration).rotation(90);
        int start = -1;
        int begin = 0;
        int end = 0;
        for (ToDoCategory category : categories) {
            start = start + (category.isExpanded() ? category.toDos().size() + 1 : 1);
            if (category.id() == categoryId){
                int index = categories.indexOf(category);
                categories.set(index, category.toBuilder().isExpanded(true).build());
                begin = start;
                end = start + category.toDos().size();
            }
        }
        notifyItemRangeInserted(begin, end);
    }

    public class ToDoViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.todo_name) TextView todoName;
        @BindView(R.id.todo_checkbox) CheckBox todoCheckBox;
        int id;
        boolean isChecked;

        public ToDoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            todoName.setOnClickListener(v -> {
                presenter.onToDoClick(id);
            });
            todoCheckBox.setOnClickListener(v -> {
                presenter.onToDoCheckboxClick(id, isChecked);
            });
            todoCheckBox.setOnCheckedChangeListener((buttonView, checked) -> isChecked = checked);
        }
    }

    public class ToDoCategoryHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.category_item_name) TextView categoryName;
        @BindView(R.id.item_arrow) ImageView arrow;
        boolean isExpanded;
        int categoryId;

        public ToDoCategoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            categoryName.setOnClickListener(v -> {
                presenter.onToDoCategoryClick(categoryId);
            });
            arrow.setOnClickListener(v -> {
                if(isExpanded){
                    collapse(v, categoryId, false);
                }else {
                    expand(v, categoryId, false);
                }
                isExpanded = !isExpanded;
                presenter.onToDoCategoryExpandClick(categoryId, isExpanded);
            });
        }
    }



}
