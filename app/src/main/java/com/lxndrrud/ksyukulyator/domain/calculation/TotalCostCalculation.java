package com.lxndrrud.ksyukulyator.domain.calculation;

import androidx.annotation.NonNull;
import com.lxndrrud.ksyukulyator.domain.Product;

public class TotalCostCalculation implements ICalculationStrategy {
    private float mass;
    private Product product;

    public TotalCostCalculation() {}

    public void setProduct(@NonNull Product product) {
        this.product = product;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    @Override
    public float calculate() {
        return (product.getCost() * mass ) / (12 * 100);
    }
}
