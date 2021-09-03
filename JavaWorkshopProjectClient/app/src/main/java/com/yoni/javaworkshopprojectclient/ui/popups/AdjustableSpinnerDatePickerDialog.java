package com.yoni.javaworkshopprojectclient.ui.popups;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yoni.javaworkshopprojectclient.R;
import com.yoni.javaworkshopprojectclient.utils.UIUtils;

public class AdjustableSpinnerDatePickerDialog extends DatePickerDialog {
    public AdjustableSpinnerDatePickerDialog(@NonNull Context context, @Nullable OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth) {
        super(context, R.style.CardExpirationDatePickerSpinnerStyle, listener, year, monthOfYear, dayOfMonth);
    }

    private void setSectionVisible(int section, boolean visible){
        UIUtils.setViewsVisible(visible, ((ViewGroup)((ViewGroup)getDatePicker().getChildAt(0)).getChildAt(0)).getChildAt(section));
    }

    public void setDayVisible(boolean visible) {
        setSectionVisible(1, visible);
    }

    public void setMonthVisible(boolean visible) {
        setSectionVisible(0, visible);
    }

    public void setYearVisible(boolean visible) {
        setSectionVisible(2, visible);
    }
}
