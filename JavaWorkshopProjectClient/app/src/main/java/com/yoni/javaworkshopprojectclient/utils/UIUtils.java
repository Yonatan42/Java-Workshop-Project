package com.yoni.javaworkshopprojectclient.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yoni.javaworkshopprojectclient.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

public class UIUtils {

    private UIUtils(){}

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
        if(txt.getText() == null) return "";
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

    public static void setViewsEnabled(boolean isEnabled, View... views){
        for(View txt: views){
            txt.setEnabled(isEnabled);
            if(!isEnabled){
                if(txt.getTag() == null) {
                    txt.setTag(txt.getBackground());
                }
                txt.setBackground(null);
            }
            else {
                Drawable backgroundDrawable = (Drawable) txt.getTag();
                if(backgroundDrawable != null) {
                    txt.setBackground(backgroundDrawable);
                }
            }
        }
    }

    public static String formatPrice(float price) {
        return formatPrice(price, "");
    }
    public static String formatPrice(float price, String currencySymbol){
        return String.format(Locale.getDefault(), "%s%.2f", currencySymbol, price);
    }

    public static String formatDate(Date date){
        return formatDate(date, "dd/MM/yyyy HH:mm");
    }

    public static String formatDateCardExpiration(Date date){
        return formatDate(date, "MM/yy");
    }

    private static String formatDate(Date date, String format){
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        return formatter.format(date);
    }

    public static String getDollarSign(Context context) {
        return context.getResources().getString(R.string.dollar_sign);
    }
}
