package com.yoni.javaworkshopprojectclient.utils;

import android.graphics.Bitmap;

public class BitmapUtils {

    public static Bitmap scaleBitmap(Bitmap originalImage, int width, int height){
        float widthRatio = (float)width / originalImage.getWidth();
        float heightRatio = (float)height / originalImage.getHeight();

        int scaleWidth;
        int scaleHeight;

        if(Math.abs(widthRatio - 1) < Math.abs(heightRatio - 1)){
            // change in height is more drastic
            scaleHeight = height;
            scaleWidth = (int)(originalImage.getWidth() * heightRatio);
        }
        else {
            // change in width is more drastic
            scaleWidth = width;
            scaleHeight = (int)(originalImage.getHeight() * widthRatio);
        }

        return Bitmap.createScaledBitmap(originalImage, scaleWidth, scaleHeight, true);
    }
}
