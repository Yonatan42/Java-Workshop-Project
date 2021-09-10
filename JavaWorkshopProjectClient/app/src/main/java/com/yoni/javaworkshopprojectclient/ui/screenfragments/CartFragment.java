package com.yoni.javaworkshopprojectclient.ui.screenfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.CartProduct;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.Product;
import com.yoni.javaworkshopprojectclient.localdatastores.cart.CartStore;
import com.yoni.javaworkshopprojectclient.remote.RemoteServiceManager;
import com.yoni.javaworkshopprojectclient.remote.StandardResponseErrorCallback;
import com.yoni.javaworkshopprojectclient.ui.listadapters.CartProductsAdapter;
import com.yoni.javaworkshopprojectclient.ui.popups.CheckoutPopup;
import com.yoni.javaworkshopprojectclient.utils.ListUtils;
import com.yoni.javaworkshopprojectclient.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends BaseFragment {

    private final List<Product> products = new ArrayList<>();
    private RecyclerView rvProducts;
    private TextView txtNoResults;
    private TextView txtTotal;
    private Button btnClear;
    private Button btnCheckout;
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
        btnClear = view.findViewById(R.id.cart_btn_clear);
        btnCheckout = view.findViewById(R.id.cart_btn_checkout);
        txtTotal = view.findViewById(R.id.cart_txt_total);
        txtNoResults = view.findViewById(R.id.cart_txt_no_results);

        products.clear();

        CartProductsAdapter adapter = new CartProductsAdapter(getParentActivity(), products, this::onProductQuantityChanged, this::onProductRemoved);
        rvProducts.setAdapter(adapter);
        rvProducts.setLayoutManager(new LinearLayoutManager(getContext()));

        btnClear.setOnClickListener(v -> {
            clearCart();
        });

        btnCheckout.setOnClickListener(v -> new CheckoutPopup(getParentActivity(), this.getParentFragmentManager(), this::clearCart).show());

        setTotalText();
        loadProducts();
    }

    private void clearCart(){
        int size = products.size();
        products.clear();
        rvProducts.getAdapter().notifyItemRangeRemoved(0, size);
        CartStore.getInstance().clear();
        totalPrice = 0;
        setTotalText();
        setEmptyChartDisplayIfNeeded();
    }

    private void loadProducts(){
        List<CartProduct> cartProducts = CartStore.getInstance().getAll();
        if(cartProducts.isEmpty()){
            setEmptyChartDisplayIfNeeded();
            return;
        }
        RemoteServiceManager.getInstance().getProductsService().getProducts(ListUtils.map(cartProducts, CartProduct::getProductId),
                (call, response, result) -> {
                    products.addAll(result);
                    rvProducts.getAdapter().notifyItemRangeInserted(0, products.size());
                    totalPrice = calculateTotalPrice();
                    setTotalText();
                    setEmptyChartDisplayIfNeeded();
                },
                new StandardResponseErrorCallback<>(getParentActivity()),
                getParentActivity().getLoader());
    }

    private float calculateTotalPrice(){
        return ListUtils.reduce(products, 0f, (acc, product) -> {
            if(product.getCartQuantity() == -1){
                product.setCartQuantity(CartStore.getInstance().get(product.getId()).getQuantity());
            }
            return acc + product.getPrice() * product.getCartQuantity();
        });
    }

    private void setTotalText(){
        txtTotal.setText(String.format("%s %s",getString(R.string.total_price_prefix), UIUtils.formatPrice(totalPrice, UIUtils.getDollarSign(getParentActivity()))));
    }

    private void onProductRemoved(Product product){
        float price = product.getPrice() * product.getCartQuantity();
        totalPrice -= price;
        final float MIN_THRESHOLD = 0.01f;
        if(Math.abs(totalPrice) < MIN_THRESHOLD){
            totalPrice = 0;
        }
        setTotalText();
        setEmptyChartDisplayIfNeeded();
    }

    private void onProductQuantityChanged(Product product, float newQuantity, float oldQuantity){
        float diff = newQuantity - oldQuantity;
        totalPrice += product.getPrice() * diff;
        setTotalText();
        setEmptyChartDisplayIfNeeded();
    }

    private void setEmptyChartDisplayIfNeeded(){
        boolean isEmpty = products.isEmpty();
        txtNoResults.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        btnCheckout.setEnabled(!isEmpty);
        btnClear.setEnabled(!isEmpty);
    }
}
