package com.yoni.javaworkshopprojectclient.ui.popups;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.Product;
import com.yoni.javaworkshopprojectclient.events.OnRequestPermissionResultListener;
import com.yoni.javaworkshopprojectclient.ui.ParentActivity;

import java.util.Arrays;


public class ProductDetailsAdminPopup extends ProductDetailsPopup {

    private ParentActivity parentActivity;

    private Consumer<Product> onNewProductCreated;

    private ViewGroup buttonsHolder;
    private ImageButton btnEditImage;

    private OnRequestPermissionResultListener onPermissionResultListener;

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

        enableEditTexts(txtTitle, txtPrice, txtDesc);
        setVisibleViews(buttonsHolder, btnEditImage);

        txtCategories.setOnClickListener(v -> {
            // todo - make picker for categories (popup with add new category and recycler of all categories with checkboxes next to them)
            AlertDialog errorDialog = new ErrorPopup(parentActivity, "coming soon");
            errorDialog.setOnDismissListener(d -> {
                txtCategories.setText("made up category - implementation coming soon");
            });
        });



         this.onPermissionResultListener = new OnRequestPermissionResultListener() {
            @Override
            public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                Toast.makeText(parentActivity, "permission result", Toast.LENGTH_SHORT).show();
                Log.i("PERMISSION_TEST", String.format("requestCode: %d, permissions: %s, grandResults: %s", requestCode, Arrays.toString(permissions), Arrays.toString(grantResults)));
            }
        };

        parentActivity.addOnPermissionsResultListener(onPermissionResultListener);

        btnEditImage.setOnClickListener(v -> {
            // todo - get image from gallery or camera, convert to base64 and display
            if(parentActivity.requestPermissions(100, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)){
                // permission was requested
            }
            else{
                // already have permission
            }
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

    @Override
    public void dismiss() {
        super.dismiss();
        parentActivity.removeOnPermissionsResultListener(onPermissionResultListener);
    }
}
