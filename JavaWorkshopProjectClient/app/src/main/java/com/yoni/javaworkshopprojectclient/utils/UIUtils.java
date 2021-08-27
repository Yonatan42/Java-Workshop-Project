package com.yoni.javaworkshopprojectclient.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.TextView;

public class UIUtils {
    // todo - perhaps remove this later

    private UIUtils(){}

//    public static int tryParseInt(String value, int defaultVal) {
//        try {
//            return Integer.parseInt(value);
//        } catch (NumberFormatException e) {
//            return defaultVal;
//        }
//    }

    public static void setSpText(TextView txt, int dimenRes){
        float textSize = txt.getContext().getResources().getDimension(dimenRes);
        txt.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }
}
