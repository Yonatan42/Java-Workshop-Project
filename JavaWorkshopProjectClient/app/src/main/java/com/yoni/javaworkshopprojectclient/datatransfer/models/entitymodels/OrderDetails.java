package com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

// todo - synchronize this class with server/db
public class OrderDetails {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("userId")
    @Expose
    private int userId;
    @SerializedName("products")
    @Expose
    private List<OrderDetailsProduct> products;
    @SerializedName("totalPrice")
    @Expose
    private float totalPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<OrderDetailsProduct> getProducts() {
        return products;
    }

    public void setProducts(List<OrderDetailsProduct> products) {
        this.products = products;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "id=" + id +
                ", userId=" + userId +
                ", products=" + products +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
