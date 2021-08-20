package com.yoni.javaworkshopprojectclient.localdatastores.cart;


import android.content.Context;

import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.CartProduct;
import com.yoni.javaworkshopprojectclient.localdatastores.InitializedStore;

import java.util.List;

public class CartStore extends InitializedStore implements ICartStore{

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

    @Override
    public void delete(int productId) {
        throwIfUninitialized();
        db.delete(productId);
    }
}
