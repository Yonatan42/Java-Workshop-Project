package com.yoni.javaworkshopprojectclient.ui.popups;

import android.app.AlertDialog;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.Product;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.ProductCategory;
import com.yoni.javaworkshopprojectclient.utils.GlideUtils;
import com.yoni.javaworkshopprojectclient.utils.ListUtils;
import com.yoni.javaworkshopprojectclient.utils.UIUtils;

public class ProductDetailsPopup extends AlertDialog {

    public ProductDetailsPopup(Context context, Product product){
        super(context);

        View layout = LayoutInflater.from(context).inflate(R.layout.popup_product_details, null, false);
        ImageView ivImage = layout.findViewById(R.id.products_details_popup_iv);
        TextView txtTitle = layout.findViewById(R.id.products_details_popup_txt_title);
        TextView txtCategories = layout.findViewById(R.id.products_details_popup_txt_categories);
        TextView txtPrice = layout.findViewById(R.id.products_details_popup_txt_price);
        TextView txtDesc = layout.findViewById(R.id.products_details_popup_txt_desc);
        Button btnBack = layout.findViewById(R.id.products_details_popup_btn_back);

        txtTitle.setText(product.getTitle());
        txtCategories.setText(ListUtils.mapJoin(product.getCategories(), ",", ProductCategory::getTitle));
        txtDesc.setText(product.getDescription());
        txtPrice.setText(String.format("%.2f", product.getPrice()));

        GlideUtils.loadBase64IntoImage(product.getImageData(), context, R.drawable.ic_product_placeholder, ivImage);

        btnBack.setOnClickListener(v -> dismiss());

        setView(layout);
    }


}
