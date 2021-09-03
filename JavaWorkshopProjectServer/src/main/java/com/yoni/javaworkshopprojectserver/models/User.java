/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoni.javaworkshopprojectserver.models;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Yoni
 */
@Entity(name = "Users")
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = "Users.findAll", query = "SELECT e FROM Users e"),
        @NamedQuery(name = "Users.findById", query = "SELECT e FROM Users e WHERE e.id = :id"),
        @NamedQuery(name = "Users.findByEmail", query = "SELECT e FROM Users e WHERE e.email = :email"),
        @NamedQuery(name = "Users.findByFirstName", query = "SELECT e FROM Users e WHERE e.firstName = :firstName"),
        @NamedQuery(name = "Users.findByLastName", query = "SELECT e FROM Users e WHERE e.lastName = :lastName"),
        @NamedQuery(name = "Users.findByPhone", query = "SELECT e FROM Users e WHERE e.phone = :phone"),
        @NamedQuery(name = "Users.findByCreated", query = "SELECT e FROM Users e WHERE e.created = :created"),
        @NamedQuery(name = "Users.findByModified", query = "SELECT e FROM Users e WHERE e.modified = :modified"),
        @NamedQuery(name = "Users.findByAdmin", query = "SELECT e FROM Users e WHERE e.isAdmin = :isAdmin")})
@NamedStoredProcedureQuery(name = "Users.refreshSecretKey",
        procedureName = "refresh_secret_key", parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "email", type = String.class)})
public class User implements Serializable {
 
    
private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Expose
    private Integer id;
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    @Expose
    private Date created;
    @Column(name = "modified")
    @Temporal(TemporalType.TIMESTAMP)
    @Expose
    private Date modified;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 320)
    @Column(name = "email")
    @Expose
    private String email;
    @Basic(optional = false)
    @Size(min = 60, max = 60)
    @Column(name = "pass")
    private String pass;
    @Basic(optional = false)
    @Size(min = 64, max = 64)
    @Column(name = "secret_key")
    private String secretKey;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "first_name")
    @Expose
    private String firstName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "last_name")
    @Expose
    private String lastName;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 20)
    @Column(name = "phone")
    @Expose
    private String phone;
    @Lob
    @Size(max = 65535)
    @Column(name = "address")
    @Expose
    private String address;
    @Basic(optional = false)
    @Column(name = "is_admin")
    @Expose
    private boolean isAdmin;

    

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

    public User(Integer id, Date created, Date modified, @NotNull @Size(min = 1, max = 320) String email, @Size(min = 60, max = 60) String pass, @Size(min = 64, max = 64) String secretKey, @NotNull @Size(min = 1, max = 32) String firstName, @NotNull @Size(min = 1, max = 32) String lastName, @Size(max = 20) String phone, @Size(max = 65535) String address, boolean isAdmin) {
        this.id = id;
        this.created = created;
        this.modified = modified;
        this.email = email;
        this.pass = pass;
        this.secretKey = secretKey;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
        this.isAdmin = isAdmin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    
//    @Override
//    public int hashCode() {
//        int hash = 0;
//        hash += (id != null ? id.hashCode() : 0);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object object) {
//        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof User)) {
//            return false;
//        }
//        User other = (User) object;
//        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "User{" + "id=" + id + ", created=" + created + ", modified=" + modified + ", email=" + email + ", pass=" + pass + ", secretKey=" + secretKey + ", firstName=" + firstName + ", lastName=" + lastName + ", phone=" + phone + ", address=" + address + '}';
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isAdmin == user.isAdmin &&
                Objects.equals(id, user.id) &&
                Objects.equals(created, user.created) &&
                Objects.equals(modified, user.modified) &&
                Objects.equals(email, user.email) &&
                Objects.equals(pass, user.pass) &&
                Objects.equals(secretKey, user.secretKey) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(phone, user.phone) &&
                Objects.equals(address, user.address);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, created, modified, email, pass, secretKey, firstName, lastName, phone, address, isAdmin);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", created=" + created +
                ", modified=" + modified +
                ", email='" + email + '\'' +
                ", pass='" + pass + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
