package com.yoni.javaworkshopprojectclient.datatransfer.services;

import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.Product;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.ProductCategory;
import com.yoni.javaworkshopprojectclient.remote.ResponseErrorCallback;
import com.yoni.javaworkshopprojectclient.remote.ResponseSuccessCallback;
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
        enqueue(service.getPagedProducts(getToken(), pageNum, filterText, filterCategoryId), onSuccess, onError);
    }

    public void insertUpdateProduct(Product product,
                                    ResponseSuccessCallback<Product> onSuccess,
                                    ResponseErrorCallback<Product> onError){

        String title = product.getTitle();
        String desc = product.getDescription();
        String imageData = product.getImageData();
        List<Integer> categoryIds = ListUtils.map(product.getCategories(), ProductCategory::getId);
        float price = product.getPrice();
        int stock = product.getStock();
        if(product.getId() <= 0){
            enqueue(service.insertProduct(getToken(), title, desc, imageData, categoryIds, price, stock), onSuccess, onError);
        }
        else{
            enqueue(service.updateProduct(getToken(), product.getId(), title, desc, imageData, categoryIds, price, stock), onSuccess, onError);
        }
    }

    public void disableProduct(int id,
                               ResponseSuccessCallback<Integer> onSuccess,
                               ResponseErrorCallback<Integer> onError){
        enqueue(service.setProductEnabled(getToken(), id, false), onSuccess, onError);
    }

    public void getProducts(List<Integer> ids,
                            ResponseSuccessCallback<List<Product>> onSuccess,
                            ResponseErrorCallback<List<Product>> onError){
        enqueue(service.getByIds(getToken(), ids), onSuccess, onError);
    }

    public void createCategory(String title,
                               ResponseSuccessCallback<ProductCategory> onSuccess,
                            ResponseErrorCallback<ProductCategory> onError){
        enqueue(service.createCategory(getToken(), title), onSuccess, onError);
    }


}
