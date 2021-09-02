package com.yoni.javaworkshopprojectclient.ui.listadapters;

import android.app.AlertDialog;
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
import com.yoni.javaworkshopprojectclient.ui.popups.ProductDetailsAdminPopup;
import com.yoni.javaworkshopprojectclient.ui.popups.ProductDetailsPopup;
import com.yoni.javaworkshopprojectclient.utils.GlideUtils;
import com.yoni.javaworkshopprojectclient.utils.ListUtils;
import com.yoni.javaworkshopprojectclient.utils.UIUtils;

import java.util.List;
import java.util.Locale;

public class CatalogProductsAdapter extends BaseProductsAdapter<BaseProductsAdapter.ViewHolder> {

    public CatalogProductsAdapter(ParentActivity parentActivity, List<Product> products){
        super(parentActivity, products);
    }

    @Override
    protected int getItemLayoutRes() {
        return R.layout.cell_product_catalog;
    }

    @Override
    protected ViewHolder createViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    protected void onItemClicked(Product product) {
        getDetailsPopup(product).show();
    }

    private AlertDialog getDetailsPopup(Product product){
        if(DataSets.getInstance().getCurrentUser().isAdminModeActive()) {
            return new ProductDetailsAdminPopup(parentActivity, product, changedProduct -> {
                int changedIndex = ListUtils.getFirstIndexWhere(products, p -> p.getProductId() == changedProduct.getProductId());
                if (changedIndex >= 0) {
                    products.set(changedIndex, changedProduct);
                    notifyItemChanged(changedIndex);
                }
            }, deletedProductId -> {
                int deletedIndex = ListUtils.getFirstIndexWhere(products, p -> p.getProductId() == deletedProductId);
                if (deletedIndex >= 0) {
                    products.remove(deletedIndex);
                    notifyItemRemoved(deletedIndex);
                }
            });
        }
        else{
            return new ProductDetailsPopup(parentActivity, product);
        }
    }
}
