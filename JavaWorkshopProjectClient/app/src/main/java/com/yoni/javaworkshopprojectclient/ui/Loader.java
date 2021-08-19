package com.yoni.javaworkshopprojectclient.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yoni.javaworkshopprojectclient.R;

public class Loader extends AlertDialog {

    public Loader(Context context, String title, String message){
        super(context);

        View layout = LayoutInflater.from(context).inflate(R.layout.layout_loader, null, false);
        TextView txtTitle = layout.findViewById(R.id.loader_txt_title);
        TextView txtMessage = layout.findViewById(R.id.loader_txt_message);

        txtTitle.setText(title);
        txtMessage.setText(message);

        setView(layout);
        setCancelable(false);
    }


}
