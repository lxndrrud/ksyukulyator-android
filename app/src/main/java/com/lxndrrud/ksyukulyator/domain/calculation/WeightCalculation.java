package com.lxndrrud.ksyukulyator.domain.calculation;

import com.lxndrrud.ksyukulyator.domain.Product;

public class WeightCalculation implements ICalculationStrategy {
    private float totalCost;
    private Product product;

    public WeightCalculation(Product product, float totalCost) {
        this.product = product;
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
