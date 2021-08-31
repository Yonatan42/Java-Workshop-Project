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

    public void getPagedProducts(int pageNum,
                                String filterText,
                                Integer filterCategoryId,
                                ResponseSuccessTokennedCallback<List<Product>> onSuccess,
                                ResponseErrorCallback<TokennedResult<List<Product>>> onError){
        enqueueTokenned(service.getPagedProducts(getToken(), pageNum, filterText, filterCategoryId), onSuccess, onError);
    }

    public void insertUpdateProduct(Product product,
                                    ResponseSuccessTokennedCallback<Product> onSuccess,
                                    ResponseErrorCallback<TokennedResult<Product>> onError){
        if(product.getProductId() <= 0){
            enqueueTokenned(service.insertProduct(
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
            enqueueTokenned(service.updateProduct(getToken(), product.getProductId(), product), onSuccess, onError);
        }
    }

    public void disableProduct(int productId,
                               ResponseSuccessTokennedCallback<Integer> onSuccess,
                               ResponseErrorCallback<TokennedResult<Integer>> onError){
        enqueueTokenned(service.setProductEnabled(getToken(), productId, false), onSuccess, onError);
    }

    public void getProducts(List<Integer> productIds,
                            ResponseSuccessTokennedCallback<List<Product>> onSuccess,
                            ResponseErrorCallback<TokennedResult<List<Product>>> onError){
        enqueueTokenned(service.getProductsByIds(getToken(), productIds), onSuccess, onError);
    }


}
