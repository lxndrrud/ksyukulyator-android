package com.lxndrrud.ksyukulyator.domain.calculation;

import androidx.annotation.NonNull;
import com.lxndrrud.ksyukulyator.domain.Product;

public class WeightCalculation implements ICalculationStrategy {
    private float totalCost;
    private Product product;

    public WeightCalculation() {}

    public void setProduct(@NonNull Product product) {
        this.product = product;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public float calculate() {
        if (totalCost == 0 && product.getCost() == 0) {
            return 0;
        }
        return (totalCost * 12 * 100) / product.getCost();
    }
}
