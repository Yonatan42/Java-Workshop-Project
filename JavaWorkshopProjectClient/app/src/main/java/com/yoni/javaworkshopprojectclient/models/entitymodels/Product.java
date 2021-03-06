package com.yoni.javaworkshopprojectclient.models.entitymodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("categories")
    @Expose
    private List<ProductCategory> categories;
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

    private int cartQuantity = -1;

    public Product(){}

    public Product(Product other){
        replace(other);
    }


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


    public void replace(Product product){
        this.id = product.id;
        this.stock = product.stock;
        this.categories = product.categories;
        this.price = product.price;
        this.title = product.title;
        this.description = product.description;
        this.imageData = product.imageData;
        this.cartQuantity = product.cartQuantity;
    }



    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", categories=" + categories +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageData='" + imageData + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                ", cartQuantity=" + cartQuantity +
                '}';
    }

}
