/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Yoni
 */
@Entity(name = "Stock")
@Table(name = "stock")
@NamedQueries({
    @NamedQuery(name = "Stock.findAll", query = "SELECT s FROM Stock s"),
    @NamedQuery(name = "Stock.findByProductIds", query = "SELECT s FROM Stock s WHERE s.productId IN :productIds"),
    @NamedQuery(name = "Stock.findByProductId", query = "SELECT s FROM Stock s WHERE s.productId = :productId"),
    @NamedQuery(name = "Stock.findAllActive", query = "SELECT s FROM Stock s WHERE s.isEnabled = true AND s.quantity > 0"),
    @NamedQuery(name = "Stock.findById", query = "SELECT s FROM Stock s WHERE s.id = :id"),
    @NamedQuery(name = "Stock.findByQuantity", query = "SELECT s FROM Stock s WHERE s.quantity = :quantity"),
    @NamedQuery(name = "Stock.findByPrice", query = "SELECT s FROM Stock s WHERE s.price = :price"),
    @NamedQuery(name = "Stock.findByIsEnabled", query = "SELECT s FROM Stock s WHERE s.isEnabled = :isEnabled"),
    @NamedQuery(name = "Stock.findByCreated", query = "SELECT s FROM Stock s WHERE s.created = :created"),
    @NamedQuery(name = "Stock.findByModified", query = "SELECT s FROM Stock s WHERE s.modified = :modified")})
public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Expose
    private Integer id;
    @Basic(optional = false)
    @Column(name = "product_id")
    private Integer productId;
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
    private float price;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_enabled")
    private boolean isEnabled;
    @Basic(optional = false)
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Basic(optional = false)
    @Column(name = "modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;
    @OneToOne(mappedBy = "stock")
    @Expose
    private Product product;

    public Stock() {
    }

    public Stock(Integer id) {
        this.id = id;
    }

    public Stock(Integer id, int quantity, float price, boolean isEnabled, Date created, Date modified) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.isEnabled = isEnabled;
        this.created = created;
        this.modified = modified;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        this.productId = product != null ? product.getId() : null;
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
        if (!(object instanceof Stock)) {
            return false;
        }
        Stock other = (Stock) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", price=" + price +
                ", isEnabled=" + isEnabled +
                ", created=" + created +
                ", modified=" + modified +
                ", product=" + product +
                '}';
    }
}
