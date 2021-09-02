package com.yoni.javaworkshopprojectclient.ui.listadapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.CartProduct;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.Product;
import com.yoni.javaworkshopprojectclient.localdatastores.DataSets;
import com.yoni.javaworkshopprojectclient.localdatastores.cart.CartStore;
import com.yoni.javaworkshopprojectclient.ui.ParentActivity;
import com.yoni.javaworkshopprojectclient.ui.customviews.Stepper;
import com.yoni.javaworkshopprojectclient.utils.GlideUtils;
import com.yoni.javaworkshopprojectclient.utils.UIUtils;

import java.util.List;

public abstract class BaseProductsAdapter<T extends BaseProductsAdapter.ViewHolder> extends RecyclerView.Adapter<T> {

    public static class ViewHolder extends RecyclerView.ViewHolder{
        protected final TextView txtTitle;
        protected final TextView txtPrice;
        protected final ImageView ivImage;
        protected final Stepper stepperCart;

        public ViewHolder(View itemView){
            super(itemView);

            txtTitle = itemView.findViewById(R.id.products_cell_txt_title);
            ivImage = itemView.findViewById(R.id.products_cell_iv);
            txtPrice = itemView.findViewById(R.id.products_cell_txt_price);
            stepperCart = itemView.findViewById(R.id.products_cell_stepper_cart);
        }

    }


    protected List<Product> products;
    protected ParentActivity parentActivity;


    public BaseProductsAdapter(ParentActivity parentActivity, List<Product> products){
        this.parentActivity = parentActivity;
        this.products = products;
    }

    protected abstract int getItemLayoutRes();

    @NonNull
    @Override
    public T onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parentActivity).inflate(getItemLayoutRes(), parent, false);
        return createViewHolder(view);
    }

    protected abstract T createViewHolder(View itemView);

    @Override
    public void onBindViewHolder(@NonNull T holder, int position) {
        Product product = products.get(position);
        loadCartQuantityIfNeeded(product); // lazy load quantities from cart

        holder.txtTitle.setText(product.getTitle());
        GlideUtils.loadBase64IntoImage(product.getImageData(),
                parentActivity,
                                        R.drawable.ic_product_placeholder,
                                        holder.ivImage);
        holder.txtPrice.setText(UIUtils.formatPrice(product.getPrice(), UIUtils.getDollarSign(parentActivity)));
        holder.itemView.setOnClickListener(v -> onItemClicked(product));

        Stepper stepperCart = holder.stepperCart;
        UIUtils.setViewsVisible(!DataSets.getInstance().getCurrentUser().isAdminModeActive(), stepperCart);
        stepperCart.setMaxValue(product.getStock());
        stepperCart.setValue(product.getCartQuantity());
        stepperCart.setOnValueChangedListener((v, newValue, oldValue) -> {
            handleAmountChange(product, newValue);
        });
    }

    protected abstract void onItemClicked(Product product);

    private void handleAmountChange(Product product, float newAmount){
        int newQuantity = (int)newAmount;
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
