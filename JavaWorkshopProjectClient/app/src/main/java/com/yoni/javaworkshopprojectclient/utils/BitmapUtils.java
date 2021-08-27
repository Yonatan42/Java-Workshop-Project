package com.yoni.javaworkshopprojectclient.utils;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.yoni.javaworkshopprojectclient.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BitmapUtils {

    private static final String TAG = "BitmapUtils";


    public static String readBase64ImageFromUri(ContentResolver resolver, Uri uri, int maxWidth, int maxHeight){
        Bitmap bitmap;

        try {
            Bitmap originalBitmap = MediaStore.Images.Media.getBitmap(resolver, uri);
            bitmap = BitmapUtils.scaleBitmap(originalBitmap, maxWidth, maxHeight);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            String base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT);

            return base64Image;
        } catch (IOException e) {
            Log.e(TAG, "readFromUri() file not found", e);
            return null;
        }
    }

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
