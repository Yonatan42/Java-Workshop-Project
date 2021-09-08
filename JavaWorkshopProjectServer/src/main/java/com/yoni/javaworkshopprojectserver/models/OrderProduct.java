package com.yoni.javaworkshopprojectserver.models;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity(name = "OrdersProducts")
@Table(name = "order_products")
public class OrderProduct implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @ManyToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "product_id")
    @Expose
    private Product product;

    @Id
    @ManyToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "order_id")
    private Order order;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "price_at_order")
    @Expose
    private float priceAtOrder;

    @Basic(optional = false)
    @NotNull
    @Column(name = "quantity")
    @Expose
    private int quantity;

    public OrderProduct(){}

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public float getPriceAtOrder() {
        return priceAtOrder;
    }

    public void setPriceAtOrder(float priceAtOrder) {
        this.priceAtOrder = priceAtOrder;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
                "product=" + product +
                ", order=" + order +
                ", priceAtOrder=" + priceAtOrder +
                ", quantity=" + quantity +
                '}';
    }
}
