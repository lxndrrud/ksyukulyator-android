package com.lxndrrud.ksyukulyator;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lxndrrud.ksyukulyator.adapters.CategoryArrayAdapter;
import com.lxndrrud.ksyukulyator.callbacks.CategoriesSelectionLayoutCallback;
import com.lxndrrud.ksyukulyator.callbacks.ICallback;
import com.lxndrrud.ksyukulyator.domain.Category;
import com.lxndrrud.ksyukulyator.repositories.CategoriesRepo;
import com.lxndrrud.ksyukulyator.utils.toastMaker.IToastMaker;
import com.lxndrrud.ksyukulyator.utils.toastMaker.LongToastMaker;

import java.util.ArrayList;
import java.util.List;

public class CategoriesListActivity extends AppCompatActivity {
    private IToastMaker toastMaker;
    private CategoriesRepo categoriesRepo;
    private ICallback selectionLayoutCallback;
    private List<Category> selectedCategoriesList;
    private FloatingActionButton addButton, deleteButton, cancelSelectionButton;
    private ListView categoriesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_list_activity);
        toastMaker = new LongToastMaker();
        try {
            categoriesView = findViewById(R.id.categoriesListView);
            categoriesView.setClickable(true);
            addButton = findViewById(R.id.addButton);
            deleteButton = findViewById(R.id.deleteButton);
            cancelSelectionButton = findViewById(R.id.cancelSelectionButton);
            selectedCategoriesList = new ArrayList<>();
            categoriesRepo = new CategoriesRepo(CategoriesListActivity.this);
            displayCategories();
        } catch (Exception e) {
            Toast errorToast = toastMaker.makeToast(this, "Ошибка: " + e.getMessage());
            errorToast.show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        displayCategories();
        selectionLayoutCallback.callBack();
    }

    public void displayCategories() {
        try {
            List<Category> categories = categoriesRepo.getAll(false);
            ICallback selectionCallback = new CategoriesSelectionLayoutCallback(
                this.selectedCategoriesList,
                this.addButton, this.deleteButton, this.cancelSelectionButton);
            this.selectionLayoutCallback = selectionCallback;
            CategoryArrayAdapter categoryArrayAdapter = new CategoryArrayAdapter(this, categories,
                selectedCategoriesList, selectionCallback);

            categoriesView.setAdapter(categoryArrayAdapter);
        } catch (Exception e) {
            Toast errorToast = toastMaker.makeToast(this, "Ошибка: " + e.getMessage());
            errorToast.show();
        }
    }

    public void onProductsListActivityButtonClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onCalculatorActivityButtonClick(View view) {
        Intent intent = new Intent(this, CalculatorActivity.class);
        startActivity(intent);
    }

    public void onAddCategoryButtonClick(View view) {
        Intent intent = new Intent(this, AddCategoryActivity.class);
        startActivity(intent);
    }
    public void onDeleteButtonClick(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Удалить категории")
                .setMessage("Ксю, ты уверена, что хочешь удалить выбранные категории?")
                .setPositiveButton("Подтвердить", (dialogInterface, i) -> {
                    deleteSelectedCategories();
                })
                .setNegativeButton("Отмена", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    private void deleteSelectedCategories() {
        for (Category category : selectedCategoriesList) {
            try {
                this.categoriesRepo.deleteCategory(category.getId());
            } catch (Exception e) {
                Toast errorToast = toastMaker.makeToast(this, "Ошибка: " + e.getMessage());
                errorToast.show();
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
