package com.example.easycare.adaptersclasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easycare.R;
import com.example.easycare.dataclasses.Hospitals;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder> {
    ArrayList<Hospitals> hospitalsArrayList;
    Hospitals currentItem;
    private HospitalAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener{
        void OnItemOnClick(int position);
    }
    public void setOnItemClickListener(HospitalAdapter.OnItemClickListener listener)
    {
        mListener=listener;
    }
    public static class HospitalViewHolder extends RecyclerView.ViewHolder {
        public TextView textView1, textView2,textView3;
        public MaterialCardView cardView;

        public HospitalViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.textView12);
            textView2 = itemView.findViewById(R.id.textView13);
            textView3=itemView.findViewById(R.id.textView14);
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

    public HospitalAdapter(ArrayList<Hospitals> arrayList) {
        hospitalsArrayList = arrayList;

    }

    @NonNull
    @Override
    public HospitalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hospital_card, parent, false);
        HospitalAdapter.HospitalViewHolder hospitalViewHolder = new HospitalAdapter.HospitalViewHolder(v,mListener);
        return hospitalViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HospitalViewHolder holder, int position) {
        currentItem = hospitalsArrayList.get(position);

        holder.textView1.setText(currentItem.getH_name());
        holder.textView2.setText(currentItem.getH_address());
        holder.textView3.setText(" "+currentItem.getH_location());
    }

    @Override
    public int getItemCount() {
        return hospitalsArrayList.size();
    }


}
