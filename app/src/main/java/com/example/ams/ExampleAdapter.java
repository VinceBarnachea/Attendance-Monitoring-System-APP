package com.example.ams;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private ArrayList<Students> mExampleList;
    private Context context;
    private int lastSelectedPosition = 1;


    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public RadioGroup radioGroup;
        public TextView mTextView1;
        public TextView result;
        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            radioGroup = itemView.findViewById(R.id.allRadioButton);
            mTextView1 = itemView.findViewById(R.id.textView);
            result = itemView.findViewById(R.id.res);
        }
    }
    public ExampleAdapter(ArrayList<Students>exampleList)
    {
        mExampleList = exampleList;
     }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ExampleViewHolder holder, int position) {
        Students currentItem = mExampleList.get(position);

        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.present:
                        holder.result.setText("Present");
                                  break;
                    case R.id.absent:
                        holder.result.setText("Absent");
                        break;
                    case R.id.excuse:
                        holder.result.setText("Excuse");
                        break;
                }
            }
        });
        holder.mTextView1.setText(currentItem.getName());
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
