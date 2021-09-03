package com.yoni.javaworkshopprojectclient.ui.popups;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.yoni.javaworkshopprojectclient.R;

public class SimpleMessagePopup extends AlertDialog {

    public static SimpleMessagePopup createGenericTimed(Context context, String title, long displayMillis){
        return new SimpleMessagePopup(context, title, null, displayMillis);
    }

    private long displayMillis = -1;
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

    public SimpleMessagePopup(Context context, String title, String message, long displayMillis){
        this(context, title, message);
        this.displayMillis = displayMillis;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (displayMillis > 0) {
            new Handler(Looper.getMainLooper()).postDelayed(this::dismiss, displayMillis);
        }
    }
}
