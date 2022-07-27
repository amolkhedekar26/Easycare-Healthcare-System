package com.example.easycare.adaptersclasses;

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

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {
    ArrayList<Questions> questionsArrayList;
    Questions currentItem;
    private QuestionAdapter.OnItemClickListener mListener;
    public interface OnItemClickListener{
        void OnItemOnClick(int position);
    }
    public void setOnItemClickListener(QuestionAdapter.OnItemClickListener listener)
    {
        mListener=listener;
    }
    public static class QuestionViewHolder extends RecyclerView.ViewHolder
    {
        public TextView textView_question, textView_answer;
        public MaterialCardView cardView;
        public EditText write_answer;
        public Button post_button;
        public QuestionViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);
            textView_question = itemView.findViewById(R.id.question);
            textView_answer = itemView.findViewById(R.id.answer);
          //  cardView = itemView.findViewById(R.id.cardview);

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
    public QuestionAdapter(ArrayList<Questions> arrayList)
    {
        questionsArrayList=arrayList;

    }
    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.question_card, parent, false);

        QuestionAdapter.QuestionViewHolder questionViewHolder= new QuestionAdapter.QuestionViewHolder(v,mListener);
        return questionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        currentItem = questionsArrayList.get(position);



        holder.textView_question.setText(currentItem.getQuery());
        holder.textView_answer.setText(currentItem.getAnswer());
    }

    @Override
    public int getItemCount() {
        return questionsArrayList.size();
    }


}
