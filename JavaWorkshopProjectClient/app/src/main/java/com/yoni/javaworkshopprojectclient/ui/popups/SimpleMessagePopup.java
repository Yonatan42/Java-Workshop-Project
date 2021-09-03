package com.yoni.javaworkshopprojectclient.ui.popups;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import androidx.appcompat.content.res.AppCompatResources;

import com.yoni.javaworkshopprojectclient.R;

public class SimpleMessagePopup extends AlertDialog {

    public SimpleMessagePopup(Context context, String title, String message){
        super(context);
        if(title != null && !title.isEmpty()){
            setTitle(title);
        }
        if(message != null && !message.isEmpty()){
            setMessage(message);
        }
        setButton(BUTTON_NEGATIVE, context.getString(R.string.btn_generic_ok), (OnClickListener) null);
    }





}
