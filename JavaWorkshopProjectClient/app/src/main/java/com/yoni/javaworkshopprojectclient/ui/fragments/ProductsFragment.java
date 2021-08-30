package com.yoni.javaworkshopprojectclient.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.ServerResponse;
import com.yoni.javaworkshopprojectclient.datatransfer.TokennedResult;
import com.yoni.javaworkshopprojectclient.datatransfer.models.ProductFilter;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.Product;
import com.yoni.javaworkshopprojectclient.localdatastores.DataSets;
import com.yoni.javaworkshopprojectclient.localdatastores.TokenStore;
import com.yoni.javaworkshopprojectclient.remote.RemoteServiceManager;
import com.yoni.javaworkshopprojectclient.remote.ResponseErrorCallback;
import com.yoni.javaworkshopprojectclient.remote.ResponseSuccessTokennedCallback;
import com.yoni.javaworkshopprojectclient.remote.TokennedServerCallback;
import com.yoni.javaworkshopprojectclient.ui.listadapters.ProductsAdapter;
import com.yoni.javaworkshopprojectclient.ui.popups.ErrorPopup;
import com.yoni.javaworkshopprojectclient.ui.popups.FilterProductsPopup;
import com.yoni.javaworkshopprojectclient.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ProductsFragment extends BaseFragment {

    private View view;
    private RecyclerView rvProducts;
    private TextView txtNoResults;
    private List<Product> products = new ArrayList<>();
    private int currentPage = 0;
    private boolean loadInProgress = false;
    private ProductFilter productsFilter = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_products, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtNoResults = view.findViewById(R.id.products_txt_no_results);
        rvProducts = view.findViewById(R.id.products_rv);

        ProductsAdapter adapter = new ProductsAdapter(getParentActivity(), products);
        rvProducts.setAdapter(adapter);
        rvProducts.setLayoutManager(new GridLayoutManager(getContext(),2));


        FloatingActionButton fabFilter = view.findViewById(R.id.products_btn_filter);

        fabFilter.setOnClickListener(v -> {
            new FilterProductsPopup(getParentActivity(), productsFilter, DataSets.getInstance().getCategories(), newFilter -> {
                productsFilter = newFilter;
                currentPage = 0;
                int oldCount = products.size();
                products.clear();
                rvProducts.getAdapter().notifyItemRangeRemoved(0, oldCount);
                loadProducts();
            }).show();
        });

        rvProducts.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE){
                    fabFilter.show();
                }
                else{
                    fabFilter.hide();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!loadInProgress && !recyclerView.canScrollVertically(RecyclerView.SCROLL_AXIS_VERTICAL)) {
                    loadProducts();
                }
            }
        });
    }




    private void loadProducts(){
//        Loader loader = new Loader(getContext(), "Loading Products", "please wait...");
//        loader.show();
        loadInProgress = true;
        String filterText = productsFilter != null ? productsFilter.getText() : null;
        Integer filterCategoryId = productsFilter != null && productsFilter.getCategory() != null ? productsFilter.getCategory().getId() : null;
        RemoteServiceManager.getInstance().getProductsService().getPagedProducts(TokenStore.getInstance().getToken(), currentPage, filterText, filterCategoryId,
                (call, response, result) -> {
//                    loader.dismiss();
                    loadInProgress = false;
                    currentPage++;
                    int startIndex = products.size();
                    List<Product> mergedList = ListUtils.combineLists(products, result, (o1, o2) -> Integer.compare(o1.getProductId(), o2.getProductId()));
                    products.clear();
                    products.addAll(mergedList);
                    rvProducts.getAdapter().notifyItemRangeInserted(startIndex, products.size() - startIndex);

                    txtNoResults.setVisibility(products.isEmpty() ? View.VISIBLE : View.GONE);


                },
                (call, responseError) -> {
//                    loader.dismiss();
                    loadInProgress = false;
                    // todo - change this - perhaps
                    ErrorPopup.createGenericOneOff(getContext()).show();
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        int oldCount = products.size();
        products.clear();
        currentPage = 0;
        loadInProgress = false;
        productsFilter = null;
        rvProducts.getAdapter().notifyItemRangeRemoved(0, oldCount);
        loadProducts();
    }
}
