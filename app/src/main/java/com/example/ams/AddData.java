package com.example.ams;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ams.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddData extends AppCompatActivity{

    EditText sec, tot, std1, std2, std3, std4, std5, std6;
    Button button, addBtn, clearBtn;
    FirebaseDatabase database;
    DatabaseReference myReff;
    ArrayList list = new ArrayList();
    ListView lv;
    ArrayAdapter<String> adapter;
    String sect, one;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        sec = (EditText) findViewById(R.id.section);
        std1 = (EditText) findViewById(R.id.student1);
        lv = (ListView) findViewById(R.id.listV);

        addBtn = (Button) findViewById(R.id.add);
        button = (Button) findViewById(R.id.save);
        database = FirebaseDatabase.getInstance();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                one = std1.getText().toString();
                list.add(one);
                adapter = new ArrayAdapter<String>(AddData.this, android.R.layout.simple_list_item_1, list);
                lv.setAdapter(adapter);
                ((EditText) findViewById(R.id.student1)).setText("");


            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sect = sec.getText().toString();
                one = std1.getText().toString();
                myReff = database.getReference("Data").child(sect);
                myReff.push().setValue(one);
                Toast.makeText(AddData.this, "Successfully Added.", Toast.LENGTH_LONG).show();
                list.clear();
                adapter.clear();
                ((EditText) findViewById(R.id.section)).setText("");
            }
        });
    }


}
