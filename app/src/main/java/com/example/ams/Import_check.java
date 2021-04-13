package com.example.ams;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class Import_check extends Fragment implements DatePickerDialog.OnDateSetListener {
    String str,dt;
    ArrayList<String> sectionList;
    ArrayAdapter adapter;
    Spinner spinner;
    DatabaseReference myReff;
    FirebaseDatabase database;
    TextView showDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_check,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TextView spinnerText=getView().findViewById(R.id.spinnerResult);
        spinner = getView().findViewById(R.id.spinner1);

        myReff = FirebaseDatabase.getInstance().getReference("Section");
        sectionList = new ArrayList<>(Arrays.asList("--Section--"));
        adapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item,sectionList);
        spinner.setAdapter(adapter);
        dateRetrieve();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                str = (String) spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Button button = getView().findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
                HomeActivity homeActivity = (HomeActivity) getActivity();
                homeActivity.sendDataBlock(str);

            }
        });

    }


    public void showDatePicker(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }


    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int i, int i1, int i2) {
        dt= String.format("%d/%d/%d",i1,i2,i);
        showDate = getView().findViewById(R.id.display);
        showDate.setText(dt);
        HomeActivity homeActivity = (HomeActivity) getActivity();
        homeActivity.sendDataDate(dt);
    }

    public void dateRetrieve() {
        myReff = FirebaseDatabase.getInstance().getReference("Section");
        myReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    sectionList.add(item.getKey());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}


