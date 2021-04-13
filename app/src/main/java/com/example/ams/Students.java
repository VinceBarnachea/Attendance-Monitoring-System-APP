package com.example.ams;

import android.widget.RadioGroup;

import com.google.firebase.database.ValueEventListener;

public class Students {
    private String name;



    private RadioGroup Value;

    public Students(){

    }
    public Students(String name) {
        this.name = name;
}



    public String getName() {
        return name;
    }


    public RadioGroup getmRadioButton() {
        return Value;
    }

    public void setmRadioButton(RadioGroup mRadioButton) {
        this.Value = mRadioButton;
    }

}
