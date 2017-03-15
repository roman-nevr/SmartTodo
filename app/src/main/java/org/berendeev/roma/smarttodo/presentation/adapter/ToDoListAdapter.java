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

import java.util.Iterator;
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
        Object result = null;

        if(position < categories.get(0).toDos().size()){
            return categories.get(0).toDos().get(position);
        }

        int categoryIndex = 1;
        int index = categories.get(0).toDos().size();

        do {
            ToDoCategory category = categories.get(categoryIndex);
            //if item in current category
            if (position < index + (category.isExpanded() ? category.toDos().size() + 1 : 1)){
                if (position == index){
                    return category;
                }else {
                    return category.toDos().get(position - index - 1);
                }
            //else move to next category
            }else {
                index = index + (category.isExpanded() ? category.toDos().size() + 1 : 1);
                categoryIndex++;
            }
        }while (index <= position);


        return result;
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
        int index = categories.indexOf(getCategory(categoryId));
        categories.set(index, categories.get(index).toBuilder().isExpanded(false).build());
        notifyItemRangeRemoved(categoryPosition(categoryId) + 1, getCategory(categoryId).toDos().size());
    }

    private void expand(View arrow, int categoryId, boolean immidiatly) {
        int duration = 200;
        if (immidiatly){
            duration = 0;
        }
        arrow.animate().setDuration(duration).rotation(90);
        int index = categories.indexOf(getCategory(categoryId));
        categories.set(index, categories.get(index).toBuilder().isExpanded(true).build());
        notifyItemRangeInserted(categoryPosition(categoryId) + 1, getCategory(categoryId).toDos().size());
    }

    private int categoryPosition(int categoryId){
        int begin = -1;
        Iterator<ToDoCategory> iterator = categories.iterator();
        ToDoCategory category = iterator.next();
        do{
            begin = begin + (category.isExpanded() ? category.toDos().size() + 1 : 1);
            category = iterator.next();
        }while (categoryId != category.id());

        return begin;
    }

    private ToDoCategory getCategory(int categoryId){
        ToDoCategory toDoCategory = null;
        for (ToDoCategory category : categories) {
            if (category.id() == categoryId){
                toDoCategory = category;
            }
        }
        return toDoCategory;
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
