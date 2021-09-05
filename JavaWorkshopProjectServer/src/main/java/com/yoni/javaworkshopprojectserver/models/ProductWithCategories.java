/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Yoni
 */
@Entity(name = "ProductsWithCategories")
@Table(name = "products_with_categories")
@NamedQueries({
    @NamedQuery(name = "ProductsWithCategories.findAll", query = "SELECT p FROM ProductsWithCategories p"),
    @NamedQuery(name = "ProductsWithCategories.findByProductId", query = "SELECT p FROM ProductsWithCategories p WHERE p.productId = :productId"),
    @NamedQuery(name = "ProductsWithCategories.findByTitle", query = "SELECT p FROM ProductsWithCategories p WHERE p.title = :title"),
    @NamedQuery(name = "ProductsWithCategories.findByCategoryId", query = "SELECT p FROM ProductsWithCategories p WHERE p.categoryId = :categoryId"),
    @NamedQuery(name = "ProductsWithCategories.findByCategory", query = "SELECT p FROM ProductsWithCategories p WHERE p.category = :category")})
public class ProductWithCategories implements Serializable {

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
    @Expose
    @Column(name = "title")
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

    public ProductWithCategories() {
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
    
}
