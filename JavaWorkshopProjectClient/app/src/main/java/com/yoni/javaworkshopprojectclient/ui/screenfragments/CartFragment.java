package com.yoni.javaworkshopprojectclient.ui.screenfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.CartProduct;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.Product;
import com.yoni.javaworkshopprojectclient.localdatastores.cart.CartStore;
import com.yoni.javaworkshopprojectclient.remote.RemoteServiceManager;
import com.yoni.javaworkshopprojectclient.ui.listadapters.CartProductsAdapter;
import com.yoni.javaworkshopprojectclient.ui.listadapters.CatalogProductsAdapter;
import com.yoni.javaworkshopprojectclient.ui.popups.ErrorPopup;
import com.yoni.javaworkshopprojectclient.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends BaseFragment {

    private final List<Product> products = new ArrayList<>();
    private RecyclerView rvProducts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvProducts = view.findViewById(R.id.cart_rv);

        products.clear();

        CartProductsAdapter adapter = new CartProductsAdapter(getParentActivity(), products);
        rvProducts.setAdapter(adapter);
        rvProducts.setLayoutManager(new LinearLayoutManager(getContext()));


        loadProducts();
    }

    private void loadProducts(){
        RemoteServiceManager.getInstance().getProductsService().getProducts(ListUtils.map(CartStore.getInstance().getAll(), CartProduct::getProductId),
                (call, response, result) -> {
                    products.addAll(result);
                    rvProducts.getAdapter().notifyItemRangeInserted(0, products.size());
                },
                (call, errorResponse) -> {
                    // todo - do something else maybe
                    ErrorPopup.createGenericOneOff(getParentActivity()).show();
                });
    }
}
