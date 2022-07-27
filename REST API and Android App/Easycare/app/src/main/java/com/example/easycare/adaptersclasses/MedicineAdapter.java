package com.example.easycare.adaptersclasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easycare.R;
import com.example.easycare.dataclasses.Medicines;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder> {

    ArrayList<Medicines> medicinesArrayList;
    Medicines currentItem;
    private MedicineAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener{
        void OnItemOnClick(int position);
    }
    public void setOnItemClickListener(MedicineAdapter.OnItemClickListener listener)
    {
        mListener=listener;
    }
    public static class MedicineViewHolder extends RecyclerView.ViewHolder {
        public TextView textView1, textView2;
        public MaterialCardView cardView;

        public MedicineViewHolder(@NonNull View itemView, final MedicineAdapter.OnItemClickListener listener) {
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

    public MedicineAdapter(ArrayList<Medicines> arrayList) {
        medicinesArrayList = arrayList;

    }

    @NonNull
    @Override
    public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_card, parent, false);
        MedicineAdapter.MedicineViewHolder medicineViewHolder = new MedicineAdapter.MedicineViewHolder(v,mListener);
        return medicineViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineViewHolder holder, int position) {
        currentItem = medicinesArrayList.get(position);
        holder.textView1.setText(currentItem.getMed_name());
        holder.textView2.setText(currentItem.getMed_disaese()+"\n"+currentItem.getMed_brand());
    }

    @Override
    public int getItemCount() {
        return medicinesArrayList.size();
    }


}
