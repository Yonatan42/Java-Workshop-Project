/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Yoni
 */
public class CatalogProduct implements Serializable {

    private static final long serialVersionUID = 1L;
    @Expose
    private int productId;
    @Expose
    private String title;
    @Expose
    private String description;
    @Expose
    private String imageData;
    @Expose
    private int quantity;
    @Expose
    private BigDecimal price;
    @Expose
    private boolean isEnabled;
    @Expose
    private Set<Category> categories = new HashSet<>();

    public CatalogProduct() {
    }

    public CatalogProduct(int productId, String title, String description, String imageData, int quantity, BigDecimal price, boolean isEnabled, Set<Category> categories) {
        this.productId = productId;
        this.title = title;
        this.description = description;
        this.imageData = imageData;
        this.quantity = quantity;
        this.price = price;
        this.isEnabled = isEnabled;
        this.categories = categories;
    }

    public CatalogProduct(Stock stock){
        this(
                stock.getProduct().getId(),
                stock.getProduct().getTitle(),
                stock.getProduct().getDescription(),
                stock.getProduct().getImageData(),
                stock.getQuantity(),
                stock.getPrice(),
                stock.getIsEnabled(),
                stock.getProduct().getCategories()
        );
    }
}
