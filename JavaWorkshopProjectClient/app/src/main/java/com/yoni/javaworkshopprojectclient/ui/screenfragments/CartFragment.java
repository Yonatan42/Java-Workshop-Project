package com.yoni.javaworkshopprojectclient.ui.screenfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.CartProduct;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.Product;
import com.yoni.javaworkshopprojectclient.localdatastores.cart.CartStore;
import com.yoni.javaworkshopprojectclient.remote.RemoteServiceManager;
import com.yoni.javaworkshopprojectclient.ui.listadapters.CartProductsAdapter;
import com.yoni.javaworkshopprojectclient.ui.popups.ErrorPopup;
import com.yoni.javaworkshopprojectclient.utils.ListUtils;
import com.yoni.javaworkshopprojectclient.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends BaseFragment {

    private final List<Product> products = new ArrayList<>();
    private RecyclerView rvProducts;
    private TextView txtTotal;
    private float totalPrice = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvProducts = view.findViewById(R.id.cart_rv);
        Button btnClear = view.findViewById(R.id.cart_btn_clear);
        Button btnCheckout = view.findViewById(R.id.cart_btn_checkout);
        txtTotal = view.findViewById(R.id.cart_txt_total);

        products.clear();

        CartProductsAdapter adapter = new CartProductsAdapter(getParentActivity(), products, this::onProductQuantityChanged, this::onProductRemoved);
        rvProducts.setAdapter(adapter);
        rvProducts.setLayoutManager(new LinearLayoutManager(getContext()));

        btnClear.setOnClickListener(v -> {
            int size = products.size();
            products.clear();
            adapter.notifyItemRangeRemoved(0, size);
            CartStore.getInstance().clear();
            totalPrice = 0;
            setTotalText();
        });

        btnCheckout.setOnClickListener(v -> {
            // todo - remove this an open checkout popup
            Toast.makeText(getParentActivity(), "checkout...", Toast.LENGTH_SHORT).show();
        });

        setTotalText();
        loadProducts();
    }

    private void loadProducts(){
        List<CartProduct> cartProducts = CartStore.getInstance().getAll();
        if(cartProducts.isEmpty()){
            return;
        }
        RemoteServiceManager.getInstance().getProductsService().getProducts(ListUtils.map(cartProducts, CartProduct::getProductId),
                (call, response, result) -> {
                    products.addAll(result);
                    rvProducts.getAdapter().notifyItemRangeInserted(0, products.size());
                    totalPrice = calculateTotalPrice();
                    setTotalText();
                },
                (call, errorResponse) -> {
                    // todo - do something else maybe
                    ErrorPopup.createGenericOneOff(getParentActivity()).show();
                });
    }

    private float calculateTotalPrice(){
        return ListUtils.reduce(products, 0f, (acc, product) -> {
            if(product.getCartQuantity() == -1){
                product.setCartQuantity(CartStore.getInstance().get(product.getProductId()).getQuantity());
            }
            return acc + product.getPrice() * product.getCartQuantity();
        });
    }

    private void setTotalText(){
        txtTotal.setText(String.format("%s %s",getString(R.string.total_price_prefix), UIUtils.formatPrice(totalPrice, UIUtils.getDollarSign(getParentActivity()))));
    }

    private void onProductRemoved(Product product){
        totalPrice -= product.getPrice() * product.getCartQuantity();
        setTotalText();
    }

    private void onProductQuantityChanged(Product product, float newQuantity, float oldQuantity){
        float diff = newQuantity - oldQuantity;
        totalPrice += product.getPrice() * diff;
        setTotalText();
    }
}
