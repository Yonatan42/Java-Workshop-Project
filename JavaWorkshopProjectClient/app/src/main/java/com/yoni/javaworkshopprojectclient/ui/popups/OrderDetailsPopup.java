package com.yoni.javaworkshopprojectclient.ui.popups;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.OrderDetails;
import com.yoni.javaworkshopprojectclient.ui.listadapters.OrderDetailsProductsAdapter;
import com.yoni.javaworkshopprojectclient.utils.UIUtils;

public class OrderDetailsPopup extends AlertDialog {

    public OrderDetailsPopup(Context context, OrderDetails order) {
        super(context);

        View layout = LayoutInflater.from(getContext()).inflate(R.layout.popup_order_details, null, false);
        Button btnBack = layout.findViewById(R.id.order_details_popup_btn_back);
        TextView txtTotalPrice = layout.findViewById(R.id.order_details_popup_txt_total);
        TextView txtOrderNum = layout.findViewById(R.id.order_details_popup_txt_order_number);
        RecyclerView rvProducts = layout.findViewById(R.id.order_details_popup_rv);


        OrderDetailsProductsAdapter adapter = new OrderDetailsProductsAdapter(order.getProducts());
        rvProducts.setAdapter(adapter);
        rvProducts.setLayoutManager(new LinearLayoutManager(context));

        txtOrderNum.setText(String.format("%s%d", context.getString(R.string.order_number_prefix), order.getId()));
        txtTotalPrice.setText(String.format("%s %s", context.getString(R.string.total_price_prefix), UIUtils.formatPrice(order.getTotalPrice(), UIUtils.getDollarSign(context))));

        btnBack.setOnClickListener(v -> dismiss());

        setView(layout);
    }
}
