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
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class Import_view extends Fragment implements DatePickerDialog.OnDateSetListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view,container,false);
    }
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myReff;
    ArrayAdapter<String> adapter;
    ArrayList<String> sectionList;
    TextView displayDate;
    Spinner spinner;
Button shw;
    String dt,str;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        displayDate =getView().findViewById(R.id.showDIT);

        spinner = getView().findViewById(R.id.viewSection);
        shw = getView().findViewById(R.id.showDATEPICKER);
        myReff = FirebaseDatabase.getInstance().getReference("Section");
        sectionList = new ArrayList<>(Arrays.asList("Section"));
        adapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item,sectionList);
        spinner.setAdapter(adapter);
        dateRetrieve();


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                str = (String) spinner.getSelectedItem().toString();
                HomeActivity homeActivity = (HomeActivity) getActivity();
                homeActivity.sendDataView(str);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    shw.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showDatePicker();

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

    public void dateRetrieve(){
        myReff = FirebaseDatabase.getInstance().getReference("Section");
        myReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot item: dataSnapshot.getChildren()){
                    sectionList.add(item.getKey());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int i, int i1, int i2) {
        dt= String.format("%d/%d/%d",i1,i2,i);
        displayDate = getView().findViewById(R.id.showDIT);
        displayDate.setText(dt);
        HomeActivity homeActivity = (HomeActivity) getActivity();
        homeActivity.sendDataDate(dt);
    }
}
