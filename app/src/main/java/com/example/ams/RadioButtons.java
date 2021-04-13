package com.example.ams;

import android.widget.RadioGroup;

public class RadioButtons {
    private String value;


    public RadioButtons(){
    }
    public RadioButtons(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
