package com.yoni.javaworkshopprojectclient.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

public class UIUtils {

    private UIUtils(){}

    public static void setViewsEnabled(boolean isEnabled, View... views){
        for (View txt: views){
            txt.setEnabled(isEnabled);
        }
    }

    public static void setViewsVisible(boolean isVisible, View...views){
        int visibility = isVisible ? View.VISIBLE : View.GONE;
        for (View txt: views){
            txt.setVisibility(visibility);
        }
    }

    public static void setSpText(TextView txt, int dimenRes){
        float textSize = txt.getContext().getResources().getDimension(dimenRes);
        txt.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }
}
