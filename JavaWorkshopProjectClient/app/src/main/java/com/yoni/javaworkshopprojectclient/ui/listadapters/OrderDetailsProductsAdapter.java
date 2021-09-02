package com.yoni.javaworkshopprojectclient.ui.listadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.OrderDetailsProduct;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.OrderSummary;
import com.yoni.javaworkshopprojectclient.datatransfer.models.uimodels.ExpandableOrder;
import com.yoni.javaworkshopprojectclient.ui.ParentActivity;
import com.yoni.javaworkshopprojectclient.utils.UIUtils;

import java.util.List;

public class OrderDetailsProductsAdapter extends RecyclerView.Adapter<OrderDetailsProductsAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView txtQuantity;
        private final TextView txtPrice;
        private final TextView txtTitle;

        public ViewHolder(View itemView){
            super(itemView);

            txtQuantity = itemView.findViewById(R.id.order_details_product_cell_txt_quantity);
            txtPrice = itemView.findViewById(R.id.order_details_product_cell_txt_price);
            txtTitle = itemView.findViewById(R.id.order_details_product_cell_txt_title);
        }

    }


    private List<OrderDetailsProduct> products;


    public OrderDetailsProductsAdapter(List<OrderDetailsProduct> products){
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.cell_order_details_product, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        OrderDetailsProduct product = products.get(position);
        holder.txtQuantity.setText(Integer.toString(product.getQuantity()));
        holder.txtPrice.setText(UIUtils.formatPrice(product.getPrice(), UIUtils.getDollarSign(context)));
        holder.txtTitle.setText(product.getProductTitle());
    }


    @Override
    public int getItemCount() {
        return products.size();
    }


}
