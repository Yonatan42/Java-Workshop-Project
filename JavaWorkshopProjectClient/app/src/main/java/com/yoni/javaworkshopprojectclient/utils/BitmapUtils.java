package com.yoni.javaworkshopprojectclient.utils;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.exifinterface.media.ExifInterface;

import com.yoni.javaworkshopprojectclient.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BitmapUtils {

    private static final String TAG = "BitmapUtils";

    private BitmapUtils(){}

    public static String readBase64ImageFromUri(ContentResolver resolver, Uri uri, int maxWidth, int maxHeight){
        Bitmap bitmap;

        try {
            Bitmap originalBitmap = MediaStore.Images.Media.getBitmap(resolver, uri);
            originalBitmap = setOrientation(resolver, originalBitmap, uri);
            bitmap = BitmapUtils.scaleBitmap(originalBitmap, maxWidth, maxHeight);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
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

    private static Bitmap setOrientation(ContentResolver resolver, Bitmap bitmap, Uri uri) {
        InputStream inputStream = null;
        try {
            inputStream = resolver.openInputStream(uri);
            ExifInterface ei = new ExifInterface(inputStream);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            Bitmap rotatedBitmap = null;
            switch (orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = rotateImage(bitmap, 90);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = rotateImage(bitmap, 180);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = rotateImage(bitmap, 270);
                    break;

                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    rotatedBitmap = bitmap;
            }

            return rotatedBitmap;
        }
        catch (IOException e){
            Log.e(TAG, "setOrientation() - failed to get image orientation", e);
            return bitmap;
        }
        finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e(TAG, "setOrientation() - failed close input stream", e);
                }
            }
        }
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }
}
