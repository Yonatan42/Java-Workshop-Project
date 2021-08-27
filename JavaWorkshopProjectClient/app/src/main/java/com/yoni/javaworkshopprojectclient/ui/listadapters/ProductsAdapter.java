package com.yoni.javaworkshopprojectclient.ui.listadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.CartProduct;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.Product;
import com.yoni.javaworkshopprojectclient.localdatastores.cart.CartStore;
import com.yoni.javaworkshopprojectclient.ui.ParentActivity;
import com.yoni.javaworkshopprojectclient.ui.popups.ProductDetailsAdminPopup;
import com.yoni.javaworkshopprojectclient.utils.GlideUtils;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView txtTitle;
        private final TextView txtPrice;
        private final EditText txtQuantity;
        private final ImageView ivImage;
        private final Button btnIncrease;
        private final Button btnDecrease;

        public ViewHolder(View itemView){
            super(itemView);

            txtTitle = itemView.findViewById(R.id.products_cell_txt_title);
            ivImage = itemView.findViewById(R.id.products_cell_iv);
            txtPrice = itemView.findViewById(R.id.products_cell_txt_price);
            txtQuantity = itemView.findViewById(R.id.products_cell_txt_quantity);
            btnIncrease = itemView.findViewById(R.id.products_cell_btn_increase);
            btnDecrease = itemView.findViewById(R.id.products_cell_btn_decrease);
        }

    }


    private List<Product> products;
    private ParentActivity parentActivity;


    public ProductsAdapter(ParentActivity parentActivity, List<Product> products){
        this.parentActivity = parentActivity;
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parentActivity).inflate(R.layout.cell_product_catalog, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);
        loadCartQuantityIfNeeded(product); // lazy load quantities from cart

        holder.txtTitle.setText(product.getTitle());
        GlideUtils.loadBase64IntoImage(product.getImageData(),
                parentActivity,
                                        R.drawable.ic_product_placeholder,
                                        holder.ivImage);
        holder.txtPrice.setText(String.format("$%.2f", product.getPrice()));
        holder.txtQuantity.setText(Integer.toString(product.getCartQuantity()));
        holder.itemView.setOnClickListener(v -> new ProductDetailsAdminPopup(parentActivity, product).show());//ProductDetailsPopup(context, product).show()); // todo - check if admin and show correct one
        holder.btnIncrease.setOnClickListener(v -> amountChangeButtonClick(holder.txtQuantity, product, true));
        holder.btnDecrease.setOnClickListener(v -> amountChangeButtonClick(holder.txtQuantity, product, false));
    }

    private void amountChangeButtonClick(EditText txtQuantity, Product product, boolean increase){
        int oldQuantity = product.getCartQuantity();
        int newQuantity;
        if(increase){
            newQuantity = ++oldQuantity;
        }
        else {
            newQuantity = --oldQuantity;
        }
        newQuantity = Math.max(newQuantity, 0);
        txtQuantity.setText(Integer.toString(newQuantity));
        product.setCartQuantity(newQuantity);
        CartStore.getInstance().set(product.getProductId(), newQuantity);
    }

    private void loadCartQuantityIfNeeded(Product product){
        if(product.getCartQuantity() == -1){
            CartProduct cartProduct = CartStore.getInstance().get(product.getProductId());
            product.setCartQuantity(cartProduct != null ? cartProduct.getQuantity() : 0);
        }
    }


    @Override
    public int getItemCount() {
        return products.size();
    }


}
