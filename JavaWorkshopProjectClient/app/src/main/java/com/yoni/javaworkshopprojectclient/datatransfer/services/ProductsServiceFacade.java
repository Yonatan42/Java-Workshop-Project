package com.yoni.javaworkshopprojectclient.datatransfer.services;

import com.yoni.javaworkshopprojectclient.models.entitymodels.Product;
import com.yoni.javaworkshopprojectclient.models.entitymodels.ProductCategory;
import com.yoni.javaworkshopprojectclient.datatransfer.infrastructure.ResponseErrorCallback;
import com.yoni.javaworkshopprojectclient.datatransfer.infrastructure.ResponseSuccessCallback;
import com.yoni.javaworkshopprojectclient.ui.popups.Loader;
import com.yoni.javaworkshopprojectclient.utils.ListUtils;

import java.util.List;

public class ProductsServiceFacade extends BaseRemoteServiceFacade<ProductsService> {

    public ProductsServiceFacade(ProductsService service) {
        super(service);
    }

    public void getPagedProducts(int pageNum,
                                 String filterText,
                                 Integer filterCategoryId,
                                 ResponseSuccessCallback<List<Product>> onSuccess,
                                 ResponseErrorCallback<List<Product>> onError){
        getPagedProducts(pageNum, filterText, filterCategoryId, onSuccess, onError, null);
    }
    public void getPagedProducts(int pageNum,
                                 String filterText,
                                 Integer filterCategoryId,
                                 ResponseSuccessCallback<List<Product>> onSuccess,
                                 ResponseErrorCallback<List<Product>> onError,
                                 Loader loader){
        enqueue(service.getPagedProducts(getToken(), pageNum, filterText, filterCategoryId), onSuccess, onError, loader);
    }

    public void insertUpdateProduct(Product product,
                                    ResponseSuccessCallback<Product> onSuccess,
                                    ResponseErrorCallback<Product> onError){
        insertUpdateProduct(product, onSuccess, onError, null);
    }

    public void insertUpdateProduct(Product product,
                                    ResponseSuccessCallback<Product> onSuccess,
                                    ResponseErrorCallback<Product> onError,
                                    Loader loader){

        String title = product.getTitle();
        String desc = product.getDescription();
        String imageData = product.getImageData();
        List<Integer> categoryIds = ListUtils.map(product.getCategories(), ProductCategory::getId);
        float price = product.getPrice();
        int stock = product.getStock();
        if(product.getId() <= 0){
            enqueue(service.insertProduct(getToken(), title, desc, imageData, categoryIds, price, stock), onSuccess, onError, loader);
        }
        else{
            enqueue(service.updateProduct(getToken(), product.getId(), title, desc, imageData, categoryIds, price, stock), onSuccess, onError, loader);
        }
    }

    public void disableProduct(int id,
                               ResponseSuccessCallback<Integer> onSuccess,
                               ResponseErrorCallback<Integer> onError){
        disableProduct(id, onSuccess, onError, null);
    }

    public void disableProduct(int id,
                               ResponseSuccessCallback<Integer> onSuccess,
                               ResponseErrorCallback<Integer> onError,
                               Loader loader){
        enqueue(service.setProductEnabled(getToken(), id, false), onSuccess, onError, loader);
    }
    public void getProducts(List<Integer> ids,
                            ResponseSuccessCallback<List<Product>> onSuccess,
                            ResponseErrorCallback<List<Product>> onError){
        getProducts(ids, onSuccess, onError, null);
    }

    public void getProducts(List<Integer> ids,
                            ResponseSuccessCallback<List<Product>> onSuccess,
                            ResponseErrorCallback<List<Product>> onError,
                            Loader loader){
        enqueue(service.getByIds(getToken(), ids), onSuccess, onError, loader);
    }
    public void createCategory(String title,
                               ResponseSuccessCallback<ProductCategory> onSuccess,
                               ResponseErrorCallback<ProductCategory> onError){
        createCategory(title, onSuccess, onError, null);
    }

    public void createCategory(String title,
                               ResponseSuccessCallback<ProductCategory> onSuccess,
                               ResponseErrorCallback<ProductCategory> onError,
                               Loader loader){
        enqueue(service.createCategory(getToken(), title), onSuccess, onError, loader);
    }


}
