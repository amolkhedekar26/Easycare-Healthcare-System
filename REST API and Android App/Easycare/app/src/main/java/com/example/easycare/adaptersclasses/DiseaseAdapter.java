package com.example.easycare.adaptersclasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easycare.R;
import com.example.easycare.dataclasses.Doctors;
import com.google.android.material.card.MaterialCardView;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;


public class DiseaseAdapter extends RecyclerView.Adapter<DiseaseAdapter.DoctorViewHolder> {
    ArrayList<String> doctorsArrayList;
    String currentItem;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void OnItemOnClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener=listener;
    }
    public static class DoctorViewHolder extends RecyclerView.ViewHolder {

        public TextView textView1, textView2;
        public MaterialCardView cardView;

        public DoctorViewHolder(@NonNull final View itemView, final OnItemClickListener listener) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.textViewd);
            cardView = itemView.findViewById(R.id.cardview);

            PushDownAnim.setPushDownAnimTo( itemView )
                    .setScale(PushDownAnim.MODE_SCALE,0.89F)
                    .setOnClickListener( new View.OnClickListener(){
                        @Override
                        public void onClick( View view ){
                            if(listener!=null)
                            {
                                int position=getAdapterPosition();
                                if(position!=RecyclerView.NO_POSITION)
                                {
                                    listener.OnItemOnClick(position);

                                }



                            }

                        }

                    } );

            /*itemView.setOnClickListener(new View.OnClickListener() {
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
            });*/
        }
    }

    public DiseaseAdapter(ArrayList<String> arrayList) {
        doctorsArrayList = arrayList;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.disease_card, parent, false);
        DoctorViewHolder doctorViewHolder = new DoctorViewHolder(v,mListener);
        return doctorViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        currentItem = doctorsArrayList.get(position);
        holder.textView1.setText(currentItem);


    }



    @Override
    public int getItemCount() {
        return doctorsArrayList.size();
    }


}
