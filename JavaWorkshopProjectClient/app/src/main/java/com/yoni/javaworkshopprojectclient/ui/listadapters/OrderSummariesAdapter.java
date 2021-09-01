package com.yoni.javaworkshopprojectclient.ui.listadapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.RecyclerView;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.OrderSummary;
import com.yoni.javaworkshopprojectclient.datatransfer.models.uimodels.ExpandableOrder;
import com.yoni.javaworkshopprojectclient.ui.ParentActivity;
import com.yoni.javaworkshopprojectclient.utils.UIUtils;

import java.util.List;

public class OrderSummariesAdapter extends RecyclerView.Adapter<OrderSummariesAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView txtDate;
        private final TextView txtPrice;
        private final TextView txtName;
        private final TextView txtPhone;
        private final TextView txtAddress;
        private final TextView txtEmail;
        private final Button btnDetails;
        private final Group grpExpand;

        public ViewHolder(View itemView){
            super(itemView);

            txtDate = itemView.findViewById(R.id.order_summary_cell_txt_date);
            txtPrice = itemView.findViewById(R.id.order_summary_cell_txt_price);
            txtName = itemView.findViewById(R.id.order_summary_cell_txt_name);
            txtPhone = itemView.findViewById(R.id.order_summary_cell_txt_phone);
            txtAddress = itemView.findViewById(R.id.order_summary_cell_txt_address);
            txtEmail = itemView.findViewById(R.id.order_summary_cell_txt_email);
            btnDetails = itemView.findViewById(R.id.order_summary_cell_btn_details);
            grpExpand = itemView.findViewById(R.id.order_summary_cell_grp_expand);
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
        View view = LayoutInflater.from(parentActivity).inflate(R.layout.cell_order_summary, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExpandableOrder order = orders.get(position);
        OrderSummary orderSummary = order.getOrderSummary();
        holder.txtDate.setText(UIUtils.formatDate(orderSummary.getTransactionDate()));
        holder.txtPrice.setText(UIUtils.formatPrice(orderSummary.getTotalPrice(), UIUtils.getDollarSign(parentActivity)));
        holder.txtName.setText(orderSummary.getFullName());
        holder.txtPhone.setText(orderSummary.getPhone());
        holder.txtAddress .setText(orderSummary.getAddress());
        holder.txtEmail.setText(orderSummary.getEmail());
        UIUtils.setViewsVisible(order.isExpanded(), holder.grpExpand);
        holder.btnDetails.setOnClickListener(v -> {
            // todo - open order details popup
            // todo - remove this
            Toast.makeText(parentActivity, "details....", Toast.LENGTH_SHORT).show();
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
