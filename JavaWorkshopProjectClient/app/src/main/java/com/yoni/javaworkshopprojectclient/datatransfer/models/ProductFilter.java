package com.yoni.javaworkshopprojectclient.datatransfer.models;

import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.ProductCategory;

import java.util.Objects;

public class ProductFilter {
    private String text;
    private ProductCategory category;

    public ProductFilter() {
    }

    public ProductFilter(String text, ProductCategory category) {
        setText(text);
        setCategory(category);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text.isEmpty() ? null : text;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category != null && category.getId() > 0 ? category : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(isNullOrEmpty(this) && isNullOrEmpty((ProductFilter) o)) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductFilter that = (ProductFilter) o;
        return Objects.equals(text, that.text) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, category);
    }

    @Override
    public String toString() {
        return "ProductFilter{" +
                "text='" + text + '\'' +
                ", category=" + category +
                '}';
    }


    public static boolean isNullOrEmpty(ProductFilter filter){
        return filter == null || (filter.getText() == null && filter.getCategory() == null);
    }

}
