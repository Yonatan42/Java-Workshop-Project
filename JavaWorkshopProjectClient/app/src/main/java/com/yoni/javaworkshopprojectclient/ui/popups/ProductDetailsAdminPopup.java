package com.yoni.javaworkshopprojectclient.ui.popups;


import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.core.util.Consumer;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.Product;
import com.yoni.javaworkshopprojectclient.ui.ParentActivity;


public class ProductDetailsAdminPopup extends ProductDetailsPopup {

    private Consumer<Product> onNewProductCreated;

    private ViewGroup buttonsHolder;
    private ImageButton btnEditImage;

    // new product
    public ProductDetailsAdminPopup(ParentActivity parentActivity, Consumer<Product> onNewProductCreated){
        super(parentActivity, null);
        this.onNewProductCreated = onNewProductCreated;
    }

    // edit product
    public ProductDetailsAdminPopup(ParentActivity parentActivity, Product product){
        super(parentActivity, product);
    }

    private ParentActivity getParentActivity(){
        return (ParentActivity) getContext();
    }

    @Override
    protected void setUp(){
        buttonsHolder = layout.findViewById(R.id.products_details_popup_admin_buttons_group);
        btnEditImage = layout.findViewById(R.id.products_details_popup_btn_edit_image);

        txtTitle.setHint(R.string.products_details_popup_txt_title_hint);
        txtPrice.setHint(R.string.products_details_popup_txt_price_hint);
        txtDesc.setHint(R.string.products_details_popup_txt_desc_hint);
        txtCategories.setHint(R.string.products_details_popup_txt_categories_hint);

        enableEditTexts(txtTitle, txtPrice, txtDesc);
        setVisibleViews(buttonsHolder, btnEditImage);

        txtCategories.setOnClickListener(v -> {
            // todo - make picker for categories (popup with add new category and recycler of all categories with checkboxes next to them)
            AlertDialog errorDialog = new ErrorPopup(getParentActivity(), "coming soon");
            errorDialog.setOnDismissListener(d -> {
                txtCategories.setText("made up category - implementation coming soon");
            });
        });

        btnEditImage.setOnClickListener(v -> {
            // todo - get image from gallery or camera, convert to base64 and display
            Toast.makeText(getParentActivity(), "camera/gallery coming soon", Toast.LENGTH_SHORT).show();
        });
    }

    private void enableEditTexts(EditText... editTexts){
        for (EditText txt: editTexts){
            txt.setEnabled(true);
        }
    }

    private void setVisibleViews(View...views){
        for (View txt: views){
            txt.setVisibility(View.VISIBLE);
        }
    }


}
