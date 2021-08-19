package com.yoni.javaworkshopprojectclient.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.ServerResponse;
import com.yoni.javaworkshopprojectclient.datatransfer.TokennedResult;
import com.yoni.javaworkshopprojectclient.datatransfer.models.Product;
import com.yoni.javaworkshopprojectclient.localdatastores.TokenStore;
import com.yoni.javaworkshopprojectclient.remote.RemoteService;
import com.yoni.javaworkshopprojectclient.remote.TokennedServerCallback;
import com.yoni.javaworkshopprojectclient.ui.listadapters.ProductsAdapter;
import com.yoni.javaworkshopprojectclient.ui.popups.ErrorPopup;
import com.yoni.javaworkshopprojectclient.ui.popups.Loader;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ProductsFragment extends BaseFragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_products, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvProducts = view.findViewById(R.id.products_rv);

        loadProducts(products -> {

            ProductsAdapter adapter = new ProductsAdapter(products);
            rvProducts.setAdapter(adapter);
            rvProducts.setLayoutManager(new GridLayoutManager(getContext(),2));
        });


    }


    private void loadProducts(Consumer<List<Product>> callback){
        Loader loader = new Loader(getContext(), "Loading Products", "please wait...");
        loader.show();

        RemoteService.getInstance().getProductsService().getAllProducts(TokenStore.getInstance().getToken()).enqueue(new TokennedServerCallback<List<Product>>() {
            @Override
            public void onResponseSuccessTokenned(Call<ServerResponse<TokennedResult<List<Product>>>> call, Response<ServerResponse<TokennedResult<List<Product>>>> response, List<Product> result) {
                loader.dismiss();
                callback.accept(result);
            }

            @Override
            public void onResponseError(Call<ServerResponse<TokennedResult<List<Product>>>> call, ServerResponse.ServerResponseError responseError) {
                loader.dismiss();
                // todo - change this
                new ErrorPopup(getContext(), "death");
            }

            @Override
            public void onFailure(Call<ServerResponse<TokennedResult<List<Product>>>> call, Throwable t) {
                loader.dismiss();
                // todo - change this
                new ErrorPopup(getContext(), "more death");
            }
        });
    }
}
