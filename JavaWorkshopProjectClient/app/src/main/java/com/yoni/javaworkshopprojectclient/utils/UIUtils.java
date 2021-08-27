package com.yoni.javaworkshopprojectclient.utils;

import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import java.util.Optional;

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

    public static String getTrimmedText(TextView txt){
        return txt.getText().toString().trim();
    }

    public static int tryGetIntValue(TextView txt, int defaultVal){
        String intString = getTrimmedText(txt);
        try {
            return Integer.parseInt(intString);
        }
        catch(NumberFormatException e){
            return defaultVal;
        }
    }

    public static float tryGetFloatValue(TextView txt, float defaultVal){
        String floatString = getTrimmedText(txt);
        try {
            return Float.parseFloat(floatString);
        }
        catch(NumberFormatException e){
            return defaultVal;
        }
    }
}
