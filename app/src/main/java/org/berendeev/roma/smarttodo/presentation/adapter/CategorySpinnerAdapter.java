package org.berendeev.roma.smarttodo.presentation.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.berendeev.roma.smarttodo.R;
import org.berendeev.roma.smarttodo.domain.model.ToDoCategory;

import java.util.List;

public class CategorySpinnerAdapter extends ArrayAdapter {
    private Context context;
    private int resourceId;
    private List<ToDoCategory> categories;
    private String defaultName;

    public CategorySpinnerAdapter(@NonNull Context context, @LayoutRes int resourceId, @NonNull List<ToDoCategory> categories, String defaultName) {
        super(context, resourceId, categories);
        this.context = context;
        this.resourceId = resourceId;
        this.categories = categories;
        this.defaultName = defaultName;
    }


    @NonNull @Override public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, resourceId, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.text);
        tv.setText(categories.get(position).name());
        return convertView;
    }

    public void setCategories(List<ToDoCategory> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    @Override public long getItemId(int position) {
        return categories.get(position).id();
    }

    @Override public int getPosition(@Nullable Object item) {
        return categories.indexOf(item);
    }

    @Override public boolean hasStableIds() {
        return true;
    }

    @Override public int getCount() {
        return super.getCount();
    }

    @Override public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}
