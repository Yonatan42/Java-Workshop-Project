package com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {

    @SerializedName("productId")
    @Expose
    private int productId;
    @SerializedName("categories")
    @Expose
    private List<ProductCategory> categories;
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
    @SerializedName("quantity")
    @Expose
    private int stock;
    @SerializedName("price")
    @Expose
    private float price;
    @SerializedName("isEnabled")
    @Expose
    private boolean isEnabled;

    private int cartQuantity = -1;


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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
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

    public List<ProductCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<ProductCategory> categories) {
        this.categories = categories;
    }

    public int getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(int cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", categories=" + categories +
                ", stockId=" + stockId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageData='" + imageData + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                ", isEnabled=" + isEnabled +
                ", cartQuantity=" + cartQuantity +
                '}';
    }

}
