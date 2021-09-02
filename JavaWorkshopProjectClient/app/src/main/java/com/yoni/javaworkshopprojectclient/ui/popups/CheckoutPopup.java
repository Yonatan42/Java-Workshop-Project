package com.yoni.javaworkshopprojectclient.ui.popups;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.OrderDetails;
import com.yoni.javaworkshopprojectclient.ui.areafragments.UserInfoFragment;
import com.yoni.javaworkshopprojectclient.ui.listadapters.OrderDetailsProductsAdapter;
import com.yoni.javaworkshopprojectclient.utils.UIUtils;

public class CheckoutPopup extends AlertDialog {

    public CheckoutPopup(Fragment fragment) {
        super(fragment.getContext());

        View layout = LayoutInflater.from(getContext()).inflate(R.layout.popup_checkout, null, false);
        Button btnCancel = layout.findViewById(R.id.checkout_popup_btn_cancel);
        Button btnOk = layout.findViewById(R.id.checkout_popup_btn_ok);
        EditText txtCreditCard = layout.findViewById(R.id.checkout_popup_txt_card);
        EditText txtCVV = layout.findViewById(R.id.checkout_popup_txt_cvv);
        EditText txtExpiration = layout.findViewById(R.id.checkout_popup_txt_expiration);
        UserInfoFragment userInfoFragment =  (UserInfoFragment) fragment.getChildFragmentManager().findFragmentById(R.id.checkout_popup_user_details_fragment);
        setView(layout);
    }
}
