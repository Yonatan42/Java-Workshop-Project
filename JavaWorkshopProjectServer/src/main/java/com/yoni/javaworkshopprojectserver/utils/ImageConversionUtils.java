package com.yoni.javaworkshopprojectserver.utils;


public class ImageConversionUtils {

    private ImageConversionUtils(){}

    public static String getEncodedImageData(byte[] imageDataBytes){
        if(imageDataBytes == null){
            return null;
        }
        return new String(imageDataBytes);

    }

    public static byte[] getDecodedImageData(String imageDataString){
        if(imageDataString == null){
            return null;
        }
        return imageDataString.getBytes();
    }
}
