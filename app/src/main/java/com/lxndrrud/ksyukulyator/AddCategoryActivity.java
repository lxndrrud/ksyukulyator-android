package com.lxndrrud.ksyukulyator;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.lxndrrud.ksyukulyator.repositories.CategoriesRepo;
import com.lxndrrud.ksyukulyator.utils.toastMaker.IToastMaker;
import com.lxndrrud.ksyukulyator.utils.toastMaker.LongToastMaker;


public class AddCategoryActivity extends AppCompatActivity {

    private EditText titleEdit;
    private Button saveButton, cancelButton;
    private CategoriesRepo categoriesRepo;
    private IToastMaker toastMaker;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_category_activity);
        toastMaker = new LongToastMaker();
        titleEdit = findViewById(R.id.titleEdit);
        categoriesRepo = new CategoriesRepo(AddCategoryActivity.this);
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
        try {
            categoriesRepo.createCategory(title);
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
