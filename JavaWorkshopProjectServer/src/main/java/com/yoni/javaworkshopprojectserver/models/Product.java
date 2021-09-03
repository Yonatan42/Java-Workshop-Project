package com.yoni.javaworkshopprojectserver.models;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;

// todo - fill in
@Entity(name = "Products")
@Table(name = "products")
public class Product implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Expose
    private Integer id;
}
