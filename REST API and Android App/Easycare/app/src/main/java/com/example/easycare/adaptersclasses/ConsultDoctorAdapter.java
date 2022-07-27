package com.example.easycare.adaptersclasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.easycare.R;
import com.example.easycare.dataclasses.ConsultDoctors;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class ConsultDoctorAdapter extends RecyclerView.Adapter<ConsultDoctorAdapter.ConsultDoctorViewHolder> {

    ArrayList<ConsultDoctors> doctorsArrayList;
    ConsultDoctors currentItem;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void OnItemOnClick(int position);
    }
    public void setOnItemClickListener(ConsultDoctorAdapter.OnItemClickListener listener)
    {
        mListener=listener;
    }
    public static class ConsultDoctorViewHolder extends RecyclerView.ViewHolder
    {
        public TextView textView1, textView2;
        public MaterialCardView cardView;
        public ConsultDoctorViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.textView12);
            textView2 = itemView.findViewById(R.id.textView13);
            cardView = itemView.findViewById(R.id.cardview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null)
                    {
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.OnItemOnClick(position);
                        }
                    }
                }
            });
        }
    }
    public ConsultDoctorAdapter(ArrayList<ConsultDoctors> arrayList)
    {
        doctorsArrayList=arrayList;
    }
    @NonNull
    @Override
    public ConsultDoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.consult_doctor_card, parent, false);
        ConsultDoctorViewHolder consultDoctorViewHolder=new ConsultDoctorViewHolder(v,mListener);

        return consultDoctorViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ConsultDoctorViewHolder holder, int position) {
        currentItem = doctorsArrayList.get(position);

        holder.textView1.setText("Dr."+currentItem.getFirst_name()+currentItem.getLast_name());
        holder.textView2.setText(currentItem.getSpeciality()+"\n"+currentItem.getExperience());

    }

    @Override
    public int getItemCount() {
        return doctorsArrayList.size();
    }


}
