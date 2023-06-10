package com.lxndrrud.ksyukulyator.callbacks;

import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lxndrrud.ksyukulyator.domain.Product;

import java.util.List;

public class ProductsSelectionLayoutCallback implements ICallback {
    private final List<Product> selectedProductsList;
    private final FloatingActionButton addButton, addToCalcButton, deleteButton, cancelSelectionButton;

    public ProductsSelectionLayoutCallback(List<Product> selectedProductsList, FloatingActionButton addButton,
                                   FloatingActionButton addToCalcButton, FloatingActionButton deleteButton,
                                   FloatingActionButton cancelSelectionButton) {
        this.selectedProductsList = selectedProductsList;
        this.addButton = addButton;
        this.addToCalcButton = addToCalcButton;
        this.deleteButton = deleteButton;
        this.cancelSelectionButton = cancelSelectionButton;
    }

    @Override
    public void callBack() {
        if (!selectedProductsList.isEmpty()) {
            addButton.setVisibility(View.INVISIBLE);
            addButton.setClickable(false);
            addToCalcButton.setVisibility(View.VISIBLE);
            addToCalcButton.setClickable(true);
            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setClickable(true);
            cancelSelectionButton.setVisibility(View.VISIBLE);
            cancelSelectionButton.setClickable(true);
        } else {
            addButton.setVisibility(View.VISIBLE);
            addButton.setClickable(true);
            addToCalcButton.setVisibility(View.INVISIBLE);
            addToCalcButton.setClickable(false);
            deleteButton.setVisibility(View.INVISIBLE);
            deleteButton.setClickable(false);
            cancelSelectionButton.setVisibility(View.INVISIBLE);
            cancelSelectionButton.setClickable(false);
        }
    }
}
