package com.yoni.javaworkshopprojectserver.models;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;

// todo - fill in
@Entity(name = "OrdersDetails")
@Table(name = "orders_details")
public class OrderDetails implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "order_id")
    @Expose
    private Integer orderId;
}
