package com.yoni.javaworkshopprojectclient.ui.popups;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import com.yoni.javaworkshopprojectclient.R;

public class ErrorPopup extends AlertDialog {

    public ErrorPopup(Context context, String message){
        super(context);

        setIcon(R.drawable.ic_error);
        Drawable icon = AppCompatResources.getDrawable(context, R.drawable.ic_error);
        icon.setTint(Color.RED);
        setIcon(icon);
        setTitle(context.getString(R.string.error_txt_title));
        setMessage(message);
        setButton(BUTTON_NEGATIVE, context.getString(R.string.error_btn_ok), (OnClickListener) null);
        setCancelable(false);
    }



}
