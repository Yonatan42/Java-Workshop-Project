package com.yoni.javaworkshopprojectclient.ui.areafragments;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.models.entitymodels.User;
import com.yoni.javaworkshopprojectclient.ui.popups.ErrorPopup;
import com.yoni.javaworkshopprojectclient.utils.InputValidationUtils;
import com.yoni.javaworkshopprojectclient.utils.UIUtils;

public class UserInfoFragment extends Fragment {

    private EditText txtFName;
    private EditText txtLName;
    private EditText txtEmail;
    private EditText txtPass;
    private EditText txtPass2;
    private EditText txtPhone;
    private EditText txtAddress;
    private boolean showPasswords;
    private boolean isEditable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_user_details, container, false);
    }

    @Override
    public void onInflate(@NonNull Context context, @NonNull AttributeSet attrs, @Nullable Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.ProfileDetailsFragment);
        showPasswords = a.getBoolean(R.styleable.ProfileDetailsFragment_showPasswords, false);
        isEditable = a.getBoolean(R.styleable.ProfileDetailsFragment_isEditable, false);
        a.recycle();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtFName = view.findViewById(R.id.user_details_txt_fname);
        txtLName = view.findViewById(R.id.user_details_txt_lname);
        txtEmail = view.findViewById(R.id.user_details_txt_email);
        txtPass = view.findViewById(R.id.user_details_txt_pass);
        txtPass2 = view.findViewById(R.id.user_details_txt_pass2);
        txtPhone = view.findViewById(R.id.user_details_txt_phone);
        txtAddress = view.findViewById(R.id.user_details_txt_address);

        setShowPasswordsInternal(showPasswords, true);
        setIsEditableInternal(isEditable, true);

    }

    private void setShowPasswordsInternal(boolean show, boolean forced){
        if(show != showPasswords || forced){
            UIUtils.setViewsVisible(show, txtPass, txtPass2);
        }
        showPasswords = show;
    }

    public void setShowPasswords(boolean show) {
        setShowPasswordsInternal(show, false);
    }

    public boolean getShowPasswords(){
        return showPasswords;
    }

    private void setIsEditableInternal(boolean isEditable, boolean forced){
        if(this.isEditable != isEditable || forced){
            UIUtils.setViewsEnabled(isEditable, txtFName, txtLName, txtEmail, txtPass, txtPass2, txtPhone, txtAddress);
        }
        this.isEditable = isEditable;
    }

    public void setEditable(boolean isEditable) {
        setIsEditableInternal(isEditable, false);
    }

    public boolean isEditable(){
        return isEditable;
    }

    public String getFirstName() {
        return UIUtils.getTrimmedText(txtFName);
    }

    public void setFirstName(String firstName) {
        txtFName.setText(firstName);
    }

    public String getLastName() {
        return UIUtils.getTrimmedText(txtLName);
    }

    public void setLastName(String lastName) {
        txtLName.setText(lastName);
    }

    public String getEmail() {
        return UIUtils.getTrimmedText(txtEmail).toLowerCase();
    }

    public void setEmail(String email) {
        txtEmail.setText(email.toLowerCase());
    }

    public String getPassword() {
        return txtPass.getText().toString();
    }

    public void setPassword(String pass) {
        txtPass.setText(pass);
    }

    public String getPassword2() {
        return txtPass2.getText().toString();
    }

    public void setPassword2(String pass) {
        txtPass2.setText(pass);
    }

    public String getPhone() {
        return UIUtils.getTrimmedText(txtPhone);
    }

    public void setPhone(String phone) {
        txtPhone.setText(phone);
    }

    public String getAddress() {
        return UIUtils.getTrimmedText(txtAddress);
    }

    public void setAddress(String address) {
        txtAddress.setText(address);
    }

    public void set(User user){
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setEmail(user.getEmail());
        setPhone(user.getPhone());
        setAddress(user.getAddress());
        setPassword("");
        setPassword2("");
    }

    public void clear(){
        for(EditText txt: new EditText[]{txtFName, txtLName, txtEmail, txtPass, txtPass2, txtPhone, txtAddress}){
            txt.setText("");
        }
    }

    public boolean validate(boolean showErrorUI){

        String errorMessage;

        String email = getEmail();
        String pass = getPassword();
        String pass2 = getPassword2();
        String firstName = getFirstName();
        String lastName = getLastName();
        String phone = getPhone();
        String address = getAddress();

        if(email.isEmpty()){
            errorMessage = getString(R.string.error_validation_user_info_email_empty);
        }
        else if(!InputValidationUtils.validateEmail(email)){
            errorMessage = getString(R.string.error_validation_user_info_email_invalid);
        }
        else if(showPasswords && pass.isEmpty()){
            errorMessage = getString(R.string.error_validation_user_info_pass_empty);
        }
        else if(showPasswords && pass2.isEmpty()){
            errorMessage = getString(R.string.error_validation_user_info_pass2_empty);
        }
        else if(showPasswords && !pass.equals(pass2)){
            errorMessage = getString(R.string.error_validation_user_info_pass_mismatch);
        }
        else if(showPasswords && !InputValidationUtils.validatePassword(pass)){
            errorMessage = getString(R.string.error_validation_user_info_pass_invalid);
        }
        else if(firstName.isEmpty()){
            errorMessage = getString(R.string.error_validation_user_info_fname_empty);
        }
        else if(!InputValidationUtils.validateName(firstName)){
            errorMessage = getString(R.string.error_validation_user_info_fname_invalid);
        }
        else if(lastName.isEmpty()){
            errorMessage = getString(R.string.error_validation_user_info_lname_empty);
        }
        else if(!InputValidationUtils.validateName(lastName)){
            errorMessage = getString(R.string.error_validation_user_info_lname_invalid);
        }
        else if(phone.isEmpty()){
            errorMessage = getString(R.string.error_validation_user_info_phone_empty);
        }
        else if(!InputValidationUtils.validatePhone(phone)){
            errorMessage = getString(R.string.error_validation_user_info_phone_invalid);
        }
        else if(address.isEmpty()){
            errorMessage = getString(R.string.error_validation_user_info_address_empty);
        }
        else if(!InputValidationUtils.validateAddress(address)){
            errorMessage = getString(R.string.error_validation_user_info_address_invalid);
        }
        else{ // valid
            return true;
        }

        if(showErrorUI){
            ErrorPopup.createGenericOneOff(getContext(), errorMessage).show();
        }

        return false;
    }
}
