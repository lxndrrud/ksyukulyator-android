package com.lxndrrud.ksyukulyator.domain.calculation;

import com.lxndrrud.ksyukulyator.domain.Product;

public class TotalCostCalculation implements ICalculationStrategy {
    private float weight;
    private Product product;

    public TotalCostCalculation(Product product, float weight) {
        this.product = product;
        this.weight = weight;
    }
    @Override
    public float calculate() {
        return (product.getCost() * weight ) / (12 * 100);
    }
}
