package com.yoni.javaworkshopprojectclient.localdatastores.cart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.CartProduct;

import java.util.ArrayList;
import java.util.List;

public class CartDB extends SQLiteOpenHelper implements CartTransactable {

    private static final String DB_NAME = "cart.db";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "cart";
    private static final String FIELD_PRODUCT_ID = "product_id";
    private static final String FIELD_QUANTITY = "quantity";

    private SQLiteDatabase db;

    public CartDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db; // when creating the db, the constructor's getWritableDatabase is null
        createTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // not upgrades at the moment
    }

    private void createTable() {
        String sqlString = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME;
        sqlString += "(";
        sqlString += FIELD_PRODUCT_ID + " INTEGER PRIMARY KEY, ";
        sqlString += FIELD_QUANTITY + " INTEGER ";
        sqlString += ")";

        db.execSQL(sqlString);
    }

    public void insert(int productId, int quantity) {
        ContentValues values = new ContentValues();
        values.put(FIELD_PRODUCT_ID, productId);
        values.put(FIELD_QUANTITY, quantity);

        db.insert(TABLE_NAME, null, values);
    }

    public List<CartProduct> getAll() {
        List<CartProduct> productList = new ArrayList<>();
        String sqlString = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(sqlString, null);

        while (cursor.moveToNext()) {
            int productId = cursor.getInt(cursor.getColumnIndex(FIELD_PRODUCT_ID));
            int quantity = cursor.getInt(cursor.getColumnIndex(FIELD_QUANTITY));
            CartProduct product = new CartProduct(productId, quantity);

            productList.add(product);
        }
        cursor.close();
        return productList;
    }

    @Override
    public CartProduct get(int productId) {
        CartProduct product = null;
        String sqlString = "SELECT * FROM " + TABLE_NAME + " WHERE "+FIELD_PRODUCT_ID+" = "+productId;

        Cursor cursor = db.rawQuery(sqlString, null);

        if (cursor.moveToNext()) {
            int quantity = cursor.getInt(cursor.getColumnIndex(FIELD_QUANTITY));
            product = new CartProduct(productId, quantity);
        }

        cursor.close();
        return product;
    }

    public void update(int productId, int quantity) {
        ContentValues values = new ContentValues();
        values.put(FIELD_QUANTITY, quantity);
        String whereClause = FIELD_PRODUCT_ID + "=" + productId;
        db.update(TABLE_NAME, values, whereClause, null);
    }


    public void delete(int productId) {
        String sqlString = "DELETE FROM " + TABLE_NAME + " WHERE " + FIELD_PRODUCT_ID + "=" + productId;
        SQLiteStatement sqLiteStatement = db.compileStatement(sqlString);
        sqLiteStatement.executeUpdateDelete();
    }

}
