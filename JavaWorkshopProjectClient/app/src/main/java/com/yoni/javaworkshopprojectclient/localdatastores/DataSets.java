package com.yoni.javaworkshopprojectclient.localdatastores;

import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.ProductCategory;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.User;
import com.yoni.javaworkshopprojectclient.utils.ListUtils;

import java.util.ArrayList;
import java.util.Arrays;
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

    private final User currentUser = new User();
    private final List<ProductCategory> categories = new ArrayList<>();

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        currentUser.replaceUser(user);
    }

    public List<ProductCategory> getCategories(){
        return categories;
    }

    public void setCategories(List<ProductCategory> newCategories){
        categories.clear();
        if(newCategories != null){
            categories.addAll(newCategories);
        }
    }

    public void addCategories(ProductCategory... newCategories){
        addCategories(Arrays.asList(newCategories));
    }

    public void addCategories(List<ProductCategory> newCategories){
        List<ProductCategory> mergedCategories = ListUtils.combineLists(categories, newCategories, (o1, o2) -> Integer.compare(o1.getId(), o2.getId()));
        setCategories(mergedCategories);
    }

    public void clearCategories(){
        categories.clear();
    }
}
