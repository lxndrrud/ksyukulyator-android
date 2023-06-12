package com.lxndrrud.ksyukulyator.callbacks;


import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.lxndrrud.ksyukulyator.R;

public class CalculationCallback implements ICallback {
    private final ListView productsListView;
    private final TextView massTextView, totalCostTextView;
    private float summaryMass, summaryTotalCost;
    public CalculationCallback(ListView productsListView, TextView massTextView, TextView totalCostTextView) {
        this.productsListView = productsListView;
        this.massTextView = massTextView;
        this.totalCostTextView = totalCostTextView;
    }

    private void calculateSum() {
        summaryTotalCost = 0F;
        summaryMass = 0F;
        for (int i = 0; i < productsListView.getCount(); i++) {
            View mChild = productsListView.getChildAt(i);
            if (mChild != null) {
                EditText totalCostEditText = mChild.findViewById(R.id.totalCostEditText);
                EditText massEditText = mChild.findViewById(R.id.massEditText);
                if (!massEditText.getText().toString().isEmpty()) {
                    summaryMass += Float.parseFloat(massEditText.getText().toString());
                }
                if (!totalCostEditText.getText().toString().isEmpty()) {
                    summaryTotalCost += Float.parseFloat(totalCostEditText.getText().toString());
                }
            }
        }
    }

    @Override
    public void callBack() {
        calculateSum();
        massTextView.setText("Общая масса: " + summaryMass + " грамм");
        totalCostTextView.setText("Общая стоимость: " + summaryTotalCost + " ХЕ");
    }
}
