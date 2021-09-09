package com.yoni.javaworkshopprojectclient.ui.listadapters;

import android.app.AlertDialog;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.Product;
import com.yoni.javaworkshopprojectclient.functionalintefaces.TriConsumer;
import com.yoni.javaworkshopprojectclient.localdatastores.cart.CartStore;
import com.yoni.javaworkshopprojectclient.ui.ParentActivity;
import com.yoni.javaworkshopprojectclient.ui.popups.ProductDetailsPopup;

import java.util.List;

public class CartProductsAdapter extends BaseProductsAdapter<CartProductsAdapter.ViewHolder> {

    private final TriConsumer<Product, Float, Float> onProductQuantityChanged;
    private final Consumer<Product> onProductRemoved;

    public CartProductsAdapter(ParentActivity parentActivity, List<Product> products, TriConsumer<Product, Float, Float> onProductQuantityChanged, Consumer<Product> onProductRemoved){
        super(parentActivity, products);
        this.onProductQuantityChanged = onProductQuantityChanged;
        this.onProductRemoved = onProductRemoved;
    }

    public static class ViewHolder extends BaseProductsAdapter.ViewHolder{

        private ImageButton btnRemove;

        public ViewHolder(View itemView) {
            super(itemView);
            btnRemove = itemView.findViewById(R.id.products_cell_btn_remove);
        }
    }

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
            CartStore.getInstance().delete(product.getId());
            products.remove(product);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, products.size());
            if(onProductRemoved != null){
                onProductRemoved.accept(product);
            }
        });
    }

    @Override
    protected void onItemClicked(Product product) {
        getDetailsPopup(product).show();
    }

    private AlertDialog getDetailsPopup(Product product){
        return new ProductDetailsPopup(parentActivity, product);
    }

    @Override
    protected void handleAmountChange(Product product, float newAmount, float oldAmount) {
        super.handleAmountChange(product, newAmount, oldAmount);
        onProductQuantityChanged.accept(product, newAmount, oldAmount);
    }
}
