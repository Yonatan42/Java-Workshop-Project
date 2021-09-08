/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.models;

import com.google.gson.annotations.Expose;
import com.yoni.javaworkshopprojectserver.utils.CollectionUtils;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Yoni
 */
public class OrderDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Expose
    private int id;
    @Expose
    private int userId;
    @Expose
    private List<OrderDetailsProduct> products;
    @Expose
    private float totalPrice;

    public OrderDetails(int id, int userId, List<OrderDetailsProduct> products, float totalPrice) {
        this.id = id;
        this.userId = userId;
        this.products = products;
        this.totalPrice = totalPrice;
    }

    public OrderDetails(Order order){
        this(
                order.getId(),
                order.getUser().getId(),
                CollectionUtils.convertCollection(order.getOrderProducts(), OrderDetailsProduct::new),
                order.getTotalPrice()
        );
    }

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

    private static class OrderDetailsProduct{
        private static final long serialVersionUID = 1L;
        @Expose
        private int id;
        @Expose
        private String title;
        @Expose
        private int quantity;
        @Expose
        private float price;

        public OrderDetailsProduct(int id, String title, int quantity, float price) {
            this.id = id;
            this.title = title;
            this.quantity = quantity;
            this.price = price;
        }

        public OrderDetailsProduct(OrderProduct orderProduct){
            this(
                    orderProduct.getProduct().getId(),
                    orderProduct.getProduct().getTitle(),
                    orderProduct.getQuantity(),
                    orderProduct.getPriceAtOrder()
            );
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

        @Override
        public String toString() {
            return "OrderDetailsProduct{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", quantity=" + quantity +
                    ", price=" + price +
                    '}';
        }
    }

}
