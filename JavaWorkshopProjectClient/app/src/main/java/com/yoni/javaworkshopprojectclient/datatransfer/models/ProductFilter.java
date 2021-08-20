package com.yoni.javaworkshopprojectclient.datatransfer.models;

import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.ProductCategory;

public class ProductFilter {
    private String text;
    private ProductCategory category;

    public ProductFilter() {
    }

    public ProductFilter(String text, ProductCategory category) {
        this.text = text;
        this.category = category;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ProductFilter{" +
                "text='" + text + '\'' +
                ", category=" + category +
                '}';
    }
}
