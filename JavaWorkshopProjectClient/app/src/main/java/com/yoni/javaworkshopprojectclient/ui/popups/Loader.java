package com.yoni.javaworkshopprojectclient.ui.popups;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yoni.javaworkshopprojectclient.R;

public class Loader extends AlertDialog {

    public Loader(Context context){
        super(context, R.style.TransparentWrapContentDialog);
        View layout = LayoutInflater.from(context).inflate(R.layout.popup_loader, null, false);
        setView(layout);
        setCancelable(false);
    }


}
