/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Yoni
 */
@Entity(name = "Products")
@Table(name = "products")
@NamedQueries({
    @NamedQuery(name = "Products.findAll", query = "SELECT p FROM Products p"),
    @NamedQuery(name = "Products.findById", query = "SELECT p FROM Products p WHERE p.id = :id"),
    @NamedQuery(name = "Products.findByTitle", query = "SELECT p FROM Products p WHERE p.title = :title"),
    @NamedQuery(name = "Products.findByCreated", query = "SELECT p FROM Products p WHERE p.created = :created"),
    @NamedQuery(name = "Products.findByModified", query = "SELECT p FROM Products p WHERE p.modified = :modified")})
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Expose
    private Integer id;
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
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Basic(optional = false)
    @Column(name = "modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;
    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "id")
    private Stock stock;

    @ManyToMany(mappedBy = "products")
    @Expose
    private Set<Category> categories = new HashSet<>();

    public Product() {
    }

    public Product(Integer id) {
        this.id = id;
    }

    public Product(Integer id, @NotNull @Size(min = 1, max = 64) String title, @Size(max = 65535) String description, String imageData, Date created, Date modified, Stock stock, Set<Category> categories) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageData = imageData;
        this.created = created;
        this.modified = modified;
        this.categories = categories;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageData='" + imageData + '\'' +
                ", created=" + created +
                ", modified=" + modified +
                ", categories=" + categories +
                '}';
    }
}
