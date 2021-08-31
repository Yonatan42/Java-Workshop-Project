package com.yoni.javaworkshopprojectclient.ui.screenfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.CartProduct;
import com.yoni.javaworkshopprojectclient.localdatastores.cart.CartStore;

import java.util.List;

public class CartFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<CartProduct> products = CartStore.getInstance().getAll();

        // todo - remove this, it's just for testing

        TextView txtData = view.findViewById(R.id.cart_txt);

        StringBuilder sb = new StringBuilder();
        for (CartProduct product: products) {
            sb.append(product.getProductId()).append("\t\t\t\t").append(product.getQuantity()).append("\n");
        }
        txtData.setText(sb);

    }
}
