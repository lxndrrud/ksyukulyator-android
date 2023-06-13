package com.lxndrrud.ksyukulyator.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxndrrud.ksyukulyator.R;
import com.lxndrrud.ksyukulyator.domain.Category;

import java.util.List;

public class CategorySpinnerAdapter extends ArrayAdapter<Category> {
    private Context context;
    private List<Category> categories;

    public CategorySpinnerAdapter(@NonNull Context context, int resource, @NonNull List<Category> objects) {
        super(context, resource, objects);
        context = context;
        categories = objects;
    }

    @Override
    public int getCount(){
        return categories.size();
    }

    @Override
    public Category getItem(int position){
        return categories.get(position);
    }

    @Override
    public long getItemId(int position){
        return categories.get(position).getId();
    }

    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.category_spinner_item_view, parent, false);
        }
        TextView categoryTitleView = convertView.findViewById(R.id.categoryTitleView);
        categoryTitleView.setText(categories.get(position).getTitle());

        // And finally return your dynamic (or custom) view for each spinner item
        return convertView;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.category_spinner_item_view, parent, false);
        }
        TextView categoryTitleView = convertView.findViewById(R.id.categoryTitleView);
        categoryTitleView.setText(categories.get(position).getTitle());

        // And finally return your dynamic (or custom) view for each spinner item
        return convertView;
    }
}
