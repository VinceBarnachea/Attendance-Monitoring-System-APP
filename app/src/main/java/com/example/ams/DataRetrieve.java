package com.example.ams;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DataRetrieve extends AppCompatActivity{


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    FirebaseDatabase database;
    DatabaseReference myReff;
    ArrayList<Students> exampleList;
    ArrayList<RadioButtons> values;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_retrieve);
        final TextView dates,blocks;
        blocks=findViewById(R.id.blocks1);
        dates=findViewById(R.id.dates1);
        database =FirebaseDatabase.getInstance();

        final String block = getIntent().getExtras().getString("Block");
        final String date = getIntent().getExtras().getString("Date");
        blocks.setText(block);
        dates.setText(date);
        swipeRefreshLayout = findViewById(R.id.refresh1);

        exampleList =  new ArrayList<>();
        values = new ArrayList<>();


        myReff = FirebaseDatabase.getInstance().getReference("Section").child(block);
        myReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot students:dataSnapshot.getChildren()){
                    Students students1 = students.getValue(Students.class);
                    exampleList.add(students1);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        myReff = FirebaseDatabase.getInstance().getReference("Date").child(dates.getText().toString()).child(blocks.getText().toString());
        myReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot studentValue: dataSnapshot.getChildren()){
                    RadioButtons radioButtons = studentValue.getValue(RadioButtons.class);
                    values.add(radioButtons);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mRecyclerView = findViewById(R.id.recyclerView1);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new StudentAdapter(exampleList,values);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRecyclerView = findViewById(R.id.recyclerView1);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(DataRetrieve.this);
                mAdapter = new StudentAdapter(exampleList,values);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },4000);
            }
        });

    }


}
