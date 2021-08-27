package com.yoni.javaworkshopprojectclient.ui.popups;


import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.core.util.Consumer;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.Product;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.ProductCategory;
import com.yoni.javaworkshopprojectclient.ui.ParentActivity;
import com.yoni.javaworkshopprojectclient.utils.GlideUtils;
import com.yoni.javaworkshopprojectclient.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;


public class ProductDetailsAdminPopup extends ProductDetailsPopup {

    private ParentActivity parentActivity;

    private Consumer<Product> onNewProductCreated;

    private ViewGroup buttonsHolder;
    private ImageButton btnEditImage;
    private Button btnReset;
    private Button btnSave;
    private Button btnDelete;

    // new product
    public ProductDetailsAdminPopup(ParentActivity parentActivity, Consumer<Product> onNewProductCreated){
        super(parentActivity, null);
        this.onNewProductCreated = onNewProductCreated;
        setUp(parentActivity, null);
    }

    // edit product
    public ProductDetailsAdminPopup(ParentActivity parentActivity, Product product){
        super(parentActivity, product);
        setUp(parentActivity, product);
    }

    private void setUp(ParentActivity parentActivity, Product product){
        this.parentActivity = parentActivity;

        buttonsHolder = layout.findViewById(R.id.products_details_popup_admin_buttons_group);
        btnReset = layout.findViewById(R.id.products_details_popup_btn_reset);
        btnSave = layout.findViewById(R.id.products_details_popup_btn_save);
        btnDelete = layout.findViewById(R.id.products_details_popup_btn_delete);
        btnEditImage = layout.findViewById(R.id.products_details_popup_btn_edit_image);

        txtTitle.setHint(R.string.products_details_popup_txt_title_hint);
        txtPrice.setHint(R.string.products_details_popup_txt_price_hint);
        txtDesc.setHint(R.string.products_details_popup_txt_desc_hint);
        txtCategories.setHint(R.string.products_details_popup_txt_categories_hint);

        UIUtils.setViewsEnabled(true, txtTitle, txtPrice, txtDesc, txtCategories);
        UIUtils.setViewsVisible(true, buttonsHolder, btnEditImage);

        List<ProductCategory> selectedCategories = new ArrayList<>(product.getCategories());
        txtCategories.setOnClickListener(v -> new CategoriesPicker(parentActivity, selectedCategories, newSelectedCategories -> {
            txtCategories.setText(getCategoriesText(newSelectedCategories));
            selectedCategories.clear();
            selectedCategories.addAll(newSelectedCategories);
        }).show());

        btnEditImage.setOnClickListener(v -> new GetImagePopup(parentActivity, base42Image -> GlideUtils.loadBase64IntoImage(base42Image, parentActivity, R.drawable.ic_product_placeholder, ivImage)).show());

        btnReset.setOnClickListener(v -> {
            selectedCategories.clear();
            selectedCategories.addAll(product.getCategories());
            setViews(product);
        });
    }
}
