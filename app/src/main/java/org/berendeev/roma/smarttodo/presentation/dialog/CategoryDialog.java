package org.berendeev.roma.smarttodo.presentation.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.berendeev.roma.smarttodo.R;
import org.berendeev.roma.smarttodo.presentation.App;
import org.berendeev.roma.smarttodo.presentation.ToDoListView;

import javax.inject.Inject;

import butterknife.BindView;

public class CategoryDialog extends DialogFragment implements CategoryDialogView {

    public static final String ID = "id";
    public static final int NEW_CATEGORY = 0;
    private int categoryId;

    @Inject CategoryDialogPresenter presenter;
    private EditText categoryName;
    private ToDoListView.DialogListener listener;

    @Override public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ToDoListView.DialogListener){
            this.listener = (ToDoListView.DialogListener)context;
        }
    }

    @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
        readCategoryId();
        initDI();
        View form = createUIView();
        return buildDialog(form);
    }

    private Dialog buildDialog(View form){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(form);
        Dialog dialog = builder.create();
        return dialog;
    }

    private void readCategoryId(){
        Bundle bundle = getArguments();
        categoryId = bundle.getInt(ID, NEW_CATEGORY);
    }

    @Override public void onStart() {
        super.onStart();
        presenter.start();
    }

    private View createUIView(){
        View form = getActivity().getLayoutInflater()
                .inflate(R.layout.category_dialog_layout, null, true);
        categoryName = (EditText) form.findViewById(R.id.category_name);
        Button btnConfirm = (Button) form.findViewById(R.id.confirm);
        Button btnDelete = (Button) form.findViewById(R.id.delete);
        if (categoryId != NEW_CATEGORY){
            btnDelete.setVisibility(View.VISIBLE);
        }
        Button btnCancel = (Button) form.findViewById(R.id.cancel);
        btnConfirm.setText(getConfirmLabel());
        btnCancel.setText(getCancelLabel());
        btnDelete.setOnClickListener(v -> presenter.onDeleteClick());
        btnConfirm.setOnClickListener(v -> {
            presenter.onConfirmClick();
        });
        btnCancel.setOnClickListener(v -> dismiss());
        return form;
    }

    private void initDI(){
        App.getApplication().getMainComponent().plusCategoryDialog().inject(this);
        presenter.setView(this);
        presenter.setCategoryId(categoryId);
    }

    private String getCancelLabel(){
        return getResources().getString(R.string.cancel_button_label);
    }

    private String getConfirmLabel(){
        if (categoryId == NEW_CATEGORY){
            return getResources().getString(R.string.create_button_label);
        }else {
            return getResources().getString(R.string.rename_button_label);
        }
    }

    public static DialogFragment getInstance(int categoryId){
        CategoryDialog fragment = new CategoryDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(ID, categoryId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override public String getCategoryName() {
        return categoryName.getText().toString();
    }

    @Override public void setCategoryName(String categoryName) {
        this.categoryName.setText(categoryName);
    }

    @Override public void onComplete() {
        if (listener != null){
            listener.onDialogComplete();
        }
        dismiss();
    }
}
