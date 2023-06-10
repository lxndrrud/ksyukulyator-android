package com.lxndrrud.ksyukulyator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lxndrrud.ksyukulyator.adapters.CategoryArrayAdapter;
import com.lxndrrud.ksyukulyator.callbacks.CategoriesSelectionLayoutCallback;
import com.lxndrrud.ksyukulyator.callbacks.ICallback;
import com.lxndrrud.ksyukulyator.domain.Category;
import com.lxndrrud.ksyukulyator.repositories.CategoriesRepo;

import java.util.ArrayList;
import java.util.List;

public class CategoriesListActivity extends AppCompatActivity {
    private CategoriesRepo categoriesRepo;
    private ICallback selectionLayoutCallback;
    private List<Category> selectedCategoriesList;
    private FloatingActionButton addButton, deleteButton, cancelSelectionButton;
    private ListView categoriesView;
    private TextView errorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_list_activity);
        errorView = findViewById(R.id.errorView);
        categoriesView = findViewById(R.id.categoriesListView);
        categoriesView.setClickable(true);
        addButton = findViewById(R.id.addButton);
        deleteButton = findViewById(R.id.deleteButton);
        cancelSelectionButton = findViewById(R.id.cancelSelectionButton);
        selectedCategoriesList = new ArrayList<>();
        categoriesRepo = new CategoriesRepo(CategoriesListActivity.this);
        displayCategories();
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayCategories();
    }

    public void displayCategories() {
        try {
            List<Category> categories = categoriesRepo.getAll(false);
            errorView.setText("Количество категорий" + categories.size());
            ICallback selectionCallback = new CategoriesSelectionLayoutCallback(
                this.selectedCategoriesList,
                this.addButton, this.deleteButton, this.cancelSelectionButton);
            this.selectionLayoutCallback = selectionCallback;
            CategoryArrayAdapter categoryArrayAdapter = new CategoryArrayAdapter(this, categories,
                selectedCategoriesList, selectionCallback);

            categoriesView.setAdapter(categoryArrayAdapter);
        } catch (Exception e) {
            errorView.setText(e.getMessage());
        }
    }

    public void onProductsListActivityButtonClick(View view) { finish(); }

    public void onAddCategoryButtonClick(View view) {
        Intent intent = new Intent(this, AddCategoryActivity.class);
        startActivity(intent);
    }
    public void onDeleteButtonClick(View view) {
        for (int i = 0; i < selectedCategoriesList.size(); i++) {
            try {
                this.categoriesRepo.deleteCategory(selectedCategoriesList.get(i).getId());
            } catch (Exception ignored) {
            }
        }
        selectedCategoriesList.clear();
        selectionLayoutCallback.callBack();
        displayCategories();
    }
    public void onCancelSelectionButtonClick(View view) {
        for (int i = 0; i < categoriesView.getCount(); i++) {
            View mChild = categoriesView.getChildAt(i);
            CheckBox mCheckBox = mChild.findViewById(R.id.categoryCheckbox);
            mCheckBox.setChecked(false);
        }
        selectedCategoriesList.clear();
    }
}
