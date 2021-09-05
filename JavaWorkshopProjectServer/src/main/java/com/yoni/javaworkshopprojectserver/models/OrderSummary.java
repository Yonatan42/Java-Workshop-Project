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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Yoni
 */
@Entity(name = "OrdersSummaries")
@Table(name = "orders_summaries")
@NamedQueries({
    @NamedQuery(name = "OrdersSummaries.findAll", query = "SELECT o FROM OrdersSummaries o"),
    @NamedQuery(name = "OrdersSummaries.findByOrderId", query = "SELECT o FROM OrdersSummaries o WHERE o.orderId = :orderId"),
    @NamedQuery(name = "OrdersSummaries.findByUserId", query = "SELECT o FROM OrdersSummaries o WHERE o.userId = :userId"),
    @NamedQuery(name = "OrdersSummaries.findByFullName", query = "SELECT o FROM OrdersSummaries o WHERE o.fullName = :fullName"),
    @NamedQuery(name = "OrdersSummaries.findByEmail", query = "SELECT o FROM OrdersSummaries o WHERE o.email = :email"),
    @NamedQuery(name = "OrdersSummaries.findByPhone", query = "SELECT o FROM OrdersSummaries o WHERE o.phone = :phone"),
    @NamedQuery(name = "OrdersSummaries.findByTotalPrice", query = "SELECT o FROM OrdersSummaries o WHERE o.totalPrice = :totalPrice"),
    @NamedQuery(name = "OrdersSummaries.findByTransactionDate", query = "SELECT o FROM OrdersSummaries o WHERE o.transactionDate = :transactionDate")})
public class OrderSummary implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "order_id")
    @Expose
    private int orderId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_id")
    @Expose
    private int userId;
    @Size(max = 67)
    @Column(name = "full_name")
    @Expose
    private String fullName;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 320)
    @Column(name = "email")
    @Expose
    private String email;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "phone")
    @Expose
    private String phone;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "address")
    @Expose
    private String address;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total_price")
    @Expose
    private BigDecimal totalPrice;
    @Basic(optional = false)
    @Column(name = "transaction_date")
    @Expose
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;

    public OrderSummary() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
    
}
