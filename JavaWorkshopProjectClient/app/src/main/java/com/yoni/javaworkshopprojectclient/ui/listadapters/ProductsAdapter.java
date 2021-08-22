package com.yoni.javaworkshopprojectclient.ui.listadapters;

import android.content.Context;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.Product;
import com.yoni.javaworkshopprojectclient.localdatastores.cart.CartStore;
import com.yoni.javaworkshopprojectclient.ui.popups.ProductDetailsPopup;
import com.yoni.javaworkshopprojectclient.utils.InputUtils;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView txtTitle;
        private final TextView txtQuantity;
        private final ImageView ivImage;
        private final Button btnIncrease;
        private final Button btnDecrease;
        private final Button btnAdd;

        public ViewHolder(View itemView){
            super(itemView);

            txtTitle = itemView.findViewById(R.id.products_cell_txt_title);
            ivImage = itemView.findViewById(R.id.products_cell_iv);
            txtQuantity = itemView.findViewById(R.id.products_cell_txt_quantity);
            btnIncrease = itemView.findViewById(R.id.products_cell_btn_increase);
            btnDecrease = itemView.findViewById(R.id.products_cell_btn_decrease);
            btnAdd = itemView.findViewById(R.id.products_cell_btn_add);
        }

    }


    private List<Product> products;
    private Context context;


    public ProductsAdapter(Context context, List<Product> products){
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.cell_product_catalog, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);

        holder.txtTitle.setText(product.getTitle());

        String imageData = product.getImageData();
        byte[] imageByteArray = imageData != null ?
                Base64.decode(imageData, Base64.DEFAULT) :
                null;
        Glide.with(context)
                .load(imageByteArray)
                .placeholder(R.drawable.ic_product_placeholder)
                .into(holder.ivImage);

        holder.txtQuantity.setText("1");

        holder.itemView.setOnClickListener(v -> {
            new ProductDetailsPopup(context, product).show();
        });

        holder.btnIncrease.setOnClickListener(v -> {
            String quantityText = holder.txtQuantity.getText().toString().trim();
            int quantity = InputUtils.tryParseInt(quantityText, 1);
            holder.txtQuantity.setText(quantity + 1);
        });

        holder.btnDecrease.setOnClickListener(v -> {
            String quantityText = holder.txtQuantity.getText().toString().trim();
            int quantity = InputUtils.tryParseInt(quantityText, 1);
            if(quantity > 1) {
                holder.txtQuantity.setText(quantity - 1);
            }
        });

        holder.btnAdd.setOnClickListener(v -> {
            String quantityText = holder.txtQuantity.getText().toString().trim();
            int quantity = InputUtils.tryParseInt(quantityText, 1);
            holder.txtQuantity.setText(quantity); // make sure the user sees what is being added
            CartStore.getInstance().update(product.getProductId(), quantity);
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }


}
