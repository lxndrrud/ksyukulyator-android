package com.lxndrrud.ksyukulyator;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.lxndrrud.ksyukulyator.adapters.CategorySpinnerAdapter;
import com.lxndrrud.ksyukulyator.domain.Category;
import com.lxndrrud.ksyukulyator.repositories.CategoriesRepo;
import com.lxndrrud.ksyukulyator.repositories.ProductsRepo;
import com.lxndrrud.ksyukulyator.utils.toastMaker.IToastMaker;
import com.lxndrrud.ksyukulyator.utils.toastMaker.LongToastMaker;

import java.util.List;

public class AddProductActivity extends AppCompatActivity {
    private EditText titleEdit, costEdit;
    private Button saveButton, cancelButton;
    private Spinner categorySpinner;

    private ProductsRepo productsRepo;
    private CategoriesRepo categoriesRepo;
    private IToastMaker toastMaker;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product_activity);
        toastMaker = new LongToastMaker();
        try {
            titleEdit = findViewById(R.id.titleEdit);
            costEdit = findViewById(R.id.costEdit);
            categorySpinner = findViewById(R.id.categorySpinner);
            categoriesRepo = new CategoriesRepo(AddProductActivity.this);
            productsRepo = new ProductsRepo(AddProductActivity.this);
            List<Category> categoriesList = categoriesRepo.getAll(true);
            CategorySpinnerAdapter categoryAdapter = new CategorySpinnerAdapter(this, android.R.layout.simple_spinner_item,
                    categoriesList.toArray(new Category[0]));
            categorySpinner.setAdapter(categoryAdapter);
        } catch (Exception e) {
            Toast errorToast = toastMaker.makeToast(this, "Ошибка: " + e.getMessage());
            errorToast.show();
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
        Long selectedCategory = categorySpinner.getSelectedItemId() != 0
                ? categorySpinner.getSelectedItemId()
                : null;
        try {
            productsRepo.createProduct(title, Float.parseFloat(cost), selectedCategory);
            finish();
        } catch (Exception e) {
            Toast errorToast = toastMaker.makeToast(this, "Ошибка: " + e.getMessage());
            errorToast.show();
        }
    }

    public void onCancelAddButtonClick(View view) {
        finish();
    }

}
