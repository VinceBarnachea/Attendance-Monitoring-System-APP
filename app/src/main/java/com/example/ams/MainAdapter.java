package com.example.ams;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Build;
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
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class MainAdapter extends AppCompatActivity{


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    Button submit;
    FirebaseDatabase database;
    DatabaseReference myReff;
    ArrayList<Students> exampleList;
    ArrayList<RadioButtons> values;
    RadioButtons radioButtons;
    int count;
    String text;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_adapter);
        final TextView dates,blocks;
        blocks=findViewById(R.id.blocks);
        dates=findViewById(R.id.dates);
        database =FirebaseDatabase.getInstance();
        submit=findViewById(R.id.sumbitBrn);
        final String block = getIntent().getExtras().getString("Block");
        final String date = getIntent().getExtras().getString("Date");
        blocks.setText(block);
        dates.setText(date);
        swipeRefreshLayout = findViewById(R.id.refresh);

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



        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(exampleList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRecyclerView = findViewById(R.id.recyclerView);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(MainAdapter.this);
                mAdapter = new ExampleAdapter(exampleList);
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

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myReff =database.getReference("Date").child(dates.getText().toString()).child(blocks.getText().toString());
                int itemCount = mAdapter.getItemCount();
                for(int i=0;i<itemCount;i++)
                {
                    View views=mRecyclerView.getChildAt(i); // This will give you entire row(child) from RecyclerView
                    if(views!=null)
                    {
                        TextView textView= (TextView) views.findViewById(R.id.res);
                        String text=textView.getText().toString();
                        radioButtons = new RadioButtons(text);


                    }
                    myReff.push().setValue(radioButtons);
                }




                // I WILL INSERT HERE THE CODE FOR GETTING THE TEXTVIEWS INSIDE THE RECYCLER
                Toast.makeText(MainAdapter.this,"SUCCESS!",Toast.LENGTH_LONG).show();
            }
        });












    }


}
