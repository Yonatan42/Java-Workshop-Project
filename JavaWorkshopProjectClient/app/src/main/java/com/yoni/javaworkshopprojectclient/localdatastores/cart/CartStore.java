package com.yoni.javaworkshopprojectclient.localdatastores.cart;


import android.content.Context;

import com.yoni.javaworkshopprojectclient.models.entitymodels.CartProduct;
import com.yoni.javaworkshopprojectclient.localdatastores.InitializedStore;

import java.util.List;

public class CartStore extends InitializedStore implements CartTransactable {

    private static CartStore instance;

    public static CartStore getInstance(){
        if(instance == null){
            synchronized (CartStore.class){
                if(instance == null){
                    instance = new CartStore();
                }
            }
        }
        return instance;
    }


    private CartDB db;

    private CartStore(){}

    @Override
    protected void privateInitialize(Context context) {
        db = new CartDB(context);
    }

    @Override
    public void insert(int productId, int quantity) {
        throwIfUninitialized();
        db.insert(productId, quantity);
    }

    @Override
    public List<CartProduct> getAll() {
        throwIfUninitialized();
        return db.getAll();
    }

    @Override
    public CartProduct get(int productId) {
        throwIfUninitialized();
        return db.get(productId);
    }

    @Override
    public void update(int productId, int quantity) {
        throwIfUninitialized();
        db.update(productId, quantity);
    }

    public void set(int productId, int quantity) {
        throwIfUninitialized();
        CartProduct product = get(productId);
        if(product == null && quantity > 0){
            insert(productId, quantity);
        }
        else if(quantity > 0 && product.getQuantity() != quantity){
            update(productId, quantity);
        }
        else { // a product exists but we want 0 quantity - so remove it from the cart
            delete(productId);
        }
    }

    @Override
    public void delete(int productId) {
        throwIfUninitialized();
        db.delete(productId);
    }

    public void clear(){
        throwIfUninitialized();
        db.clear();
    }
}
