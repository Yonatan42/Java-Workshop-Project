package com.yoni.javaworkshopprojectclient.ui.popups;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.OrderDetails;
import com.yoni.javaworkshopprojectclient.remote.RemoteServiceManager;
import com.yoni.javaworkshopprojectclient.ui.listadapters.OrderDetailsProductsAdapter;

public class OrderDetailsPopup extends AlertDialog {

    public OrderDetailsPopup(Context context, OrderDetails order) {
        super(context);

        View layout = LayoutInflater.from(getContext()).inflate(R.layout.popup_order_details, null, false);
        Button btnBack = layout.findViewById(R.id.order_details_popup_btn_back);
        RecyclerView rvProducts = layout.findViewById(R.id.order_details_popup_rv);


        OrderDetailsProductsAdapter adapter = new OrderDetailsProductsAdapter(order.getProducts());
        rvProducts.setAdapter(adapter);
        rvProducts.setLayoutManager(new LinearLayoutManager(context));


        // todo - this is not done - need the total price txt and any others

        btnBack.setOnClickListener(v -> dismiss());

        setView(layout);
    }
}
