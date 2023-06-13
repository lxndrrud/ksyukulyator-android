package com.lxndrrud.ksyukulyator;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.lxndrrud.ksyukulyator.adapters.CategorySpinnerAdapter;
import com.lxndrrud.ksyukulyator.domain.Category;
import com.lxndrrud.ksyukulyator.domain.Product;
import com.lxndrrud.ksyukulyator.repositories.CategoriesRepo;
import com.lxndrrud.ksyukulyator.repositories.ProductsRepo;
import com.lxndrrud.ksyukulyator.utils.toastMaker.IToastMaker;
import com.lxndrrud.ksyukulyator.utils.toastMaker.LongToastMaker;

import java.util.List;

public class DisplayProductActivity extends AppCompatActivity {
    private IToastMaker toastMaker;
    private ProductsRepo productsRepo;
    private CategoriesRepo categoriesRepo;
    private EditText titleEdit, costEdit;
    private Spinner categorySpinner;
    private Button saveButton, cancelButton;
    private long selectedProductId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_product_activity);
        titleEdit = findViewById(R.id.titleEdit);
        costEdit = findViewById(R.id.costEdit);
        categorySpinner = findViewById(R.id.categorySpinner);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);

        this.productsRepo = new ProductsRepo(this);
        this.categoriesRepo = new CategoriesRepo(this);
        this.toastMaker = new LongToastMaker();

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            selectedProductId = Integer.parseInt(extras.getString("selectedProductId"));
            loadSelectedProduct();
        }
    }

    private int findCategorySelectedCategoryPosition(List<Category> categoriesList, Category selectedCategory) {
        for (int i = 0; i< categoriesList.size(); i++) {
            if (categoriesList.get(i).getId() == selectedCategory.getId()) {
                return i;
            }
        }
        return 0;
    }


    public void loadSelectedProduct() {
        List<Category> categoriesList = categoriesRepo.getAll(true);
        Product selectedProduct = productsRepo.getProduct(selectedProductId);
        if (selectedProduct != null) {
            titleEdit.setText(selectedProduct.getTitle());
            costEdit.setText(String.valueOf(selectedProduct.getCost()));
            CategorySpinnerAdapter categoryAdapter = new CategorySpinnerAdapter(this,
                    android.R.layout.simple_spinner_item,
                    categoriesList);
            categorySpinner.setAdapter(categoryAdapter);
            int categoryPosition = this.findCategorySelectedCategoryPosition(categoriesList, selectedProduct.getCategory());
            categorySpinner.setSelection(categoryPosition);
        }
    }

    public void onSaveButtonClick(View view) {
        String title = titleEdit.getText().toString();
        if (title.length() == 0) {
            Toast errorToast = toastMaker.makeToast(getApplicationContext(), "Ксю, ты не ввела название продукта!");
            errorToast.show();
            titleEdit.setHighlightColor(Color.RED);
            titleEdit.selectAll();
            return;
        }
        String cost = costEdit.getText().toString();
        if (cost.length() == 0) {
            Toast errorToast = toastMaker.makeToast(getApplicationContext(), "Ксю, ты не ввела стоимость продукта!");
            errorToast.show();
            costEdit.setHighlightColor(Color.RED);
            costEdit.selectAll();
            return;
        }
        Category selectedCategory = categorySpinner.getSelectedItemId() != 0
                ? (Category) categorySpinner.getSelectedItem()
                : null;

        Product productToUpdate = new Product(selectedProductId, title,
            Float.parseFloat(cost), selectedCategory);
        productsRepo.updateProduct(productToUpdate);
        finish();
    }

    public void onCancelButtonClick(View view) {
        finish();
    }
}
