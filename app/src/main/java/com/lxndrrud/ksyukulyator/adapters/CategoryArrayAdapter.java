package com.lxndrrud.ksyukulyator.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.lxndrrud.ksyukulyator.DisplayCategoryActivity;
import com.lxndrrud.ksyukulyator.DisplayProductActivity;
import com.lxndrrud.ksyukulyator.R;
import com.lxndrrud.ksyukulyator.callbacks.ICallback;
import com.lxndrrud.ksyukulyator.domain.Category;

import java.util.List;

public class CategoryArrayAdapter extends ArrayAdapter<Category> {
    private List<Category> selectedCategories;
    private ICallback callback;
    public CategoryArrayAdapter(Context context, List<Category> categories, List<Category> selectedCategories,
                               ICallback callback) {
        super(context, R.layout.product_list_view, categories);
        this.selectedCategories = selectedCategories;
        this.callback = callback;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Category category = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.category_list_view, parent, false);
        }

        TextView titleView = convertView.findViewById(R.id.titleView);
        CheckBox categoryCheckbox = convertView.findViewById(R.id.categoryCheckbox);

        titleView.setText(category.getTitle());
        categoryCheckbox.setChecked(false);

        categoryCheckbox.setOnCheckedChangeListener((compoundButton, b) -> {
            if (compoundButton.isChecked()) {
                selectedCategories.add(category);
            } else {
                selectedCategories.remove(category);
            }
            callback.callBack();
        });

        convertView.setClickable(true);
        convertView.setOnClickListener((v) -> {
            Intent intent = new Intent(getContext(), DisplayCategoryActivity.class);
            intent.putExtra("selectedCategoryId", ((Long) category.getId()).toString());
            getContext().startActivity(intent);
        });

        return convertView;
    }
}
