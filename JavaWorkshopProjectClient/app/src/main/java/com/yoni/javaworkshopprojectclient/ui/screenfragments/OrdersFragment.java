package com.yoni.javaworkshopprojectclient.ui.screenfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.OrderSummary;
import com.yoni.javaworkshopprojectclient.datatransfer.models.uimodels.ExpandableOrder;
import com.yoni.javaworkshopprojectclient.localdatastores.DataSets;
import com.yoni.javaworkshopprojectclient.ui.listadapters.OrderSummariesAdapter;
import com.yoni.javaworkshopprojectclient.utils.UIUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrdersFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvOrders = view.findViewById(R.id.orders_rv);
        ViewGroup layoutAdmin = view.findViewById(R.id.orders_admin_layout);
        Button btnSearch = view.findViewById(R.id.orders_btn_search_user);
        EditText txtUserId = view.findViewById(R.id.orders_txt_userid);
        TextView txtNoResults = view.findViewById(R.id.orders_txt_no_results);

        OrderSummariesAdapter adapter = new OrderSummariesAdapter(getParentActivity(), fakeOrders());
        rvOrders.setAdapter(adapter);
        rvOrders.setLayoutManager(new LinearLayoutManager(getParentActivity()));

        UIUtils.setViewsVisible(DataSets.getInstance().getCurrentUser().isAdminModeActive(), layoutAdmin);

        // todo - add paging for the orders
    }

    // todo - delete this and get data from server
    private List<ExpandableOrder> fakeOrders(){
        List<ExpandableOrder> orders = new ArrayList<>();
        for (int i = 1; i <= 20; i++ ) {
            orders.add(new ExpandableOrder(new OrderSummary(
                    i,
                    1,
                    "fname"+i+" "+"lname"+i,
                    "email"+i+"@mail.mail",
                    String.format("%d%d%d%d%d%d%d%d%d", i, i, i, i, i, i, i, i, i),
                    "the place "+i+"\nsome more place no."+i,
                    4.59f*i,
                    new Date(new Date().getTime()+(i*1000*60*60*24)))));
        }
        return orders;
    }
}
