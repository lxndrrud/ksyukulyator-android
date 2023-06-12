package com.lxndrrud.ksyukulyator.callbacks;

import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lxndrrud.ksyukulyator.domain.Product;

import java.util.List;

public class CalculationProductsSelectionLayoutCallback implements ICallback {

    private final List<Product> selectedProductsList;
    private final FloatingActionButton deleteButton, cancelSelectionButton;

    public CalculationProductsSelectionLayoutCallback(List<Product> selectedProductsList,
                                             FloatingActionButton deleteButton, FloatingActionButton cancelSelectionButton) {
        this.selectedProductsList = selectedProductsList;
        this.deleteButton = deleteButton;
        this.cancelSelectionButton = cancelSelectionButton;
    }
    @Override
    public void callBack() {
        if (selectedProductsList.isEmpty()) {
            deleteButton.setVisibility(View.INVISIBLE);
            deleteButton.setClickable(false);
            cancelSelectionButton.setVisibility(View.INVISIBLE);
            cancelSelectionButton.setClickable(false);
        } else {
            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setClickable(true);
            cancelSelectionButton.setVisibility(View.VISIBLE);
            cancelSelectionButton.setClickable(true);
        }
    }
}
