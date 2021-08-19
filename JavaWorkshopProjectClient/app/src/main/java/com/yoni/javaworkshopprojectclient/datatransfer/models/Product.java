package com.yoni.javaworkshopprojectclient.datatransfer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Product {

    @SerializedName("productId")
    @Expose
    private int productId;
    @SerializedName("categories")
    @Expose
    private ProductCategory[] categories;
    @SerializedName("stockId")
    @Expose
    private int stockId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("imageData")
    @Expose
    private String imageData; // base64
    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("quantity")
    @Expose
    private int quantity;
    @SerializedName("price")
    @Expose
    private float price;
    @SerializedName("isEnabled")
    @Expose
    private boolean isEnabled;


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public ProductCategory[] getCategories() {
        return categories;
    }

    public void setCategories(ProductCategory[] categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", categories=" + Arrays.toString(categories) +
                ", stockId=" + stockId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageData='" + imageData + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", isEnabled=" + isEnabled +
                '}';
    }

    public static class ProductCategory
    {
        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("title")
        @Expose
        private String title;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return "ProductCategory{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    '}';
        }
    }


}
