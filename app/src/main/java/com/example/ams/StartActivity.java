package com.example.ams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

public class StartActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    String str,dt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        final TextView spinnerText=findViewById(R.id.spinnerResult);
        Spinner spinner = findViewById(R.id.spinner1);
        String[] items = new String[]{"Course/Section","1-BSIT-01","1-BSIT-02","1-BSIT-03","1-BSIT-04","1-BSIT-05"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, items);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 1:
                        spinnerText.setText("Block 1");
                        str=spinnerText.getText().toString();
                        break;
                    case 2:
                        spinnerText.setText("Block 2");
                        str=spinnerText.getText().toString();
                        break;
                    case 3:
                        spinnerText.setText("Block 3");
                        str=spinnerText.getText().toString();
                        break;
                    case 4:
                        spinnerText.setText("Block 4");
                        str=spinnerText.getText().toString();
                        break;
                    case 5:
                        spinnerText.setText("Block 5");
                        str=spinnerText.getText().toString();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        Button button = findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DialogFragment datePicker = new com.example.ams.DatePicker();
//                datePicker.show(getSupportFragmentManager(), "Date Picker");
//            }
//        });


    }



    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, i);
        c.set(Calendar.MONTH, i1);
        c.set(Calendar.DAY_OF_MONTH, i2);
        String curentDate = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        TextView textView = findViewById(R.id.display);
        textView.setText(curentDate);
        dt=textView.getText().toString();
    }
    public void goToCheckAttendance(View v){
        Intent intent = new Intent(StartActivity.this, MainAdapter.class);
        intent.putExtra("Block",str);
        intent.putExtra("Date",dt);
        startActivity(intent);

    }

}

