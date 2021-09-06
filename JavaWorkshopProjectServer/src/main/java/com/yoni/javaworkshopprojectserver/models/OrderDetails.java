///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.yoni.javaworkshopprojectserver.models;
//
//import com.google.gson.annotations.Expose;
//
//import java.io.Serializable;
//import java.math.BigDecimal;
//import java.util.Date;
//import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
//import javax.xml.bind.annotation.XmlRootElement;
//
///**
// *
// * @author Yoni
// */
//@Entity(name = "OrdersDetails")
//@Table(name = "orders_details")
//@NamedQueries({
//    @NamedQuery(name = "OrdersDetails.findAll", query = "SELECT o FROM OrdersDetails o"),
//    @NamedQuery(name = "OrdersDetails.findByOrderId", query = "SELECT o FROM OrdersDetails o WHERE o.orderId = :orderId"),
//    @NamedQuery(name = "OrdersDetails.findByUserId", query = "SELECT o FROM OrdersDetails o WHERE o.userId = :userId"),
//    @NamedQuery(name = "OrdersDetails.findByProductId", query = "SELECT o FROM OrdersDetails o WHERE o.productId = :productId"),
//    @NamedQuery(name = "OrdersDetails.findByFullName", query = "SELECT o FROM OrdersDetails o WHERE o.fullName = :fullName"),
//    @NamedQuery(name = "OrdersDetails.findByEmail", query = "SELECT o FROM OrdersDetails o WHERE o.email = :email"),
//    @NamedQuery(name = "OrdersDetails.findByPhone", query = "SELECT o FROM OrdersDetails o WHERE o.phone = :phone"),
//    @NamedQuery(name = "OrdersDetails.findByProductName", query = "SELECT o FROM OrdersDetails o WHERE o.productName = :productName"),
//    @NamedQuery(name = "OrdersDetails.findByPrice", query = "SELECT o FROM OrdersDetails o WHERE o.price = :price"),
//    @NamedQuery(name = "OrdersDetails.findByQuantity", query = "SELECT o FROM OrdersDetails o WHERE o.quantity = :quantity"),
//    @NamedQuery(name = "OrdersDetails.findByTotalPrice", query = "SELECT o FROM OrdersDetails o WHERE o.totalPrice = :totalPrice"),
//    @NamedQuery(name = "OrdersDetails.findByTransactionDate", query = "SELECT o FROM OrdersDetails o WHERE o.transactionDate = :transactionDate")})
//public class OrderDetails implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//    @Id
//    @Basic(optional = false)
//    @NotNull
//    @Column(name = "order_id")
//    @Expose
//    private int orderId;
//    @Basic(optional = false)
//    @NotNull
//    @Column(name = "user_id")
//    @Expose
//    private int userId;
//    @Basic(optional = false)
//    @NotNull
//    @Column(name = "product_id")
//    @Expose
//    private int productId;
//    @Size(max = 67)
//    @Column(name = "full_name")
//    @Expose
//    private String fullName;
//    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
//    @Basic(optional = false)
//    @NotNull
//    @Size(min = 1, max = 320)
//    @Expose
//    @Column(name = "email")
//    private String email;
//    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
//    @Basic(optional = false)
//    @NotNull
//    @Size(min = 1, max = 20)
//    @Column(name = "phone")
//    @Expose
//    private String phone;
//    @Basic(optional = false)
//    @NotNull
//    @Lob
//    @Size(min = 1, max = 65535)
//    @Column(name = "address")
//    @Expose
//    private String address;
//    @Basic(optional = false)
//    @NotNull
//    @Size(min = 1, max = 64)
//    @Column(name = "product_name")
//    @Expose
//    private String productName;
//    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
//    @Basic(optional = false)
//    @NotNull
//    @Column(name = "price")
//    @Expose
//    private BigDecimal price;
//    @Basic(optional = false)
//    @NotNull
//    @Column(name = "quantity")
//    @Expose
//    private int quantity;
//    @Basic(optional = false)
//    @NotNull
//    @Column(name = "total_price")
//    @Expose
//    private BigDecimal totalPrice;
//    @Basic(optional = false)
//    @Column(name = "transaction_date")
//    @Expose
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date transactionDate;
//
//    public OrderDetails() {
//    }
//
//    public int getOrderId() {
//        return orderId;
//    }
//
//    public void setOrderId(int orderId) {
//        this.orderId = orderId;
//    }
//
//    public int getUserId() {
//        return userId;
//    }
//
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }
//
//    public int getProductId() {
//        return productId;
//    }
//
//    public void setProductId(int productId) {
//        this.productId = productId;
//    }
//
//    public String getFullName() {
//        return fullName;
//    }
//
//    public void setFullName(String fullName) {
//        this.fullName = fullName;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getProductName() {
//        return productName;
//    }
//
//    public void setProductName(String productName) {
//        this.productName = productName;
//    }
//
//    public BigDecimal getPrice() {
//        return price;
//    }
//
//    public void setPrice(BigDecimal price) {
//        this.price = price;
//    }
//
//    public int getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//    }
//
//    public BigDecimal getTotalPrice() {
//        return totalPrice;
//    }
//
//    public void setTotalPrice(BigDecimal totalPrice) {
//        this.totalPrice = totalPrice;
//    }
//
//    public Date getTransactionDate() {
//        return transactionDate;
//    }
//
//    public void setTransactionDate(Date transactionDate) {
//        this.transactionDate = transactionDate;
//    }
//
//}
