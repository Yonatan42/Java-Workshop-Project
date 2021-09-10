package com.yoni.javaworkshopprojectclient.ui.popups;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import androidx.appcompat.content.res.AppCompatResources;

import com.yoni.javaworkshopprojectclient.R;

public class ErrorPopup extends AlertDialog {

    public static ErrorPopup createGenericOneOff(Context context){
        return createGenericOneOff(context, null);
    }

    public static ErrorPopup createGenericOneOff(Context context, String message){
        return new ErrorPopup(context, message);
    }

    public ErrorPopup(Context context, String message){
        super(context);
        commonSetUp(context, message);
        setButton(BUTTON_NEGATIVE, context.getString(R.string.btn_generic_ok), (OnClickListener) null);
        setCancelable(false);
    }

    public ErrorPopup(Context context, String message, Runnable onRetry){
        super(context);
        commonSetUp(context, message);
        setButton(BUTTON_NEUTRAL, context.getString(R.string.error_btn_retry), (v,b) -> onRetry.run());
        setCancelable(false);
    }


    private void commonSetUp(Context context, String message){
        setIcon(R.drawable.ic_error);
        Drawable icon = AppCompatResources.getDrawable(context, R.drawable.ic_error);
        icon.setTint(Color.RED);
        setIcon(icon);
        setTitle(context.getString(R.string.error_txt_title));
        if(message != null && !message.isEmpty()) {
            setMessage(message);
        }
    }



}
