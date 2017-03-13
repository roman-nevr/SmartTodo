package org.berendeev.roma.smarttodo.presentation.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.berendeev.roma.smarttodo.R;

import javax.inject.Inject;

public class CategoryDialog extends DialogFragment {

    public static final String ID = "id";
    private int categoryId;

    @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        categoryId = bundle.getInt(ID);
        View form = getActivity().getLayoutInflater()
                .inflate(R.layout.category_dialog_layout, null, true);
//        EditText categoryName = (TextView) form.findViewById(R.id.tvMessage);
        Button btnConfirm = (Button) form.findViewById(R.id.confirm);
        Button btnCancel = (Button) form.findViewById(R.id.cancel);
        btnConfirm.setText(getConfirmLabel());
        btnCancel.setText(getCancelLabel());
//        tvMessage.setText(bundle.getString(QUESTION));
//        btnConfirm.setOnClickListener(v -> {
//            if (listener != null) {
//                listener.onOkButtonClick(categoryId);
//            }
//            ok = true;
//            dismiss();
//        });
        btnCancel.setOnClickListener(v -> dismiss());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(form);
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    private String getCancelLabel(){
        return getResources().getString(R.string.cancel_button_label);
    }

    private String getConfirmLabel(){
        if (categoryId == 0){
            return getResources().getString(R.string.create_button_label);
        }else {
            return getResources().getString(R.string.create_button_label);
        }
    }


    private class Presenter{
    }

}
