package com.lxndrrud.ksyukulyator.domain;

import lombok.Data;
import lombok.NonNull;
import lombok.ToString;

@Data
@ToString(includeFieldNames=true)
public class Category {
    private long id;
    private String title;
    private Product[] products;

    public Category(
            @NonNull long id,
            @NonNull String title
    ) {
        this.id = id;
        this.title = title;
        products = null;
    }
}
