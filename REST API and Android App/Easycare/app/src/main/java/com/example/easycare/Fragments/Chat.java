package com.example.easycare.Fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easycare.R;
import com.example.easycare.adaptersclasses.DoctorQuestionAdapter;
import com.example.easycare.adaptersclasses.QuestionAdapter;
import com.example.easycare.api.easycareapi;
import com.example.easycare.dataclasses.Post_Put_Response;
import com.example.easycare.dataclasses.Questions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.easycare.Constants.Constant.BASE_NAME;
import static com.example.easycare.Constants.Constant.MY_PREF_NAME;
import static com.example.easycare.Constants.Constant.USER_TOKEN;

public class Chat extends AppCompatActivity {
    TextView textView;
    ImageView send,back;
    EditText editText;
    QuestionAdapter adapter;
    DoctorQuestionAdapter adapter3;
    LinearLayoutManager layoutManager;
    RecyclerView recyclerView;
    String tag,doc_name,user,user_name;
    LinearLayout linearLayout;
    boolean t;
    SharedPreferences SP;
    BottomSheetDialog dialog ;
    Context context;
    int que_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recyclerView = findViewById(R.id.recyclerview);
        linearLayout=(LinearLayout)findViewById(R.id.linear);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        textView = findViewById(R.id.textViewtitle);
        editText = (EditText) findViewById(R.id.type_query);
        send = (ImageView) findViewById(R.id.send);
        back=findViewById(R.id.backarrow);
        SP = getSharedPreferences(MY_PREF_NAME, MODE_PRIVATE);
        tag = getIntent().getStringExtra("tag");
        doc_name = getIntent().getStringExtra("doc_name");
        //textView.setText("Dr " + doc_name);
      //  Toast.makeText(getApplicationContext(),doc_name,Toast.LENGTH_SHORT).show();
        final String query = editText.getText().toString();
        if (SP.contains(USER_TOKEN)) {
            user = SP.getString(USER_TOKEN, null);
        }
        if (SP.contains("is_doctor")) {
            t = SP.getBoolean("is_doctor", false);
            if (t == true) {
                user_name = getIntent().getStringExtra("user_name");
               // textView.setText( user_name);
                linearLayout.setVisibility(View.INVISIBLE);
                //Toast.makeText(getApplicationContext(),user_name,Toast.LENGTH_SHORT).show();
                displayDoctorQuestions();

            }
        }

