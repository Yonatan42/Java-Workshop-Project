/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Yoni
 */
@Entity(name = "CatalogProducts")
@Table(name = "products_catalog")
@NamedQueries({
    @NamedQuery(name = "CatalogProducts.findAll", query = "SELECT p FROM CatalogProducts p"),
    @NamedQuery(name = "CatalogProducts.findByProductId", query = "SELECT p FROM CatalogProducts p WHERE p.productId = :productId"),
    @NamedQuery(name = "CatalogProducts.findByTitle", query = "SELECT p FROM CatalogProducts p WHERE p.title = :title"),
    @NamedQuery(name = "CatalogProducts.findByCategoryId", query = "SELECT p FROM CatalogProducts p WHERE p.categoryId = :categoryId"),
    @NamedQuery(name = "CatalogProducts.findByCategory", query = "SELECT p FROM CatalogProducts p WHERE p.category = :category"),
    @NamedQuery(name = "CatalogProducts.findByStockId", query = "SELECT p FROM CatalogProducts p WHERE p.stockId = :stockId"),
    @NamedQuery(name = "CatalogProducts.findByQuantity", query = "SELECT p FROM CatalogProducts p WHERE p.quantity = :quantity"),
    @NamedQuery(name = "CatalogProducts.findByPrice", query = "SELECT p FROM CatalogProducts p WHERE p.price = :price"),
    @NamedQuery(name = "CatalogProducts.findByIsEnabled", query = "SELECT p FROM CatalogProducts p WHERE p.isEnabled = :isEnabled")})
public class CatalogProduct implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "product_id")
    @Expose
    private int productId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "title")
    @Expose
    private String title;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    @Expose
    private String description;
    @Lob
    @Column(name = "image_data")
    @Expose
    private String imageData;
    @Basic(optional = false)
    @NotNull
    @Column(name = "category_id")
    @Expose
    private int categoryId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "category")
    @Expose
    private String category;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stock_id")
    @Expose
    private int stockId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantity")
    @Expose
    private int quantity;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "price")
    @Expose
    private BigDecimal price;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_enabled")
    private boolean isEnabled;

    public CatalogProduct() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
    
}
