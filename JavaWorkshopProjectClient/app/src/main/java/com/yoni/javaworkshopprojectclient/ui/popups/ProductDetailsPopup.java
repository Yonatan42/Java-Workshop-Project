package com.yoni.javaworkshopprojectclient.ui.popups;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.models.entitymodels.Product;
import com.yoni.javaworkshopprojectclient.models.entitymodels.ProductCategory;
import com.yoni.javaworkshopprojectclient.utils.GlideUtils;
import com.yoni.javaworkshopprojectclient.utils.ListUtils;
import com.yoni.javaworkshopprojectclient.utils.UIUtils;

import java.util.List;

public class ProductDetailsPopup extends AlertDialog {

    protected View layout;
    protected ImageView ivImage;
    protected EditText txtTitle;
    protected EditText txtCategories;
    protected EditText txtPrice;
    protected EditText txtDesc;
    protected Button btnBack;



    public ProductDetailsPopup(Context context, Product product) {
        super(context);
        setUp(product);
    }

    private void setUp(Product product){
        layout = LayoutInflater.from(getContext()).inflate(R.layout.popup_product_details, null, false);
        ivImage = layout.findViewById(R.id.products_details_popup_iv);
        txtTitle = layout.findViewById(R.id.products_details_popup_txt_title);
        txtCategories = layout.findViewById(R.id.products_details_popup_txt_categories);
        txtPrice = layout.findViewById(R.id.products_details_popup_txt_price);
        txtDesc = layout.findViewById(R.id.products_details_popup_txt_desc);
        btnBack = layout.findViewById(R.id.products_details_popup_btn_back);

        UIUtils.setViewsEnabled(false, txtTitle, txtPrice, txtDesc, txtCategories);
        setViews(product);

        btnBack.setOnClickListener(v -> dismiss());

        setView(layout);
    }

    protected void setViews(Product product) {
        if (product != null) {

            String pTitle = product.getTitle();
            String title = pTitle != null ? pTitle : "";
            List<ProductCategory> pCategories = product.getCategories();
            String categories = pCategories != null && !pCategories.isEmpty() ? getCategoriesText(pCategories) : "";
            String pDesc = product.getDescription();
            String desc = pDesc != null ? pDesc : "";
            float pPrice = product.getPrice();
            String price = UIUtils.formatPrice(pPrice);

            txtTitle.setText(title);
            txtCategories.setText(categories);
            txtDesc.setText(desc);
            txtPrice.setText(price);

            GlideUtils.loadBase64IntoImage(product.getImageData(), getContext(), R.drawable.ic_product_placeholder, ivImage);
        }
    }

    protected String getCategoriesText(List<ProductCategory> categories){
        return ListUtils.mapJoin(categories, ", ", ProductCategory::getTitle);
    }




}
