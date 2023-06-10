package com.lxndrrud.ksyukulyator.domain;

import lombok.Data;
import lombok.NonNull;
import lombok.ToString;

@Data
@ToString(includeFieldNames=true)
public class Product {
    private long id;
    private String title;
    private float cost;
    private Category category;

    public Product(
            @NonNull long id,
            @NonNull String title,
            @NonNull float cost,
            Category category
    ) {
        this.id = id;
        this.title = title;
        this.cost = cost;
        this.category = category;
    }
}
