package com.yoni.javaworkshopprojectclient.ui.listadapters;

import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.Product;
import com.yoni.javaworkshopprojectclient.localdatastores.DataSets;
import com.yoni.javaworkshopprojectclient.localdatastores.cart.CartStore;
import com.yoni.javaworkshopprojectclient.ui.ParentActivity;
import com.yoni.javaworkshopprojectclient.ui.popups.ProductDetailsAdminPopup;
import com.yoni.javaworkshopprojectclient.ui.popups.ProductDetailsPopup;
import com.yoni.javaworkshopprojectclient.utils.ListUtils;

import java.util.List;

public class CartProductsAdapter extends BaseProductsAdapter<CartProductsAdapter.ViewHolder> {

    public CartProductsAdapter(ParentActivity parentActivity, List<Product> products){
        super(parentActivity, products);
    }

    public static class ViewHolder extends BaseProductsAdapter.ViewHolder{

        private Button btnRemove;

        public ViewHolder(View itemView) {
            super(itemView);
            btnRemove = itemView.findViewById(R.id.products_cell_btn_remove);
        }
    }

    // todo - current the layout is the same as the catalog, needs to be changed
    @Override
    protected int getItemLayoutRes() {
        return R.layout.cell_product_cart;
    }

    @Override
    protected ViewHolder createViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Product product = products.get(position);
        holder.btnRemove.setOnClickListener(v -> {
            CartStore.getInstance().delete(product.getProductId());
            notifyItemRemoved(position);
        });
    }

    @Override
    protected void onItemClicked(Product product) {
        getDetailsPopup(product).show();
    }

    private AlertDialog getDetailsPopup(Product product){
        return new ProductDetailsPopup(parentActivity, product);
    }
}
