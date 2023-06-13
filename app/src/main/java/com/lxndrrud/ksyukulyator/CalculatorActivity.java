package com.lxndrrud.ksyukulyator;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lxndrrud.ksyukulyator.adapters.ProductCalculationArrayAdapter;
import com.lxndrrud.ksyukulyator.callbacks.CalculationCallback;
import com.lxndrrud.ksyukulyator.callbacks.CalculationProductsSelectionLayoutCallback;
import com.lxndrrud.ksyukulyator.callbacks.ICallback;
import com.lxndrrud.ksyukulyator.domain.Product;
import com.lxndrrud.ksyukulyator.domain.calculation.TotalCostCalculation;
import com.lxndrrud.ksyukulyator.domain.calculation.MassCalculation;
import com.lxndrrud.ksyukulyator.repositories.ProductsRepo;
import com.lxndrrud.ksyukulyator.utils.toastMaker.IToastMaker;
import com.lxndrrud.ksyukulyator.utils.toastMaker.LongToastMaker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CalculatorActivity extends AppCompatActivity {
    private FloatingActionButton deleteButton, cancelSelectionButton;
    private TextView massTextView, totalCostTextView;
    private ListView productsListView;
    private ProductsRepo productsRepo;
    private List<Product> selectedProductsList;
    private ICallback selectionLayoutCallback;
    private CalculationCallback calculationCallback;
    private MassCalculation massCalculation;
    private TotalCostCalculation totalCostCalculation;
    private IToastMaker toastMaker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_activity);
        toastMaker = new LongToastMaker();
        try {
            productsListView = findViewById(R.id.productsListView);
            deleteButton = findViewById(R.id.deleteButton);
            cancelSelectionButton = findViewById(R.id.cancelSelectionButton);
            massTextView = findViewById(R.id.massTextView);
            totalCostTextView = findViewById(R.id.totalCostTextView);
            selectedProductsList = new ArrayList<>();
            productsRepo = new ProductsRepo(CalculatorActivity.this);
            selectionLayoutCallback = new CalculationProductsSelectionLayoutCallback(selectedProductsList,
                    deleteButton, cancelSelectionButton);
            totalCostCalculation = new TotalCostCalculation();
            massCalculation = new MassCalculation();
            calculationCallback = new CalculationCallback(productsListView, massTextView, totalCostTextView);
            displayProductsForCalculation();
        } catch (Exception e) {
            Toast errorToast = toastMaker.makeToast(this, "Ошибка: " + e.getMessage());
            errorToast.show();
        }
    }

    private HashMap<String, Float> storeCalculation() {
        HashMap<String, Float> resultMap = new HashMap<>();
        for (int i = 0; i < productsListView.getCount(); i++) {
            View mChild = productsListView.getChildAt(i);
            Product product = (Product) productsListView.getItemAtPosition(i);
            if (product != null && mChild != null) {
                EditText massEditText = mChild.findViewById(R.id.massEditText);
                if (!massEditText.getText().toString().isEmpty()) {
                    resultMap.put(product.getId()+ "_mass", Float.parseFloat(massEditText.getText().toString()));
                }
                EditText totalCostEditText = mChild.findViewById(R.id.totalCostEditText);
                if (!totalCostEditText.getText().toString().isEmpty()) {
                    resultMap.put(product.getId()+ "_totalCost", Float.parseFloat(totalCostEditText.getText().toString()));
                }
            }
        }
        return resultMap;
    }

    private void restoreCalculation(HashMap<String, Float> calculationMap) {
        for (int i = 0; i < productsListView.getCount(); i++) {
            View mChild = productsListView.getChildAt(i);
            Product product = (Product) productsListView.getItemAtPosition(i);
            if (product != null && mChild != null) {
                EditText massEditText = mChild.findViewById(R.id.massEditText);
                Float productMass = calculationMap.get(product.getId() + "_mass");
                if (productMass != null) {
                    massEditText.setText(productMass.toString());
                }
                EditText totalCostEditText = mChild.findViewById(R.id.totalCostEditText);
                Float productTotalCost= calculationMap.get(product.getId() + "_totalCost");
                if (productTotalCost != null) {
                    totalCostEditText.setText(productTotalCost.toString());
                }
            }
        }
    }

    public void displayProductsForCalculation() {
        try {
            //HashMap<String, Float> calculationMap = storeCalculation();
            List<Product> products = productsRepo.getSelectedForCalculation();
            ProductCalculationArrayAdapter adapter = new ProductCalculationArrayAdapter(this,
                    products,
                    selectedProductsList,
                    totalCostCalculation, massCalculation,
                    selectionLayoutCallback, calculationCallback);
            productsListView.setAdapter(adapter);
            //restoreCalculation(calculationMap);
        } catch (Exception e) {
            Toast errorToast = toastMaker.makeToast(this, "Ошибка: " + e.getMessage());
            errorToast.show();
        }
    }

    public void onProductsListActivityButtonClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void onCategoriesListActivityButtonClick(View view) {
        Intent intent = new Intent(this, CategoriesListActivity.class);
        startActivity(intent);
    }

    public void onDeleteFromCalculatorButtonClick(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Убрать продукты")
                .setMessage("Ксю, ты уверена, что хочешь убрать выбранные продукты с расчёта?")
                .setPositiveButton("Подтвердить", (dialogInterface, i) -> {
                    deleteSelectedProductsFromCalculation();
                })
                .setNegativeButton("Отмена", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void deleteSelectedProductsFromCalculation() {
        for (Product product : selectedProductsList) {
            productsRepo.updateIsSelectedForCalculation(product, false);
        }
        selectedProductsList.clear();
        displayProductsForCalculation();
        calculationCallback.callBack();
        selectionLayoutCallback.callBack();
    }

    public void onCancelSelectionButtonClick(View view) {
        for (int i = 0; i < productsListView.getCount(); i++) {
            View mChild = productsListView.getChildAt(i);
            CheckBox mCheckBox = mChild.findViewById(R.id.productCheckbox);
            mCheckBox.setChecked(false);
        }
        selectedProductsList.clear();
    }


    protected void onResume() {
        super.onResume();
        displayProductsForCalculation();
        try {
            selectionLayoutCallback.callBack();
            calculationCallback.callBack();
        } catch (Exception e) {
            Toast errorToast = toastMaker.makeToast(this, "Ошибка: " + e.getMessage());
            errorToast.show();
        }
    }
}
