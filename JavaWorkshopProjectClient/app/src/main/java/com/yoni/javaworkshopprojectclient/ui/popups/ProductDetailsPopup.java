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

public class ProductDetailsPopup extends AlertDialog {

    public ProductDetailsPopup(Context context, Product product){
        super(context);

        View layout = LayoutInflater.from(context).inflate(R.layout.popup_product_details, null, false);
        ImageView ivImage = layout.findViewById(R.id.products_details_popup_iv);
        TextView txtTitle = layout.findViewById(R.id.products_details_popup_txt_title);
        TextView txtDesc = layout.findViewById(R.id.products_details_popup_txt_desc);
        Button btnBack = layout.findViewById(R.id.products_details_popup_btn_back);

        txtTitle.setText(product.getTitle());
        txtDesc.setText(product.getDescription());

        String imageData = product.getImageData();
        byte[] imageByteArray = imageData != null ?
                Base64.decode(imageData, Base64.DEFAULT) :
                null;
        Glide.with(context)
                .load(imageByteArray)
                .placeholder(R.drawable.ic_product_placeholder)
                .into(ivImage);

        btnBack.setOnClickListener(v -> dismiss());

        setView(layout);
    }


}
