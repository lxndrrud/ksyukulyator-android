package com.lxndrrud.ksyukulyator;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.lxndrrud.ksyukulyator.domain.Category;
import com.lxndrrud.ksyukulyator.repositories.CategoriesRepo;
import com.lxndrrud.ksyukulyator.utils.toastMaker.IToastMaker;
import com.lxndrrud.ksyukulyator.utils.toastMaker.LongToastMaker;

public class DisplayCategoryActivity extends AppCompatActivity {
    private IToastMaker toastMaker;
    private CategoriesRepo categoriesRepo;
    private EditText titleEdit;
    private Button saveButton, cancelButton;
    private long selectedCategoryId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_category_activity);
        titleEdit = findViewById(R.id.titleEdit);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);

        this.categoriesRepo = new CategoriesRepo(this);
        this.toastMaker = new LongToastMaker();

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            selectedCategoryId = Integer.parseInt(extras.getString("selectedCategoryId"));
            loadSelectedProduct();
        }
    }

    public void loadSelectedProduct() {
        Category selectedCategory = categoriesRepo.getCategory(selectedCategoryId);
        if (selectedCategory != null) {
            titleEdit.setText(selectedCategory.getTitle());
        }
    }

    public void onSaveButtonClick(View view) {
        String title = titleEdit.getText().toString();
        if (title.length() == 0) {
            Toast errorToast = toastMaker.makeToast(getApplicationContext(), "Ксю, ты не ввела название категории!");
            errorToast.show();
            titleEdit.setHighlightColor(Color.RED);
            titleEdit.selectAll();
            return;
        }

        Category categoryToUpdate = new Category(selectedCategoryId, title);
        categoriesRepo.updateCategory(categoryToUpdate);
        finish();
    }

    public void onCancelButtonClick(View view) {
        finish();
    }
}
