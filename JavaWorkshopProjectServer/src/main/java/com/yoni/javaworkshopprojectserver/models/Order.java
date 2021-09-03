package com.yoni.javaworkshopprojectserver.models;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;

// todo - fill in
@Entity(name = "Orders")
@Table(name = "orders")
public class Order implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Expose
    private Integer id;
}
