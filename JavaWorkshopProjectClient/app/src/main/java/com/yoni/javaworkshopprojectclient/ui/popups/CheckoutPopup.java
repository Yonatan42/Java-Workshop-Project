package com.yoni.javaworkshopprojectclient.ui.popups;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.User;
import com.yoni.javaworkshopprojectclient.localdatastores.DataSets;
import com.yoni.javaworkshopprojectclient.remote.RemoteServiceManager;
import com.yoni.javaworkshopprojectclient.ui.areafragments.UserInfoFragment;
import com.yoni.javaworkshopprojectclient.utils.UIUtils;

import java.util.Calendar;
import java.util.Date;

public class CheckoutPopup extends AlertDialog {


    private Calendar expirationCalendar;
    private Button btnCancel;
    private Button btnOk;
    private EditText txtCreditCard;
    private EditText txtCVV;
    private EditText txtExpiration;
    private UserInfoFragment userInfoFragment;

    public CheckoutPopup(Fragment fragment) {
        super(fragment.getContext());

        View layout = LayoutInflater.from(getContext()).inflate(R.layout.popup_checkout, null, false);
        btnCancel = layout.findViewById(R.id.checkout_popup_btn_cancel);
        btnOk = layout.findViewById(R.id.checkout_popup_btn_ok);
        txtCreditCard = layout.findViewById(R.id.checkout_popup_txt_card);
        txtCVV = layout.findViewById(R.id.checkout_popup_txt_cvv);
        txtExpiration = layout.findViewById(R.id.checkout_popup_txt_expiration);
        userInfoFragment =  (UserInfoFragment) fragment.getParentFragmentManager().findFragmentById(R.id.checkout_popup_user_details_fragment);



        setUpUserInfo();
        setUpExpiration();
        btnCancel.setOnClickListener(v -> dismiss());
        btnOk.setOnClickListener(v -> {
            if(validateForm()) {
                makeOrder();
            }
        });


        setView(layout);
    }

    private void makeOrder() {
        RemoteServiceManager.getInstance().getOrdersService().createOrder(
                DataSets.getInstance().getCurrentUser().getId(),
                userInfoFragment.getEmail(),
                userInfoFragment.getFirstName(),
                userInfoFragment.getLastName(),
                userInfoFragment.getPhone(),
                userInfoFragment.getAddress(),
                UIUtils.getTrimmedText(txtCreditCard),
                expirationCalendar.getTime(),
                UIUtils.getTrimmedText(txtCVV),
                (call, response, result) -> {
                    new SimpleMessagePopup(getContext(), getContext().getString(R.string.order_complete_title), String.format("#%d",result.getOrderId())).show();
                    dismiss();
                },
                (call, responseError) -> {
                    // todo - perhaps change this or be more specific
                    ErrorPopup.createGenericOneOff(getContext()).show();
                }
        );
    }

    private boolean validateForm(){
        // todo - validate - use userInfoFragment's validation + for the new stuff
        // show error toast within this method
        return true;
    }

    private void setUpUserInfo(){
        User currentUser = DataSets.getInstance().getCurrentUser();
        userInfoFragment.setFirstName(currentUser.getFirstName());
        userInfoFragment.setLastName(currentUser.getLastName());
        userInfoFragment.setEmail(currentUser.getEmail());
        userInfoFragment.setPhone(currentUser.getPhone());
        userInfoFragment.setAddress(currentUser.getAddress());
    }

    private void setUpExpiration() {
        expirationCalendar = Calendar.getInstance();

        txtExpiration.setOnClickListener(v -> {
            AdjustableSpinnerDatePickerDialog datePickerDialog = new AdjustableSpinnerDatePickerDialog(getContext(),
                    (view, year, monthOfYear, dayOfMonth) -> {
                        expirationCalendar.set(Calendar.YEAR, year);
                        expirationCalendar.set(Calendar.MONTH, monthOfYear);
                        txtExpiration.setText(UIUtils.formatDateCardExpiration(expirationCalendar.getTime()));
                    },
                    expirationCalendar.get(Calendar.YEAR), expirationCalendar.get(Calendar.MONTH), expirationCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.setDayVisible(false);
            datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
            datePickerDialog.show();
        });
    }
}
