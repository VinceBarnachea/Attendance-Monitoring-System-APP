package com.example.ams;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;


import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Import_add extends Fragment implements SeekBar.OnSeekBarChangeListener {
    EditText sec,tot,std1,std2,std3,std4,std5,std6;
    Button button,addBtn,clearBtn;
    FirebaseDatabase database;
    DatabaseReference myReff;
    ArrayList list = new ArrayList();
    ListView lv;
    ArrayAdapter<String> adapter;
    String sect,one;

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add,container,false);

    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sec = (EditText) getView().findViewById(R.id.section);
        std1 = (EditText) getView().findViewById(R.id.student1);
        lv = (ListView) getView().findViewById(R.id.listV);

        addBtn = (Button) getView().findViewById(R.id.add);
        button = (Button) getView().findViewById(R.id.save);
        database = FirebaseDatabase.getInstance();



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studentName = std1.getText().toString();

                Students students = new Students(studentName);
                sect = sec.getText().toString();
                myReff = database.getReference("Section");
                if (!TextUtils.isEmpty(studentName) && !TextUtils.isEmpty(sect)) {
                    myReff.child(sect.toUpperCase()).push().setValue(students).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {

                            Toast.makeText(getActivity(), "Successfully Added.", Toast.LENGTH_LONG).show();

                            ((EditText) getView().findViewById(R.id.student1)).setText("");
                        }
                    });
             } else {
                    Toast.makeText(getActivity(), "Please Type the Stuent name or Class", Toast.LENGTH_LONG).show();
                }


            }
        });
    }




    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
