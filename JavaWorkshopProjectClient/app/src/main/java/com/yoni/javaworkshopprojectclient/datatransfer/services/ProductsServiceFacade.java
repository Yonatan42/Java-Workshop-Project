package com.yoni.javaworkshopprojectclient.datatransfer.services;

import com.yoni.javaworkshopprojectclient.datatransfer.ServerResponse;
import com.yoni.javaworkshopprojectclient.datatransfer.TokennedResult;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.Product;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.ProductCategory;
import com.yoni.javaworkshopprojectclient.datatransfer.models.pureresponsemodels.LoginResponse;
import com.yoni.javaworkshopprojectclient.remote.ResponseErrorCallback;
import com.yoni.javaworkshopprojectclient.remote.ResponseSuccessTokennedCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.Header;
import retrofit2.http.Path;

public class ProductsServiceFacade extends BaseRemoteServiceFacade<ProductsService> {

    public ProductsServiceFacade(ProductsService service) {
        super(service);
    }

    public void getPagedProducts(String token,
                      int pageNum,
                      String filterText,
                      Integer filterCategoryId,
                      ResponseSuccessTokennedCallback<List<Product>> onSuccess,
                      ResponseErrorCallback<TokennedResult<List<Product>>> onError){
        enqueueTokenned(service.getPagedProducts(token, pageNum, filterText, filterCategoryId), onSuccess, onError);
    }

    public void insertUpdateProduct(String token,
                                    Product product,
                                    ResponseSuccessTokennedCallback<Product> onSuccess,
                                    ResponseErrorCallback<TokennedResult<Product>> onError){
        if(product.getProductId() <= 0){
            enqueueTokenned(service.insertProduct(
                    token,
                    product.getTitle(),
                    product.getDescription(),
                    product.getImageData(),
                    product.getCategories(),
                    product.getPrice(),
                    product.getStock()
            ), onSuccess, onError);
        }
        else{
            enqueueTokenned(service.updateProduct(token, product.getProductId(), product), onSuccess, onError);
        }
    }

    public void disableProduct(String token,
                                 int productId,
                                 ResponseSuccessTokennedCallback<Integer> onSuccess,
                                 ResponseErrorCallback<TokennedResult<Integer>> onError){
        enqueueTokenned(service.setProductEnabled(token, productId, false), onSuccess, onError);
    }

    public void getProducts(String token,
                            List<Integer> productIds,
                            ResponseSuccessTokennedCallback<List<Product>> onSuccess,
                            ResponseErrorCallback<TokennedResult<List<Product>>> onError){
        enqueueTokenned(service.getProductsByIds(token, productIds), onSuccess, onError);
    }


}
