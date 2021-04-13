package com.example.ams;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ExampleViewHolder1> {
    private ArrayList<Students> mExampleList;
    private ArrayList<RadioButtons> mRadioButtons;
    private ExampleViewHolder1 holder;
    private int position;


    public static class ExampleViewHolder1 extends RecyclerView.ViewHolder{

        public TextView mTextView2;
        public TextView result1;

        public ExampleViewHolder1(@NonNull View itemView) {
            super(itemView);
            mTextView2 = itemView.findViewById(R.id.textView1);
            result1 = itemView.findViewById(R.id.valueResult);
        }
    }
    public StudentAdapter(ArrayList<Students>exampleList, ArrayList<RadioButtons> radioBu)
    {
        mExampleList = exampleList;
        mRadioButtons = radioBu;
    }

    @NonNull
    @Override
    public ExampleViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items2, parent, false);
        ExampleViewHolder1 evh = new ExampleViewHolder1(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ExampleViewHolder1 holder, int position) {
        this.holder = holder;
        this.position = position;
        Students currentItem = mExampleList.get(position);
        RadioButtons curr = mRadioButtons.get(position);


        holder.mTextView2.setText(currentItem.getName());
        holder.result1.setText(curr.getValue());
    }

    @Override
    public int getItemCount() {

        return mExampleList.size();
    }
//    public class ViewHolder extends RecyclerView.ViewHolder{
//        public RadioGroup selectionState;
//
//        public ViewHolder(View view){
//            super(view);
//            selectionState = (RadioGroup) view.findViewById(R.id.allRadioButton);
//
//            selectionState.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    lastSelectedPosition = getAdapterPosition();
//                    notifyDataSetChanged();
//                }
//            });
//        }
//    }
}
