package com.yoni.javaworkshopprojectclient.models.pureresponsemodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.yoni.javaworkshopprojectclient.models.entitymodels.ProductCategory;
import com.yoni.javaworkshopprojectclient.models.entitymodels.User;

import java.util.List;

public class LoginResponse {
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("categories")
    @Expose
    private List<ProductCategory> categories;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ProductCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<ProductCategory> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "user=" + user +
                ", categories=" + categories +
                '}';
    }
}
