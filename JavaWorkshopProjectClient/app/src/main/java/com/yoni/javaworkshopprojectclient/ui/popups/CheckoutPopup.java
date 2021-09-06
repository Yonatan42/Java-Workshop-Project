package com.yoni.javaworkshopprojectclient.ui.popups;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.OrderDetails;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.User;
import com.yoni.javaworkshopprojectclient.localdatastores.DataSets;
import com.yoni.javaworkshopprojectclient.remote.RemoteServiceManager;
import com.yoni.javaworkshopprojectclient.remote.StandardResponseErrorCallback;
import com.yoni.javaworkshopprojectclient.ui.ParentActivity;
import com.yoni.javaworkshopprojectclient.ui.areafragments.UserInfoFragment;
import com.yoni.javaworkshopprojectclient.utils.UIUtils;

import java.util.Calendar;
import java.util.Date;

public class CheckoutPopup extends AlertDialog {

    private ParentActivity parentActivity;
    private Calendar expirationCalendar;
    private Button btnCancel;
    private Button btnOk;
    private EditText txtCreditCard;
    private EditText txtCVV;
    private EditText txtExpiration;
    private UserInfoFragment userInfoFragment;

    public CheckoutPopup(ParentActivity parentActivity, Fragment fragment) {
        super(parentActivity);
        this.parentActivity = parentActivity;

        View layout = LayoutInflater.from(parentActivity).inflate(R.layout.popup_checkout, null, false);
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
        btnOk.setEnabled(false);
        setCalendarToEndOfMonth();
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
                    new SimpleMessagePopup(getContext(), getContext().getString(R.string.order_complete_title), String.format("#%d",result.getId())).show();
                    dismiss();
                },
                new StandardResponseErrorCallback<OrderDetails>(parentActivity) {
                    @Override
                    public void onPreErrorResponse() {
                        btnOk.setEnabled(true);
                    }
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

    private void setCalendarToEndOfMonth(){
        expirationCalendar.set(Calendar.DAY_OF_MONTH, expirationCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        expirationCalendar.set(Calendar.HOUR_OF_DAY, expirationCalendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        expirationCalendar.set(Calendar.MINUTE, expirationCalendar.getActualMaximum(Calendar.MINUTE));
        expirationCalendar.set(Calendar.SECOND, expirationCalendar.getActualMaximum(Calendar.SECOND));
        expirationCalendar.set(Calendar.MILLISECOND, expirationCalendar.getActualMaximum(Calendar.MILLISECOND));
    }
}
