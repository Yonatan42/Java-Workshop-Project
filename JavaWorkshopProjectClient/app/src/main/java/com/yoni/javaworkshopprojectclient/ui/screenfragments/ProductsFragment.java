package com.yoni.javaworkshopprojectclient.ui.screenfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.OrderDetails;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.Product;
import com.yoni.javaworkshopprojectclient.localdatastores.DataSets;
import com.yoni.javaworkshopprojectclient.remote.RemoteServiceManager;
import com.yoni.javaworkshopprojectclient.remote.StandardResponseErrorCallback;
import com.yoni.javaworkshopprojectclient.ui.listadapters.CatalogProductsAdapter;
import com.yoni.javaworkshopprojectclient.ui.popups.ErrorPopup;
import com.yoni.javaworkshopprojectclient.ui.popups.FilterProductsPopup;
import com.yoni.javaworkshopprojectclient.ui.popups.ProductDetailsAdminPopup;
import com.yoni.javaworkshopprojectclient.utils.ListUtils;
import com.yoni.javaworkshopprojectclient.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class ProductsFragment extends BaseFragment {

    private Button btnNew;
    private RecyclerView rvProducts;
    private TextView txtNoResults;
    private final List<Product> products = new ArrayList<>();
    private int currentPage = 0;
    private boolean loadInProgress = false;
    private ProductFilter productsFilter = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_products, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtNoResults = view.findViewById(R.id.products_txt_no_results);
        rvProducts = view.findViewById(R.id.products_rv);
        btnNew = view.findViewById(R.id.products_btn_new);
        FloatingActionButton fabFilter = view.findViewById(R.id.products_btn_filter);

        CatalogProductsAdapter adapter = new CatalogProductsAdapter(getParentActivity(), products);
        rvProducts.setAdapter(adapter);
        rvProducts.setLayoutManager(new GridLayoutManager(getContext(),2));

        UIUtils.setViewsVisible(DataSets.getInstance().getCurrentUser().isAdminModeActive(), btnNew);
        btnNew.setOnClickListener(v -> new ProductDetailsAdminPopup(getParentActivity(), newProduct -> {
            products.add(newProduct);
            adapter.notifyItemInserted(products.size()-2);
        }).show());

        fabFilter.setOnClickListener(v -> {
            new FilterProductsPopup(getParentActivity(), productsFilter, DataSets.getInstance().getCategories(), newFilter -> {
                productsFilter = newFilter;
                currentPage = 0;
                int oldCount = products.size();
                products.clear();
                adapter.notifyItemRangeRemoved(0, oldCount);
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

        initialProductsLoad();
    }




    private void loadProducts(){
//        Loader loader = new Loader(getContext(), "Loading Products", "please wait...");
//        loader.show();
        loadInProgress = true;
        String filterText = productsFilter != null ? productsFilter.getText() : null;
        Integer filterCategoryId = productsFilter != null && productsFilter.getCategory() != null ? productsFilter.getCategory().getId() : null;
        RemoteServiceManager.getInstance().getProductsService().getPagedProducts(currentPage, filterText, filterCategoryId,
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
                new StandardResponseErrorCallback<TokennedResult<List<Product>>>(getParentActivity()) {
                    @Override
                    public void onPreErrorResponse() {
                        loadInProgress = false;
                    }
                });
    }


    private void initialProductsLoad() {
        int oldCount = products.size();
        products.clear();
        currentPage = 0;
        loadInProgress = false;
        productsFilter = null;
        rvProducts.getAdapter().notifyItemRangeRemoved(0, oldCount);
        loadProducts();
    }
}
