package com.yoni.javaworkshopprojectclient.datatransfer.models.pureresponsemodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.Product;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.ProductCategory;

import java.util.List;

public class LoginResponse {
    @SerializedName("products")
    @Expose
    private List<Product> products;
    @SerializedName("categories")
    @Expose
    private List<ProductCategory> categories;
}
