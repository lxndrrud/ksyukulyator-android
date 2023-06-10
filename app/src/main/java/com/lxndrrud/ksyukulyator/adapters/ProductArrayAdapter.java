package com.lxndrrud.ksyukulyator.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.lxndrrud.ksyukulyator.DisplayProductActivity;
import com.lxndrrud.ksyukulyator.R;
import com.lxndrrud.ksyukulyator.callbacks.ICallback;
import com.lxndrrud.ksyukulyator.domain.Product;

import java.util.List;
import java.util.function.Function;

public class ProductArrayAdapter extends ArrayAdapter<Product> {
    private List<Product> selectedProducts;
    private ICallback callback;
    public ProductArrayAdapter(Context context, List<Product> products, List<Product> selectedProducts,
        ICallback callback) {
        super(context, R.layout.product_list_view, products);
        this.selectedProducts = selectedProducts;
        this.callback = callback;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Product product = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.product_list_view, parent, false);
        }

        TextView titleView = convertView.findViewById(R.id.titleView);
        TextView costView = convertView.findViewById(R.id.costView);
        TextView categoryTitleView = convertView.findViewById(R.id.categoryTitleView);
        CheckBox productsCheckbox = convertView.findViewById(R.id.productCheckbox);

        titleView.setText(product.getTitle());
        costView.setText(product.getCost() + " ХЕ");
        categoryTitleView.setText(product.getCategory().getTitle());
        productsCheckbox.setChecked(false);

        productsCheckbox.setOnCheckedChangeListener((compoundButton, b) -> {
            if (compoundButton.isChecked()) {
                selectedProducts.add(product);
            } else {
                selectedProducts.remove(product);
            }
            callback.callBack();
        });

        convertView.setClickable(true);
        convertView.setOnClickListener((v) -> {
            Intent intent = new Intent(getContext(), DisplayProductActivity.class);
            intent.putExtra("selectedProductId", ((Long) product.getId()).toString());
            getContext().startActivity(intent);
        });

        return convertView;
    }
}
