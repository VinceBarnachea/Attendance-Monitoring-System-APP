package com.example.ams;

import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Example_item {




    private RadioGroup mRadioButton;
    private String mText1;


    public Example_item(RadioGroup radioGroup, String text1){
        mRadioButton = radioGroup;
        mText1=text1;

    }

    public RadioGroup getmRadioButton() {
        return mRadioButton;
    }

    public void setmRadioButton(RadioGroup mRadioButton) {
        this.mRadioButton = mRadioButton;
    }
    public String getText1() {
        return mText1;
    }}