        displayquestions();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String query = editText.getText().toString();
                if (SP.contains(USER_TOKEN)) {
                    user = SP.getString(USER_TOKEN, null);
                }
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_NAME)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                final easycareapi easycareapi = retrofit.create(easycareapi.class);
                Call<Post_Put_Response> call = easycareapi.submitQuery("Token " + user, query, doc_name, tag);
                call.enqueue(new Callback<Post_Put_Response>() {
                    @Override
                    public void onResponse(Call<Post_Put_Response> call, Response<Post_Put_Response> response) {
                        Post_Put_Response post_put_response = response.body();
                        if (post_put_response.getResult().equals("Success")) {
                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                            /*Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl("http://192.168.43.50:8000/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            final easycareapi easycareapi = retrofit.create(easycareapi.class);
                            Call<List<Questions>> call1 = easycareapi.getQuestions("Token " + user);
                            call1.enqueue(new Callback<List<Questions>>() {
                                @Override
                                public void onResponse(Call<List<Questions>> call, Response<List<Questions>> response) {
                                    List<Questions> questionsList = response.body();

                                    ArrayList<Questions> questionsArrayList = new ArrayList<Questions>();
                                    for (Questions q : questionsList) {
                                        Questions item = new Questions();
                                        item.setQuery(q.getQuery());
                                        item.setAnswer(q.getAnswer());
                                        questionsArrayList.add(item);
                                    }
                                    Collections.reverse(questionsArrayList);
                                    adapter = new QuestionAdapter(questionsArrayList);
                                    recyclerView.setAdapter(adapter);
                                    recyclerView.smoothScrollToPosition(0);
                                    editText.setText("");
                                }

                                @Override
                                public void onFailure(Call<List<Questions>> call, Throwable t) {

                                }
                            });*/
                            displayquestions();
                        }
                    }

                    @Override
                    public void onFailure(Call<Post_Put_Response> call, Throwable t) {

                    }
                });

            }
        });

    }

    public void displayquestions() {
        if(SP.contains("is_doctor")) {
            boolean t = SP.getBoolean("is_doctor", false);
            Toast.makeText(getBaseContext(), String.valueOf(t), Toast.LENGTH_SHORT).show();
            if (t == true) {
                user_name = getIntent().getStringExtra("user_name");
                //Toast.makeText(getBaseContext(), String.valueOf(t), Toast.LENGTH_SHORT).show();
                textView.setText(user_name);
            }
            else {
                textView.setText("dr."+doc_name);
            }
        }


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_NAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final easycareapi easycareapi = retrofit.create(easycareapi.class);
        Call<List<Questions>> cal=easycareapi.getQuestions("Token " + user, doc_name);
        cal.enqueue(new Callback<List<Questions>>() {
            @Override
            public void onResponse(Call<List<Questions>> call, Response<List<Questions>> response) {

                final List<Questions> questionsList = response.body();
                ArrayList<Questions> questionsArrayList=new ArrayList<Questions>();
                for (Questions q : questionsList) {
                    Questions item = new Questions();
                    item.setQuery(q.getQuery());
                    //item.setAnswer(q.getAnswer());
                    if(q.isPosted())
                    {
                        //Toast.makeText(getApplicationContext(),String.valueOf(q.isPosted()),Toast.LENGTH_SHORT).show();
                        item.setAnswer(q.getAnswer());
                    }
                    else {
                        item.setAnswer("X Will reply soon");

                    }
                    questionsArrayList.add(item);
                }
                //Collections.reverse(questionsArrayList);
                adapter = new QuestionAdapter(questionsArrayList);
                recyclerView.setAdapter(adapter);
                recyclerView.smoothScrollToPosition(0);
                editText.setText("");
                /*adapter.setOnItemClickListener(new QuestionAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemOnClick(int position) {
                        Questions quest=questionsList.get(position);
                        Toast.makeText(getApplicationContext(),quest.getId()+" "+quest.getQuery(),Toast.LENGTH_SHORT).show();
                    }
                });*/

            }

            @Override
            public void onFailure(Call<List<Questions>> call, Throwable t) {

            }
        });
    }

    public void displayDoctorQuestions() {
        textView.setText(doc_name);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_NAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final easycareapi easycareapi = retrofit.create(easycareapi.class);
        Call<List<Questions>> call1 = easycareapi.getAskedQuestions("Token " + user, user_name);
        call1.enqueue(new Callback<List<Questions>>() {
            @Override
            public void onResponse(Call<List<Questions>> call, Response<List<Questions>> response) {

                final List<Questions> questionsList = response.body();
                final ArrayList<Questions> questionsArrayList = new ArrayList<Questions>();
                for (Questions q : questionsList) {
                    Questions item = new Questions();
                    item.setQuery(q.getQuery());
                    if(q.isPosted())
                    {





                        //Toast.makeText(getApplicationContext(),String.valueOf(q.isPosted()),Toast.LENGTH_SHORT).show();
                        item.setAnswer(q.getAnswer());
                        item.setPosted(q.isPosted());
                        //int is=R.layout.doctor_question_card;


                    }
                    else {

                        if(t)
                        {
                            item.setAnswer(null);
                            /*View v1=getLayoutInflater().inflate(R.layout.doctor_question_card,null);

                            MaterialButton materialButton=v1.findViewById(R.id.post_answer);
                            materialButton.setVisibility(View.VISIBLE);

                            Toast.makeText(getApplicationContext(),String.valueOf(materialButton.getText()),Toast.LENGTH_SHORT).show();*/
                        }
                        else {
                            item.setAnswer("X Will reply soon");
                        }

                    }
                    questionsArrayList.add(item);
                }
                //Collections.reverse(questionsArrayList);
                adapter3 = new DoctorQuestionAdapter(questionsArrayList);
                recyclerView.setAdapter(adapter3);
                recyclerView.smoothScrollToPosition(0);
                editText.setText("");
                adapter3.setOnItemClickListener(new DoctorQuestionAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemOnClick(int position) {
                        final Questions quest=questionsList.get(position);
                        que_id=quest.getId();
                        View view1=getLayoutInflater().inflate(R.layout.submit_answer_sheet,null);
                        context=view1.getContext();
                        dialog = new BottomSheetDialog(context,R.style.BottomSheetDialog);
                        dialog.setContentView(view1);
                        dialog.show();
                        final EditText e=(EditText)view1.findViewById(R.id.answer_text);
                        Button submit=(Button)view1.findViewById(R.id.submit_answer);
                        submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(BASE_NAME)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                final easycareapi easycareapi = retrofit.create(easycareapi.class);
                                Call<Post_Put_Response> call2=easycareapi.postAnswer("Token " + user,que_id,e.getText().toString());
                                call2.enqueue(new Callback<Post_Put_Response>() {
                                    @Override
                                    public void onResponse(Call<Post_Put_Response> call, Response<Post_Put_Response> response) {
                                        Post_Put_Response post_put_response=response.body();
                                        if(post_put_response.getResult().equals("Success"))
                                        {
                                            Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                            displayDoctorQuestions();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Post_Put_Response> call, Throwable t) {

                                    }
                                });
                            }
                        });
                        //TextView t1=(TextView)view1.findViewById(R.id.textView10);
                        //Toast.makeText(getApplicationContext(),String.valueOf(quest.getId())+quest.getQuery(),Toast.LENGTH_LONG).show();
                        /*Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://192.168.43.50:8000/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        final easycareapi easycareapi = retrofit.create(easycareapi.class);
                        Call<Post_Put_Response> call2=easycareapi.postAnswer("Token " + user,quest.getId(),"asdfghjk");
                        call2.enqueue(new Callback<Post_Put_Response>() {
                            @Override
                            public void onResponse(Call<Post_Put_Response> call, Response<Post_Put_Response> response) {
                                Post_Put_Response post_put_response=response.body();
                                if(post_put_response.getResult().equals("Success"))
                                {
                                    Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Post_Put_Response> call, Throwable t) {

                            }
                        });*/
                    }
                });


            }

            @Override
            public void onFailure(Call<List<Questions>> call, Throwable t) {

            }
        });
    }
}
