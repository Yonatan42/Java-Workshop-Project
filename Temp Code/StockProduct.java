package com.yoni.javaworkshopprojectserver.models;


import com.google.gson.annotations.Expose;
import org.eclipse.persistence.annotations.MapKeyConvert;
import org.eclipse.persistence.annotations.PrimaryKey;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

// todo - fill in
@Entity(name = "StockProducts")
@Table(name = "products_catalog")
@NamedQueries({
        @NamedQuery(name = "StockProducts.findAllActive", query = "SELECT e FROM StockProducts e WHERE e.isEnabled = true AND e.quantity > 0")/* GROUP BY e.productId, e.stockId")/*,
        @NamedQuery(name = "StockProducts.findAllActivePaged", query = "SELECT e FROM StockProducts e WHERE is_enabled = 1 AND quantity > 0 LIMIT :start, :pageSize")*/
})
public class StockProduct implements Serializable {

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "category_id")
    private int categoryId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "product_id")
    @Expose
    private Integer productId;
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
    private String imageData;
    @NotNull
    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Expose
    private Set<ProductCategory> categories = new HashSet<>();
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

    public StockProduct() {
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
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

    public Set<ProductCategory> getCategories() {
        return categories;
    }

    public void setCategories(Set<ProductCategory> categories) {
        this.categories = categories;
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

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productId != null ? productId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StockProduct)) {
            return false;
        }
        StockProduct other = (StockProduct) object;
        if ((this.productId == null && other.productId != null) || (this.productId != null && !this.productId.equals(other.productId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "StockProduct{" +
                "productId=" + productId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageData='" + imageData + '\'' +
                ", categories=" + categories +
                ", stockId=" + stockId +
                ", quantity=" + quantity +
                ", price=" + price +
                ", isEnabled=" + isEnabled +
                '}';
    }
}
