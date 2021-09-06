package com.yoni.javaworkshopprojectclient.ui.listadapters;

import android.app.AlertDialog;
import android.view.View;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.Product;
import com.yoni.javaworkshopprojectclient.localdatastores.DataSets;
import com.yoni.javaworkshopprojectclient.ui.ParentActivity;
import com.yoni.javaworkshopprojectclient.ui.popups.ProductDetailsAdminPopup;
import com.yoni.javaworkshopprojectclient.ui.popups.ProductDetailsPopup;
import com.yoni.javaworkshopprojectclient.utils.ListUtils;

import java.util.List;

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
                int changedIndex = ListUtils.getFirstIndexWhere(products, p -> p.getId() == changedProduct.getId());
                if (changedIndex >= 0) {
                    products.set(changedIndex, changedProduct);
                    notifyItemChanged(changedIndex);
                }
            }, deletedId -> {
                int deletedIndex = ListUtils.getFirstIndexWhere(products, p -> p.getId() == deletedId);
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
