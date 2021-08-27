package com.yoni.javaworkshopprojectclient.ui.popups;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.Product;
import com.yoni.javaworkshopprojectclient.events.OnRequestPermissionResultListener;
import com.yoni.javaworkshopprojectclient.localdatastores.DataSets;
import com.yoni.javaworkshopprojectclient.ui.ParentActivity;
import com.yoni.javaworkshopprojectclient.utils.GlideUtils;

import java.util.Arrays;


public class ProductDetailsAdminPopup extends ProductDetailsPopup {

    private ParentActivity parentActivity;

    private Consumer<Product> onNewProductCreated;

    private ViewGroup buttonsHolder;
    private ImageButton btnEditImage;

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
        btnEditImage = layout.findViewById(R.id.products_details_popup_btn_edit_image);

        txtTitle.setHint(R.string.products_details_popup_txt_title_hint);
        txtPrice.setHint(R.string.products_details_popup_txt_price_hint);
        txtDesc.setHint(R.string.products_details_popup_txt_desc_hint);
        txtCategories.setHint(R.string.products_details_popup_txt_categories_hint);

        enableEditTexts(txtTitle, txtPrice, txtDesc, txtCategories);
        setVisibleViews(buttonsHolder, btnEditImage);

        txtCategories.setOnClickListener(v -> {
            // todo - make picker for categories (popup with add new category and recycler of all categories with checkboxes next to them)
            AlertDialog errorDialog = new ErrorPopup(parentActivity, "coming soon");
            errorDialog.setOnDismissListener(d -> {
                new CategoriesPicker(parentActivity, DataSets.getInstance().getCategories(), selectedCategories -> {
                    txtCategories.setText(getCategoriesText(selectedCategories));
                });
            });
            errorDialog.show();
        });

        btnEditImage.setOnClickListener(v -> new GetImagePopup(parentActivity, base42Image -> GlideUtils.loadBase64IntoImage(base42Image, parentActivity, R.drawable.ic_product_placeholder, ivImage)).show());
    }

    private void enableEditTexts(View... views){
        for (View txt: views){
            txt.setEnabled(true);
        }
    }

    private void setVisibleViews(View...views){
        for (View txt: views){
            txt.setVisibility(View.VISIBLE);
        }
    }

}
