package com.yoni.javaworkshopprojectclient.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.ServerResponse;
import com.yoni.javaworkshopprojectclient.datatransfer.TokennedResult;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.Product;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.ProductCategory;
import com.yoni.javaworkshopprojectclient.localdatastores.DataSets;
import com.yoni.javaworkshopprojectclient.localdatastores.TokenStore;
import com.yoni.javaworkshopprojectclient.remote.RemoteService;
import com.yoni.javaworkshopprojectclient.remote.TokennedServerCallback;
import com.yoni.javaworkshopprojectclient.ui.listadapters.ProductsAdapter;
import com.yoni.javaworkshopprojectclient.ui.popups.ErrorPopup;
import com.yoni.javaworkshopprojectclient.ui.popups.FilterProductsPopup;
import com.yoni.javaworkshopprojectclient.ui.popups.Loader;
import com.yoni.javaworkshopprojectclient.utils.ListUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ProductsFragment extends BaseFragment {

    private View view;
    private RecyclerView rvProducts;
    private List<Product> products = new ArrayList<>();

    private final Observer<List<Product>> productsObserver = new Observer<List<Product>>() {
        @Override
        public void onChanged(List<Product> productList) {
            // todo - for paging on the response we will make the larger data set and then post the entire thing
            products.clear();
            products.addAll(productList);
            rvProducts.getAdapter().notifyDataSetChanged();
        }
    };

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

        rvProducts = view.findViewById(R.id.products_rv);

        ProductsAdapter adapter = new ProductsAdapter(getContext(), products);
        rvProducts.setAdapter(adapter);
        rvProducts.setLayoutManager(new GridLayoutManager(getContext(),2));


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

        loadProducts();
    }




    private void loadProducts(){
//        Loader loader = new Loader(getContext(), "Loading Products", "please wait...");
//        loader.show();

        RemoteService.getInstance().getProductsService().getAllProducts(TokenStore.getInstance().getToken()).enqueue(new TokennedServerCallback<List<Product>>() {
            @Override
            public void onResponseSuccessTokenned(Call<ServerResponse<TokennedResult<List<Product>>>> call, Response<ServerResponse<TokennedResult<List<Product>>>> response, List<Product> result) {
//                loader.dismiss();
                // note a paged response so change the whole thing
                
                DataSets.getInstance().productsLiveData.postValue(result);
                // todo - this sort of thing for paging
//                DataSets.getInstance().productsLiveData.postValue(ListUtils.combineLists(products, result, (o1, o2) -> Integer.compare(o1.getProductId(), o2.getProductId())));
            }

            @Override
            public void onResponseError(Call<ServerResponse<TokennedResult<List<Product>>>> call, ServerResponse.ServerResponseError responseError) {
//                loader.dismiss();
                // todo - change this
                new ErrorPopup(getContext(), "death");
            }

            @Override
            public void onFailure(Call<ServerResponse<TokennedResult<List<Product>>>> call, Throwable t) {
//                loader.dismiss();
                // todo - change this
                new ErrorPopup(getContext(), "more death");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        DataSets.getInstance().productsLiveData.observe(getParentActivity(), productsObserver);
    }

    @Override
    public void onPause() {
        super.onPause();
        DataSets.getInstance().productsLiveData.removeObserver(productsObserver);
    }
}
