package com.lxndrrud.ksyukulyator;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lxndrrud.ksyukulyator.adapters.ProductArrayAdapter;
import com.lxndrrud.ksyukulyator.adapters.ProductListAdapter;
import com.lxndrrud.ksyukulyator.callbacks.ICallback;
import com.lxndrrud.ksyukulyator.callbacks.ProductsSelectionLayoutCallback;
import com.lxndrrud.ksyukulyator.domain.Product;
import com.lxndrrud.ksyukulyator.repositories.ProductsRepo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView errorView;
    private ListView productsView;
    private ProductsRepo productsRepo;
    private List<Product> selectedProductsList;
    private List<Product> addedToCalcProductsList;
    private FloatingActionButton addButton, addToCalcButton, deleteButton, cancelSelectionButton;
    private ICallback selectionLayoutCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        errorView = findViewById(R.id.errorView);
        productsView = findViewById(R.id.productsListView);
        productsView.setClickable(true);
        addButton = findViewById(R.id.addButton);
        addToCalcButton = findViewById(R.id.addToCalculatorButton);
        deleteButton = findViewById(R.id.deleteButton);
        cancelSelectionButton = findViewById(R.id.cancelSelectionButton);
        selectedProductsList = new ArrayList<>();
        addedToCalcProductsList = new ArrayList<>();
        /*
        productsView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    Product product = (Product) adapterView.getItemAtPosition(i);
                    selectedProductsList.add(product);
                    view.animate().setDuration(2000).alpha(0.5F);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedProductsList.clear();
            }
        });
         */
        productsRepo = new ProductsRepo(MainActivity.this);
        displayProducts();
    }



    public void displayProducts() {
        try {
            List<Product> products = productsRepo.getAll();
            errorView.setText("Количество продуктов" + products.size());
            //ProductListAdapter productListAdapter = new ProductListAdapter(this, products);
            ICallback selectionCallback = new ProductsSelectionLayoutCallback(
                    this.selectedProductsList,
                    this.addButton, this.addToCalcButton, this.deleteButton, this.cancelSelectionButton
            );
            this.selectionLayoutCallback = selectionCallback;
            ProductArrayAdapter productArrayAdapter = new ProductArrayAdapter(this, products, selectedProductsList,
                    selectionCallback);

            productsView.setAdapter(productArrayAdapter);
        } catch (Exception e) {
            errorView.setText(e.getMessage());
        }
    }

    public void onCategoriesListActivityButtonClick(View view) {
        Intent intent = new Intent(this, CategoriesListActivity.class);
        startActivity(intent);
    }

    public void onAddProductButtonClick(View view) {
        Intent intent = new Intent(this, AddProductActivity.class);
        startActivity(intent);
    }

    public void onAddToCalculatorButtonClick(View view) {
        for (int i = 0; i < selectedProductsList.size(); i++) {
            if (!addedToCalcProductsList.contains(selectedProductsList.get(i))) {
                addedToCalcProductsList.add(selectedProductsList.get(i));
            }
        }
    }

    public void onDeleteButtonClick(View view) {
        for (int i = 0; i < selectedProductsList.size(); i++) {
            try {
                addedToCalcProductsList.remove(selectedProductsList.get(i));
                this.productsRepo.deleteProduct(selectedProductsList.get(i).getId());
            } catch (Exception ignored) {
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
    }
}