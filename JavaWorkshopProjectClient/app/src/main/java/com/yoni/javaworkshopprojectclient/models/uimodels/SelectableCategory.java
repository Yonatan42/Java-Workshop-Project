package com.yoni.javaworkshopprojectclient.models.uimodels;

import com.yoni.javaworkshopprojectclient.models.entitymodels.ProductCategory;
import com.yoni.javaworkshopprojectclient.utils.ListUtils;

import java.util.List;

public class SelectableCategory {

    private ProductCategory productCategory;
    private boolean isSelected;


    public SelectableCategory(ProductCategory productCategory){
        this.productCategory = productCategory;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getTitle(){
        return productCategory.getTitle();
    }


    public static List<SelectableCategory> fromProductCategories(List<ProductCategory> categories){
        return ListUtils.map(categories, SelectableCategory::new);
    }

    public static List<ProductCategory> toProductCategory(List<SelectableCategory> categories){
        return ListUtils.map(categories, category -> category.productCategory);
    }
}
