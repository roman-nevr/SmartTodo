package org.berendeev.roma.smarttodo.presentation.dialog;

interface CategoryDialogView {
    String getCategoryName();

    void onComplete();

    void setCategoryName(String categoryName);
}
