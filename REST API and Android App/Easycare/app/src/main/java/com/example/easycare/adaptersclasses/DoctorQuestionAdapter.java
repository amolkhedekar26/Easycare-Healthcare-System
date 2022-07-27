package com.example.easycare.adaptersclasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easycare.R;
import com.example.easycare.dataclasses.Questions;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;

public class DoctorQuestionAdapter extends RecyclerView.Adapter<DoctorQuestionAdapter.QuestionViewHolder> {
    ArrayList<Questions> questionsArrayList;
    Questions currentItem;
    Context context;
    private OnItemClickListener mListener;
    public interface OnItemClickListener{
        void OnItemOnClick(int position);
    }
    public void setOnItemClickListener(DoctorQuestionAdapter.OnItemClickListener listener)
    {
        mListener=listener;
    }


    public static class QuestionViewHolder extends RecyclerView.ViewHolder
    {
        public TextView textView_question, textView_answer;
        public MaterialCardView cardView;
        public EditText write_answer;
        public Button post_button;
        Chip chip;
        public QuestionViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            textView_question = itemView.findViewById(R.id.question);
            textView_answer = itemView.findViewById(R.id.answer);
            chip=itemView.findViewById(R.id.chip);
            post_button=itemView.findViewById(R.id.post_answer);

            post_button.setOnClickListener(new View.OnClickListener() {
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
    public DoctorQuestionAdapter(ArrayList<Questions> arrayList)
    {
        questionsArrayList=arrayList;

    }
    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_question_card, parent, false);

        DoctorQuestionAdapter.QuestionViewHolder questionViewHolder= new DoctorQuestionAdapter.QuestionViewHolder(v,mListener);
        return questionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final QuestionViewHolder holder, int position) {
        currentItem = questionsArrayList.get(position);

        if(currentItem.isPosted()==true)
        {
            holder.chip.setText(String.valueOf(position));
            holder.post_button.setVisibility(View.INVISIBLE);
        }
        else
        {
            holder.chip.setText(String.valueOf(position));
            holder.textView_answer.setVisibility(View.INVISIBLE);
            holder.post_button.setVisibility(View.VISIBLE);
        }
        holder.textView_question.setText(currentItem.getQuery());
        holder.textView_answer.setText(currentItem.getAnswer());


    }

    @Override
    public int getItemCount() {
        return questionsArrayList.size();
    }


}
