package com.yoni.javaworkshopprojectclient.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.ServerResponse;
import com.yoni.javaworkshopprojectclient.datatransfer.TokennedResult;
import com.yoni.javaworkshopprojectclient.datatransfer.models.Product;
import com.yoni.javaworkshopprojectclient.datatransfer.models.ProductCategory;
import com.yoni.javaworkshopprojectclient.localdatastores.TokenStore;
import com.yoni.javaworkshopprojectclient.remote.RemoteService;
import com.yoni.javaworkshopprojectclient.remote.TokennedServerCallback;
import com.yoni.javaworkshopprojectclient.ui.listadapters.ProductsAdapter;
import com.yoni.javaworkshopprojectclient.ui.popups.ErrorPopup;
import com.yoni.javaworkshopprojectclient.ui.popups.FilterProductsPopup;
import com.yoni.javaworkshopprojectclient.ui.popups.Loader;

import java.util.ArrayList;
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvProducts = view.findViewById(R.id.products_rv);
        FloatingActionButton fabFilter = view.findViewById(R.id.products_btn_filter);

        fabFilter.setOnClickListener(v -> {
            /// fake categories - todo - get real ones later ///
            List<ProductCategory> categories = new ArrayList<>();
            categories.add(new ProductCategory(1, "cat1"));
            categories.add(new ProductCategory(2, "cat2"));
            categories.add(new ProductCategory(3, "cat3"));
            categories.add(new ProductCategory(4, "cat4"));
            categories.add(new ProductCategory(5, "cat5"));
            categories.add(new ProductCategory(6, "cat6"));
            categories.add(new ProductCategory(7, "cat7"));
            categories.add(new ProductCategory(8, "cat8"));
            categories.add(new ProductCategory(9, "cat9"));
            categories.add(new ProductCategory(10, "cat10"));
            categories.add(new ProductCategory(11, "cat11"));
            ////////////////////////////////////////////////////

            new FilterProductsPopup(getParentActivity(), null, categories, newFilter -> {}, () -> {}).show();
        });

        rvProducts.setOnTouchListener((v, motionEvent) -> {
            if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                fabFilter.show();
            }
            else{
                fabFilter.hide();
            }

            return false;
        });

        loadProducts(products -> {

            ProductsAdapter adapter = new ProductsAdapter(getContext(), products);
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
