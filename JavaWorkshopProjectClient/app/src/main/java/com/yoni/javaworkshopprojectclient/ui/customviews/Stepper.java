package com.yoni.javaworkshopprojectclient.ui.customviews;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.yoni.javaworkshopprojectclient.R;


public class Stepper extends FrameLayout {

    private float value;
    private float initialValue;
    private float minValue;
    private float maxValue;
    private float step;
    private int decimalPrecision;

    private TextView txtTittle;
    private EditText txtAmount;
    private Button btnIncrease;
    private Button btnDecrease;

    private OnValueChangedListener listener;

    public Stepper(Context context) {
        this(context, null);
    }

    public Stepper(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Stepper(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public Stepper(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){

        float defaultSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20,context.getResources().getDisplayMetrics());

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Stepper, 0, 0);
        String titleText = a.getString(R.styleable.Stepper_titleText);
        float titleTextSize = a.getDimension(R.styleable.Stepper_titleTextSize, defaultSize);
        int titleTextColor = a.getColor(R.styleable.Stepper_titleTextColor, Color.BLACK);
        String amountHint = a.getString(R.styleable.Stepper_amountHint);
        float amountTextSize = a.getDimension(R.styleable.Stepper_amountTextSize, defaultSize);
        int amountTextColor = a.getColor(R.styleable.Stepper_amountTextColor, Color.GRAY);
        float buttonTextSize = a.getDimension(R.styleable.Stepper_buttonTextSize, defaultSize);
        int buttonBackgroundColor = a.getColor(R.styleable.Stepper_buttonBackgroundColor, Color.BLACK);
        int buttonTextColor = a.getColor(R.styleable.Stepper_buttonTextColor, Color.WHITE);
        float minValue = a.getFloat(R.styleable.Stepper_minValue, Float.MIN_VALUE);
        float maxValue = a.getFloat(R.styleable.Stepper_maxValue, Float.MAX_VALUE);
        float initialValue = a.getFloat(R.styleable.Stepper_initialValue, 0);
        float step = a.getFloat(R.styleable.Stepper_step, 1);
        int decimalPrecision = a.getInteger(R.styleable.Stepper_decimalPrecision, 0);
        int inputType = a.getInteger(R.styleable.Stepper_android_inputType, EditorInfo.TYPE_CLASS_NUMBER | EditorInfo.TYPE_NUMBER_FLAG_DECIMAL | EditorInfo.TYPE_NUMBER_FLAG_SIGNED);

        a.recycle();


        inflate(getContext(), R.layout.layout_stepper,this);

        value = initialValue;
        this.initialValue = initialValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.step = step;
        this.decimalPrecision = Math.max(decimalPrecision, 0);

        txtTittle = findViewById(R.id.stepper_txt_title);
        txtAmount = findViewById(R.id.stepper_txt_amount);
        btnIncrease = findViewById(R.id.stepper_btn_increase);
        btnDecrease = findViewById(R.id.stepper_btn_decrease);

        setTitleText(titleText);
        setTitleTextColor(titleTextColor);
        setTitleTextSize(titleTextSize);

        setAmountTextSize(amountTextSize);
        setAmountTextColor(amountTextColor);
        setAmountHint(amountHint);
        setInputType(inputType);
        txtAmount.addTextChangedListener(textWatcher);

        setButtonTextColor(buttonTextColor);
        setButtonTextSize(buttonTextSize);
        setButtonBackgroundColor(buttonBackgroundColor);
        btnIncrease.setOnClickListener(onButtonClick);
        btnDecrease.setOnClickListener(onButtonClick);

        setValueUnchecked(value, true);

    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
        @Override
        public void afterTextChanged(Editable s) {
            try{
                float val = Float.parseFloat(s.toString().trim());
                setValueInternal(val, false);
            }
            catch(NumberFormatException ignored){}
        }
    };

