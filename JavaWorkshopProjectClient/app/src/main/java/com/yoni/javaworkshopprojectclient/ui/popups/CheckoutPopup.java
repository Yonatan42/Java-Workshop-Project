package com.yoni.javaworkshopprojectclient.ui.popups;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.datatransfer.ServerResponse;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.OrderDetails;
import com.yoni.javaworkshopprojectclient.datatransfer.models.entitymodels.User;
import com.yoni.javaworkshopprojectclient.localdatastores.DataSets;
import com.yoni.javaworkshopprojectclient.localdatastores.cart.CartStore;
import com.yoni.javaworkshopprojectclient.remote.ErrorCodes;
import com.yoni.javaworkshopprojectclient.remote.RemoteServiceManager;
import com.yoni.javaworkshopprojectclient.remote.StandardResponseErrorCallback;
import com.yoni.javaworkshopprojectclient.ui.ParentActivity;
import com.yoni.javaworkshopprojectclient.ui.areafragments.UserInfoFragment;
import com.yoni.javaworkshopprojectclient.utils.InputValidationUtils;
import com.yoni.javaworkshopprojectclient.utils.UIUtils;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;

public class CheckoutPopup extends AlertDialog {

    private final ParentActivity parentActivity;
    private final FragmentManager fragmentManager;
    private final Runnable onCheckoutComplete;
    private Calendar expirationCalendar;
    private Button btnCancel;
    private Button btnOk;
    private EditText txtCreditCard;
    private EditText txtCVV;
    private EditText txtExpiration;
    private UserInfoFragment userInfoFragment;

    public CheckoutPopup(ParentActivity parentActivity, FragmentManager fragmentManager, Runnable onCheckoutComplete) {
        super(parentActivity);
        this.parentActivity = parentActivity;
        this.fragmentManager = fragmentManager;
        this.onCheckoutComplete = onCheckoutComplete;

        View layout = LayoutInflater.from(parentActivity).inflate(R.layout.popup_checkout, null, false);
        btnCancel = layout.findViewById(R.id.checkout_popup_btn_cancel);
        btnOk = layout.findViewById(R.id.checkout_popup_btn_ok);
        txtCreditCard = layout.findViewById(R.id.checkout_popup_txt_card);
        txtCVV = layout.findViewById(R.id.checkout_popup_txt_cvv);
        txtExpiration = layout.findViewById(R.id.checkout_popup_txt_expiration);
        userInfoFragment =  (UserInfoFragment) fragmentManager.findFragmentById(R.id.checkout_popup_user_details_fragment);



        setUpUserInfo();
        setUpExpiration();
        btnCancel.setOnClickListener(v -> dismiss());
        btnOk.setOnClickListener(v -> {
            setCalendarToEndOfMonth();
            if(validateForm()) {
                makeOrder();
            }
        });


        setView(layout);
    }

    private void makeOrder() {
        btnOk.setEnabled(false);
        RemoteServiceManager.getInstance().getOrdersService().createOrder(
                DataSets.getInstance().getCurrentUser().getId(),
                userInfoFragment.getEmail(),
                userInfoFragment.getFirstName(),
                userInfoFragment.getLastName(),
                userInfoFragment.getPhone(),
                userInfoFragment.getAddress(),
                CartStore.getInstance().getAll(),
                UIUtils.getTrimmedText(txtCreditCard),
                expirationCalendar.getTime(),
                UIUtils.getTrimmedText(txtCVV),
                (call, response, result) -> {
                    new SimpleMessagePopup(getContext(), getContext().getString(R.string.order_complete_title), String.format("#%d",result)).show();
                    dismiss();
                    onCheckoutComplete.run();
                },
                new StandardResponseErrorCallback<Integer>(parentActivity) {
                    @Override
                    public void onPreErrorResponse() {
                        btnOk.setEnabled(true);
                    }

                    @Override
                    public void onUnhandledResponseError(@NonNull Call<ServerResponse<Integer>> call, ServerResponse.ServerResponseError responseError) {
                        String errorMessage;
                        switch (responseError.getCode()){
                            case ErrorCodes.USERS_NO_SUCH_USER:
                                errorMessage = parentActivity.getString(R.string.error_no_user_found);
                                break;
                            case ErrorCodes.RESOURCES_UNAVAILABLE:
                                errorMessage = parentActivity.getString(R.string.error_product_disabled_or_too_few);
                                break;
                            case ErrorCodes.ORDERS_EMPTY:
                                errorMessage = parentActivity.getString(R.string.error_empty_order);
                                break;
                            default:
                                super.onUnhandledResponseError(call, responseError);
                                return;
                        }
                        ErrorPopup.createGenericOneOff(parentActivity, errorMessage).show();                        }

                }
        );
    }

    private boolean validateForm(){

        if(!userInfoFragment.validate(true)){
            return false;
        }

        String cardNum = UIUtils.getTrimmedText(txtCreditCard);
        String cvv = UIUtils.getTrimmedText(txtCVV);
        String expirationText = UIUtils.getTrimmedText(txtExpiration);
        Date expiration = expirationCalendar.getTime();

        String errorMessage;

        if(cardNum.isEmpty()){
            errorMessage = "credit card number must be filled in";
        }
        else if(!InputValidationUtils.validateCreditCardNum(cardNum)){
            errorMessage = "credit card number is not valid";
        }
        else if(cvv.isEmpty()){
            errorMessage = "credit card cvv must be filled in";
        }
        else if(!InputValidationUtils.validateCreditCardCVV(cvv)){
            errorMessage = "credit card cvv is not valid";
        }
        else if(expirationText.isEmpty()){
            errorMessage = "credit card expiration date must be filled in";
        }
        else if(expiration.compareTo(new Date()) <= 0){
            errorMessage = "credit card expiration date has passed";
        }
        else{ // valid
            return true;
        }

        ErrorPopup.createGenericOneOff(getContext(), errorMessage).show();
        return false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        fragmentManager.beginTransaction().remove(userInfoFragment).commit();
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
