package org.berendeev.roma.smarttodo.di;

import org.berendeev.roma.smarttodo.presentation.dialog.CategoryDialog;

import dagger.Subcomponent;

@Subcomponent
public interface CategoryDialogComponent {
    void inject(CategoryDialog dialog);
}
