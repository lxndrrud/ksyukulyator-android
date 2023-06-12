package com.lxndrrud.ksyukulyator;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lxndrrud.ksyukulyator.adapters.ProductArrayAdapter;
import com.lxndrrud.ksyukulyator.callbacks.ICallback;
import com.lxndrrud.ksyukulyator.callbacks.ProductsSelectionLayoutCallback;
import com.lxndrrud.ksyukulyator.domain.Product;
import com.lxndrrud.ksyukulyator.repositories.ProductsRepo;
import com.lxndrrud.ksyukulyator.utils.toastMaker.IToastMaker;
import com.lxndrrud.ksyukulyator.utils.toastMaker.LongToastMaker;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private IToastMaker toastMaker;
    private ListView productsView;
    private ProductsRepo productsRepo;
    private List<Product> selectedProductsList;
    private FloatingActionButton addButton, addToCalcButton, deleteButton, cancelSelectionButton;
    private ICallback selectionLayoutCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toastMaker = new LongToastMaker();
        try {
            productsView = findViewById(R.id.productsListView);
            productsView.setClickable(true);
            addButton = findViewById(R.id.addButton);
            addToCalcButton = findViewById(R.id.addToCalculatorButton);
            deleteButton = findViewById(R.id.deleteButton);
            cancelSelectionButton = findViewById(R.id.cancelSelectionButton);
            selectedProductsList = new ArrayList<>();
            selectionLayoutCallback = new ProductsSelectionLayoutCallback(
                    this.selectedProductsList,
                    this.addButton, this.addToCalcButton, this.deleteButton, this.cancelSelectionButton
            );
            productsRepo = new ProductsRepo(MainActivity.this);
            displayProducts();
        } catch (Exception e) {
            Toast errorToast = toastMaker.makeToast(this, "Ошибка: " + e.getMessage());
            errorToast.show();
        }

    }

    public void displayProducts() {
        try {
            List<Product> products = productsRepo.getAll();
            //ProductListAdapter productListAdapter = new ProductListAdapter(this, products);
            ProductArrayAdapter productArrayAdapter = new ProductArrayAdapter(this, products, selectedProductsList,
                    selectionLayoutCallback);
            productsView.setAdapter(productArrayAdapter);
        } catch (Exception e) {
            Toast errorToast = toastMaker.makeToast(this, "Ошибка: " + e.getMessage());
            errorToast.show();
        }
    }

    public void onCategoriesListActivityButtonClick(View view) {
        Intent intent = new Intent(this, CategoriesListActivity.class);
        startActivity(intent);
    }

    public void onCalculatorActivityButtonClick(View view) {
        Intent intent = new Intent(this, CalculatorActivity.class);
        startActivity(intent);
    }

    public void onAddProductButtonClick(View view) {
        Intent intent = new Intent(this, AddProductActivity.class);
        startActivity(intent);
    }

    public void onAddToCalculatorButtonClick(View view) {
        List<Product> selectedForCalcProducts = productsRepo.getSelectedForCalculation();
        for (Product product : selectedProductsList) {
            if (!selectedForCalcProducts.contains(product)) {
                productsRepo.updateIsSelectedForCalculation(product, true);
            }
        }
    }

    public void onDeleteButtonClick(View view) {
        for (Product product : selectedProductsList) {
            try {
                this.productsRepo.deleteProduct(product.getId());
            } catch (Exception e) {
                Toast errorToast = toastMaker.makeToast(this, "Ошибка: " + e.getMessage());
                errorToast.show();
            }
        }
        selectedProductsList.clear();
        selectionLayoutCallback.callBack();
        displayProducts();
    }

    public void onCancelSelectionButtonClick(View view) {
        for (int i = 0; i < productsView.getCount(); i++) {
            View mChild = productsView.getChildAt(i);
            CheckBox mCheckBox = mChild.findViewById(R.id.productCheckbox);
            mCheckBox.setChecked(false);
        }
        selectedProductsList.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayProducts();
        selectionLayoutCallback.callBack();
    }
}