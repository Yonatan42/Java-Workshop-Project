package com.yoni.javaworkshopprojectclient.localdatastores.cart;

import com.yoni.javaworkshopprojectclient.models.entitymodels.CartProduct;

import java.util.List;

public interface CartTransactable {
    void insert(int productId, int quantity);
    List<CartProduct> getAll();
    CartProduct get(int productId);
    void update(int productId, int quantity);
    void delete(int productId);
    void clear();
}
