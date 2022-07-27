package com.example.easycare.adaptersclasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.easycare.R;
import com.example.easycare.dataclasses.User;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    ArrayList<User> userArrayList;
    User currentItem;
    private UserAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener{
        void OnItemOnClick(int position);
    }
    public void setOnItemClickListener(UserAdapter.OnItemClickListener listener)
    {
        mListener=listener;
    }
    public static class UserViewHolder extends RecyclerView.ViewHolder
    {
        public TextView textView1, textView2;
        public MaterialCardView cardView;
        public UserViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
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
    public UserAdapter(ArrayList<User> arrayList)
    {
        userArrayList=arrayList;
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.consult_doctor_card, parent, false);
        UserViewHolder userViewHolder=new UserViewHolder(v,mListener);


        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        currentItem = userArrayList.get(position);

        holder.textView1.setText(currentItem.getFirst_name()+" "+currentItem.getLast_name());
        //holder.textView2.setText(currentItem.getSpeciality()+"\n"+currentItem.getExperience());
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }


}
