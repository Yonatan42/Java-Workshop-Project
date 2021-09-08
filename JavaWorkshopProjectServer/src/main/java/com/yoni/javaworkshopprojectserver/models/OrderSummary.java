/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.Date;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

/**
 *
 * @author Yoni
 */
public class OrderSummary implements Serializable {

    private static final long serialVersionUID = 1L;
    @Expose
    private int id;
    @Expose
    private int userId;
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private String email;
    @Expose
    private String phone;
    @Expose
    private String address;
    @Expose
    private float totalPrice;
    @Expose
    private Date transactionDate;


    public OrderSummary() {
    }

    public OrderSummary(int id, int userId, String firstName, String lastName, String email, String phone, String address, float totalPrice, Date transactionDate) {
        this.id = id;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.totalPrice = totalPrice;
        this.transactionDate = transactionDate;
    }

    public OrderSummary(Order order){
        this(
            order.getId(),
            order.getUser().getId(),
            order.getUser().getFirstName(),
            order.getUser().getLastName(),
            order.getEmail(),
            order.getPhone(),
            order.getAddress(),
            order.getOrderProducts().stream().reduce(0f, (acc, orderProduct) -> acc + orderProduct.getQuantity() * orderProduct.getPriceAtOrder(), (u, u2) -> u + u2),
            order.getCreated()
        );
    }
}
