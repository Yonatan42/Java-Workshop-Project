package com.yoni.javaworkshopprojectclient.datatransfer.services;

import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.Product;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.ProductCategory;
import com.yoni.javaworkshopprojectclient.remote.ResponseErrorCallback;
import com.yoni.javaworkshopprojectclient.remote.ResponseSuccessCallback;

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
        if(product.getId() <= 0){
            enqueue(service.insertProduct(
                    getToken(),
                    product.getTitle(),
                    product.getDescription(),
                    product.getImageData(),
                    product.getCategories(),
                    product.getPrice(),
                    product.getStock()
            ), onSuccess, onError);
        }
        else{
            enqueue(service.updateProduct(getToken(), product.getId(), product), onSuccess, onError);
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
