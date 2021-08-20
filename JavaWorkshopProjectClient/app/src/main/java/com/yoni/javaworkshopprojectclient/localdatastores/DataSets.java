package com.yoni.javaworkshopprojectclient.localdatastores;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.Product;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.ProductCategory;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.User;

import java.util.ArrayList;
import java.util.List;

public class DataSets {
    private static DataSets instance;

    public static DataSets getInstance(){
        if(instance == null){
            synchronized (DataSets.class){
                if(instance == null){
                    instance = new DataSets();
                }
            }
        }
        return instance;
    }

    private DataSets(){}

    public final MutableLiveData<User> userLiveData = new MutableLiveData<>();
    public final MutableLiveData<List<Product>> productsLiveData = new MutableLiveData<>(new ArrayList<>());
    public final MutableLiveData<List<ProductCategory>> categoriesLiveData = new MutableLiveData<>(new ArrayList<>());



}