    private final View.OnClickListener onButtonClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            float val;
            if(v == btnIncrease){
                val = value + step;
            }
            else{
                val = value - step;
            }
            setValue(val);
        }
    };

    private void setValueInternal(float newValue, boolean setTxtForValid){
        if(newValue == value){
            return;
        }
        setValueUnchecked(newValue, setTxtForValid);
    }

    private void setValueUnchecked(float newValue, boolean setTxtForValue){
        boolean isValid = true;
        if(newValue <= minValue){
            isValid = newValue == minValue;
            newValue = minValue;
            btnDecrease.setEnabled(false);
        }
        else{
            btnDecrease.setEnabled(true);
        }
        if(newValue >= maxValue){
            isValid = isValid && newValue == maxValue;
            newValue = maxValue;
            btnIncrease.setEnabled(false);
        }
        else{
            btnIncrease.setEnabled(true);
        }
        float oldValue = value;
        value = newValue;
        if(setTxtForValue || !isValid){
            txtAmount.setText(getValueText(value));
        }
        if(this.listener != null){
            listener.onValueChanged(this, newValue, oldValue);
        }
    }

    private String getValueText(float val){
        return String.format("%."+this.decimalPrecision+"f", val);
    }

    public void setTitleText(String titleText){
        if(titleText == null || titleText.isEmpty()) {
            txtTittle.setVisibility(View.GONE);
            txtTittle.setText("");

        }
        else {
            txtTittle.setVisibility(View.VISIBLE);
            txtTittle.setText(titleText);
        }
    }

    public String getTitleText(){
        return txtTittle.getText().toString();
    }

    private void setTextColor(TextView txt, int color){
        txt.setTextColor(color);
    }

    private int getTextColor(TextView txt){
        return txt.getTextColors().getDefaultColor();
    }

    public void setTitleTextColor(int color){
        setTextColor(txtTittle, color);
    }

    public int getTitleTextColor(){
        return getTextColor(txtTittle);
    }

    public void setAmountTextColor(int color){
        setTextColor(txtAmount, color);
    }

    public int getAmountTextColor(){
        return getTextColor(txtAmount);
    }

    public void setButtonTextColor(int color){
        setTextColor(btnIncrease, color);
        setTextColor(btnDecrease, color);
    }

    public int getButtonTextColor(){
        return getTextColor(btnDecrease);
    }

    private void setTextSize(TextView txt, float size){
        txt.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    private float getTextSize(TextView txt){
        return txt.getTextSize();
    }


    public void setTitleTextSize(float size){
        setTextSize(txtTittle, size);
    }

    public float getTitleTextSize(){
        return getTextSize(txtTittle);
    }

    public void setAmountTextSize(float size){
        setTextSize(txtAmount, size);
    }

    public float getAmountTextSize(){
        return getTextSize(txtAmount);
    }

    public void setButtonTextSize(float size){
        setTextSize(btnIncrease, size);
        setTextSize(btnDecrease, size);
    }

    public float getButtonTextSize(){
        return getTextSize(btnDecrease);
    }

    public void setButtonBackgroundColor(int color){
        ColorStateList colors = ColorStateList.valueOf(color);
        btnIncrease.setBackgroundTintList(colors);
        btnDecrease.setBackgroundTintList(colors);
    }

    public int getButtonBackgroundColor(){
        return btnDecrease.getBackgroundTintList().getDefaultColor();
    }


    public void setValue(float newValue){
        setValueInternal(newValue, true);
    }

    public void setOnValueChangedListener(OnValueChangedListener listener){
        this.listener = listener;
    }

    public void reset(){
        setValue(initialValue);
    }

    public void setInitialValue(float val){
        if(value == initialValue){
            initialValue = val;
            setValue(initialValue);
            return;
        }
        initialValue = val;
    }

    public float getInitialValue(){
        return initialValue;
    }

    public void setMinValue(float val){
        minValue = val;
        if(value > minValue){
            btnDecrease.setEnabled(true);
        }
    }

    public float getMinValue(){
        return minValue;
    }

    public void setMaxValue(float val){
        maxValue = val;
        if(value < maxValue){
            btnIncrease.setEnabled(true);
        }
    }

    public float getMaxValue(){
        return maxValue;
    }

    public void setStep(float val){
        step = val;
    }

    public float getStep(){
        return step;
    }

    public void setDecimalPrecision(int val){
        int oldPrecision = decimalPrecision;
        decimalPrecision = val;
        if(oldPrecision != decimalPrecision){
            txtAmount.setText(getValueText(value));
        }
    }

    public int getDecimalPrecision() {
        return decimalPrecision;
    }

    public void setAmountHint(String hint){
        txtAmount.setHint(hint);
    }

    public String getAmountHint(){
        return txtAmount.getHint().toString();
    }

    public void setInputType(int inputType){
        txtAmount.setInputType(inputType);
    }


    public int getInputType(){
        return txtAmount.getInputType();
    }

    public interface OnValueChangedListener{
        void onValueChanged(Stepper view, float newValue, float oldValue);
    }

}
