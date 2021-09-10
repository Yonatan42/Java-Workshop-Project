package com.yoni.javaworkshopprojectclient.ui.listadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.ServerResponse;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.OrderDetails;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.OrderSummary;
import com.yoni.javaworkshopprojectclient.datatransfer.models.uimodels.ExpandableOrder;
import com.yoni.javaworkshopprojectclient.remote.ErrorCodes;
import com.yoni.javaworkshopprojectclient.remote.RemoteServiceManager;
import com.yoni.javaworkshopprojectclient.remote.StandardResponseErrorCallback;
import com.yoni.javaworkshopprojectclient.ui.ParentActivity;
import com.yoni.javaworkshopprojectclient.ui.popups.ErrorPopup;
import com.yoni.javaworkshopprojectclient.ui.popups.OrderDetailsPopup;
import com.yoni.javaworkshopprojectclient.utils.UIUtils;

import java.util.List;

import retrofit2.Call;

public class OrderSummariesAdapter extends RecyclerView.Adapter<OrderSummariesAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView txtOrderNumber;
        private final TextView txtDate;
        private final TextView txtPrice;
        private final TextView txtName;
        private final TextView txtPhone;
        private final TextView txtAddress;
        private final TextView txtEmail;
        private final Button btnDetails;
        private final ViewGroup grpExpand;

        public ViewHolder(View itemView){
            super(itemView);

            txtOrderNumber = itemView.findViewById(R.id.order_summary_cell_txt_order_number);
            txtDate = itemView.findViewById(R.id.order_summary_cell_txt_date);
            txtPrice = itemView.findViewById(R.id.order_summary_cell_txt_price);
            txtName = itemView.findViewById(R.id.order_summary_cell_txt_name);
            txtPhone = itemView.findViewById(R.id.order_summary_cell_txt_phone);
            txtAddress = itemView.findViewById(R.id.order_summary_cell_txt_address);
            txtEmail = itemView.findViewById(R.id.order_summary_cell_txt_email);
            btnDetails = itemView.findViewById(R.id.order_summary_cell_btn_details);
            grpExpand = itemView.findViewById(R.id.order_summary_cell_expand_layout);
        }

    }


    private List<ExpandableOrder> orders;
    private ParentActivity parentActivity;


    public OrderSummariesAdapter(ParentActivity parentActivity, List<ExpandableOrder> orders){
        this.parentActivity = parentActivity;
        this.orders = orders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.cell_order_summary, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        ExpandableOrder order = orders.get(position);
        OrderSummary orderSummary = order.getOrderSummary();
        holder.txtOrderNumber.setText(String.format("%s%d", context.getString(R.string.order_number_prefix), orderSummary.getId()));
        holder.txtDate.setText(UIUtils.formatDate(orderSummary.getTransactionDate()));
        holder.txtPrice.setText(UIUtils.formatPrice(orderSummary.getTotalPrice(), UIUtils.getDollarSign(context)));
        String fullName = orderSummary.getLastName() + ", " + orderSummary.getFirstName();
        holder.txtName.setText(fullName);
        holder.txtPhone.setText(orderSummary.getPhone());
        holder.txtAddress .setText(orderSummary.getAddress());
        holder.txtEmail.setText(orderSummary.getEmail());
        UIUtils.setViewsVisible(order.isExpanded(), holder.grpExpand);
        holder.btnDetails.setOnClickListener(v -> {
            ViewGroup parent = (ViewGroup) holder.itemView.getParent();
            parent.setEnabled(false);
            RemoteServiceManager.getInstance().getOrdersService().getOrderDetails(orderSummary.getId(),
                    (call, response, result) -> {
                        parent.setEnabled(true);
                        new OrderDetailsPopup(context, result).show();
                    },
                    new StandardResponseErrorCallback<OrderDetails>(parentActivity) {
                        @Override
                        public void onPreErrorResponse() {
                            parent.setEnabled(true);
                        }

                        @Override
                        public void onUnhandledResponseError(@NonNull Call<ServerResponse<OrderDetails>> call, ServerResponse.ServerResponseError responseError) {
                            String errorMessage;
                            switch (responseError.getCode()){
                                case ErrorCodes.RESOURCES_NOT_FOUND:
                                    errorMessage = parentActivity.getString(R.string.error_no_order_found);
                                    break;
                                default:
                                    super.onUnhandledResponseError(call, responseError);
                                    return;
                            }
                            ErrorPopup.createGenericOneOff(parentActivity, errorMessage).show();                        }
                    });
        });

        holder.itemView.setOnClickListener(v -> {
            order.setExpanded(!order.isExpanded());
            notifyItemChanged(position);
        });

    }


    @Override
    public int getItemCount() {
        return orders.size();
    }


}
