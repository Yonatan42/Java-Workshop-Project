package com.yoni.javaworkshopprojectclient.localdatastores.cart;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.CartProduct;

import java.util.ArrayList;
import java.util.List;

public interface ICartStore {
    void insert(int productId, int quantity);
    List<CartProduct> getAll();
    CartProduct get(int productId);
    void update(int productId, int quantity);
    void delete(int productId);
}
