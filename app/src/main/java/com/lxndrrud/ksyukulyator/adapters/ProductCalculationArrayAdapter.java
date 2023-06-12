package com.lxndrrud.ksyukulyator.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.lxndrrud.ksyukulyator.R;
import com.lxndrrud.ksyukulyator.callbacks.ICallback;
import com.lxndrrud.ksyukulyator.domain.Product;
import com.lxndrrud.ksyukulyator.domain.calculation.TotalCostCalculation;
import com.lxndrrud.ksyukulyator.domain.calculation.MassCalculation;

import java.util.List;

public class ProductCalculationArrayAdapter extends ArrayAdapter<Product> {

    private final List<Product> selectedProducts;
    private final ICallback selectionCallback;
    private final ICallback calculationCallback;
    private final TotalCostCalculation totalCostCalculation;
    private final MassCalculation massCalculation;
    public ProductCalculationArrayAdapter(Context context, List<Product> products, List<Product> selectedProducts,
       TotalCostCalculation totalCostCalculation, MassCalculation massCalculation,
       ICallback selectionCallback, ICallback calculationCallback) {
        super(context, R.layout.product_list_view, products);
        this.selectedProducts = selectedProducts;
        this.selectionCallback = selectionCallback;
        this.calculationCallback = calculationCallback;
        this.totalCostCalculation = totalCostCalculation;
        this.massCalculation = massCalculation;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Product product = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.product_calculation_list_view, parent,
        false);
        }

        TextView titleView = convertView.findViewById(R.id.titleView);
        TextView costView = convertView.findViewById(R.id.costView);
        TextView categoryTitleView = convertView.findViewById(R.id.categoryTitleView);
        CheckBox productsCheckbox = convertView.findViewById(R.id.productCheckbox);
        EditText massEditText = convertView.findViewById(R.id.massEditText);
        EditText totalCostEditText = convertView.findViewById(R.id.totalCostEditText);

        titleView.setText(product.getTitle());
        costView.setText(product.getCost() + " ХЕ");
        categoryTitleView.setText(product.getCategory().getTitle());
        productsCheckbox.setChecked(selectedProducts.contains(product));

        productsCheckbox.setOnCheckedChangeListener((compoundButton, b) -> {
            if (compoundButton.isChecked()) {
                selectedProducts.add(product);
            } else {
                selectedProducts.remove(product);
            }
            selectionCallback.callBack();
        });
        massEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if (massEditText.hasFocus()) {
                    float mass = 0;
                    if (!massEditText.getText().toString().isEmpty()) {
                        mass = Float.parseFloat(massEditText.getText().toString());
                    }
                    totalCostCalculation.setMass(mass);
                    totalCostCalculation.setProduct(product);
                    float totalCost = totalCostCalculation.calculate();
                    totalCostEditText.setText(String.valueOf(totalCost));
                    calculationCallback.callBack();
                }
            }
        });
        totalCostEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if (totalCostEditText.hasFocus()) {
                    float totalCost = 0;
                    if (!totalCostEditText.getText().toString().isEmpty()) {
                        totalCost = Float.parseFloat(totalCostEditText.getText().toString());
                    }
                    massCalculation.setTotalCost(totalCost);
                    massCalculation.setProduct(product);
                    float mass = massCalculation.calculate();
                    massEditText.setText(String.valueOf(mass));
                    calculationCallback.callBack();
                }
            }
        });
        return convertView;
    }
}
