package com.yoni.javaworkshopprojectserver.models;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;

// todo - fill in
@Entity(name = "OrdersSummaries")
@Table(name = "orders_summaries")
public class OrderSummary implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "order_id")
    @Expose
    private Integer orderId;
}
