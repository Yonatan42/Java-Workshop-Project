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
import com.yoni.javaworkshopprojectclient.datatransfer.ServerResponse;
import com.yoni.javaworkshopprojectclient.datatransfer.TokennedResult;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.OrderSummary;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.User;
import com.yoni.javaworkshopprojectclient.datatransfer.models.uimodels.ExpandableOrder;
import com.yoni.javaworkshopprojectclient.localdatastores.DataSets;
import com.yoni.javaworkshopprojectclient.remote.RemoteServiceManager;
import com.yoni.javaworkshopprojectclient.remote.StandardResponseErrorCallback;
import com.yoni.javaworkshopprojectclient.ui.listadapters.OrderSummariesAdapter;
import com.yoni.javaworkshopprojectclient.utils.ListUtils;
import com.yoni.javaworkshopprojectclient.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class OrdersFragment extends BaseFragment {

    private RecyclerView rvOrders;
    private TextView txtUserId;
    private TextView txtNoResults;
    private ViewGroup layoutAdmin;
    private Button btnSearch;
    private final List<ExpandableOrder> orders = new ArrayList<>();
    private int currentPage = 0;
    private boolean loadInProgress = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvOrders = view.findViewById(R.id.orders_rv);
        layoutAdmin = view.findViewById(R.id.orders_admin_layout);
        btnSearch = view.findViewById(R.id.orders_btn_search_user);
        txtUserId = view.findViewById(R.id.orders_txt_userid);
        txtNoResults = view.findViewById(R.id.orders_txt_no_results);

        OrderSummariesAdapter adapter = new OrderSummariesAdapter(getParentActivity(), orders);
        rvOrders.setAdapter(adapter);
        rvOrders.setLayoutManager(new LinearLayoutManager(getParentActivity()));

        UIUtils.setViewsVisible(DataSets.getInstance().getCurrentUser().isAdminModeActive(), layoutAdmin);

        rvOrders.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!loadInProgress && !recyclerView.canScrollVertically(RecyclerView.SCROLL_AXIS_VERTICAL)) {
                    loadOrders();
                }
            }
        });

        btnSearch.setOnClickListener(v -> {
            int userId = UIUtils.tryGetIntValue(txtUserId, -1);
            if(userId < 0){
                Toast.makeText(getParentActivity(), getString(R.string.invalid_search_user_id), Toast.LENGTH_SHORT).show();
                return;
            }
            currentPage = 0;
            int oldCount = orders.size();
            orders.clear();
            adapter.notifyItemRangeRemoved(0, oldCount);
            loadOrders();
            txtUserId.setText("");
        });

        initialOrdersLoad();
    }

    private void setLoadInProgress(boolean isInProgress){
        loadInProgress = isInProgress;
        btnSearch.setEnabled(!isInProgress);
    }

    private void loadOrders(){
        User currentUser = DataSets.getInstance().getCurrentUser();
        int filterUserId = currentUser.isAdminModeActive() ? UIUtils.tryGetIntValue(txtUserId, -1) : currentUser.getId();
        if(filterUserId < 0){
            return;
        }
        setLoadInProgress(true);
        RemoteServiceManager.getInstance().getOrdersService().getPagedOrderSummaries(filterUserId, currentPage,
                (call, response, result) -> {
                    setLoadInProgress(false);
                    currentPage++;
                    int startIndex = orders.size();
                    List<ExpandableOrder> mergedList = ListUtils.combineLists(orders, ExpandableOrder.fromOrderSummaries(result), (o1, o2) -> o1.getOrderSummary().getTransactionDate().compareTo(o2.getOrderSummary().getTransactionDate()));
                    orders.clear();
                    orders.addAll(mergedList);
                    rvOrders.getAdapter().notifyItemRangeInserted(startIndex, orders.size() - startIndex);

                    txtNoResults.setVisibility(orders.isEmpty() ? View.VISIBLE : View.GONE);


                },
                new StandardResponseErrorCallback<TokennedResult<List<OrderSummary>>>(getParentActivity()) {
                    @Override
                    public void onPreErrorResponse() {
                        setLoadInProgress(false);
                    }

                    @Override
                    public void onUnhandledResponseError(@NonNull Call<ServerResponse<TokennedResult<List<OrderSummary>>>> call, ServerResponse.ServerResponseError responseError) {
                        // todo - check the error code - we want to know if the user id doesn't exist
                    }
                });
    }

    private void initialOrdersLoad() {
        int oldCount = orders.size();
        orders.clear();
        currentPage = 0;
        setLoadInProgress(false);
        rvOrders.getAdapter().notifyItemRangeRemoved(0, oldCount);
        loadOrders();
    }

}
