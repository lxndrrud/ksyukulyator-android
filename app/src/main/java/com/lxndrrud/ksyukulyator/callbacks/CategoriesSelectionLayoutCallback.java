package com.lxndrrud.ksyukulyator.callbacks;

import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lxndrrud.ksyukulyator.domain.Category;

import java.util.List;

public class CategoriesSelectionLayoutCallback implements ICallback {

    private final List<Category> selectedCategoriesList;
    private final FloatingActionButton addButton, deleteButton, cancelSelectionButton;

    public CategoriesSelectionLayoutCallback(List<Category> selectedCategoriesList, FloatingActionButton addButton,
           FloatingActionButton deleteButton, FloatingActionButton cancelSelectionButton) {
        this.selectedCategoriesList = selectedCategoriesList;
        this.addButton = addButton;
        this.deleteButton = deleteButton;
        this.cancelSelectionButton = cancelSelectionButton;
    }
    @Override
    public void callBack() {
        if (!selectedCategoriesList.isEmpty()) {
            addButton.setVisibility(View.INVISIBLE);
            addButton.setClickable(false);
            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setClickable(true);
            cancelSelectionButton.setVisibility(View.VISIBLE);
            cancelSelectionButton.setClickable(true);
        } else {
            addButton.setVisibility(View.VISIBLE);
            addButton.setClickable(true);
            deleteButton.setVisibility(View.INVISIBLE);
            deleteButton.setClickable(false);
            cancelSelectionButton.setVisibility(View.INVISIBLE);
            cancelSelectionButton.setClickable(false);
        }
    }
}
