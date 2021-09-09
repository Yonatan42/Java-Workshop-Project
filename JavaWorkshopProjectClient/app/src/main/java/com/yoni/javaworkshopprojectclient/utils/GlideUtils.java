package com.yoni.javaworkshopprojectclient.utils;

import android.content.Context;
import android.util.Base64;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class GlideUtils {

    private GlideUtils(){}

    public static void loadBase64IntoImage(String base64, Context context, int placeholderRes, ImageView iv){
        byte[] imageByteArray = null;
        if(base64 != null) {
            try {
                imageByteArray = Base64.decode(base64, Base64.DEFAULT);
            }
            catch (IllegalArgumentException e){} // malformed base64 string
        }

        if(imageByteArray != null) {
            Glide.with(context)
                    .load(imageByteArray)
                    .placeholder(placeholderRes)
                    .into(iv);
        }
        else{
            iv.setImageResource(placeholderRes);
        }
    }

}
