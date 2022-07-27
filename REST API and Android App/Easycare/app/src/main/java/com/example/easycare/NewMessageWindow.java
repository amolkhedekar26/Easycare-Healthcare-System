package com.example.easycare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.easycare.Authentication.login;
import com.example.easycare.Fragments.Chat;
import com.example.easycare.adaptersclasses.ConsultDoctorAdapter;
import com.example.easycare.api.easycareapi;
import com.example.easycare.dataclasses.ConsultDoctors;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.easycare.Constants.Constant.BASE_NAME;
import static com.example.easycare.Constants.Constant.MY_PREF_NAME;
import static com.example.easycare.Constants.Constant.USER_TOKEN;

public class NewMessageWindow extends AppCompatActivity {
    RecyclerView recyclerView;
    ConsultDoctorAdapter adapter;
    ProgressDialog progressDialog;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ConsultDoctors> doctorsArrayList;
    SharedPreferences SP;
    String user;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message_window);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        back=findViewById(R.id.backarrow);
        layoutManager = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        progressDialog=new ProgressDialog(NewMessageWindow.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        SP=getSharedPreferences(MY_PREF_NAME, Context.MODE_PRIVATE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        loaddoctors();
    }
    public void loaddoctors() {

        if (SP.contains(USER_TOKEN)) {
            user = SP.getString(USER_TOKEN, null);
        }
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_NAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        easycareapi api = retrofit.create(easycareapi.class);

        Call<List<ConsultDoctors>> call = api.getConsultDoctors("Token " + user);

        call.enqueue(new Callback<List<ConsultDoctors>>() {
            @Override
            public void onResponse(Call<List<ConsultDoctors>> call, final Response<List<ConsultDoctors>> response) {
                if (!response.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(getBaseContext(), "You have to login first", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(NewMessageWindow.this, login.class);
                    startActivity(intent);
                    return;
                }
                final List<ConsultDoctors> doctors = response.body();
                doctorsArrayList = new ArrayList<ConsultDoctors>();
                if(doctors.size()==0)
                {
                }

                for (ConsultDoctors doc : doctors) {
                    ConsultDoctors item = new ConsultDoctors();
                    item.setFirst_name(doc.getFirst_name());
                    item.setLast_name(doc.getLast_name());
                    item.setExperience(doc.getExperience());
                    item.setSpeciality(doc.getSpeciality());
                    doctorsArrayList.add(item);


                }
                adapter = new ConsultDoctorAdapter(doctorsArrayList);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();
                adapter.setOnItemClickListener(new ConsultDoctorAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemOnClick(int position) {
                        Toast.makeText(getBaseContext(),String.valueOf(position),Toast.LENGTH_LONG).show();
                       /* View view1=getLayoutInflater().inflate(R.layout.bottom_sheet,null);
                        context=view1.getContext();
                        dialog = new BottomSheetDialog(context,R.style.BottomSheetDialog);
                        dialog.setContentView(view1);
                        TextView t1=(TextView)view1.findViewById(R.id.textView10);
                        t1.setText("amol");
                        dialog.show();*/
                        Intent intent=new Intent(NewMessageWindow.this, Chat.class);
                        ConsultDoctors doct=doctors.get(position);
                        //Toast.makeText(getBaseContext(),String.valueOf(position),Toast.LENGTH_LONG).show();
                        //Toast.makeText(getBaseContext(),doct.getUser_name(),Toast.LENGTH_LONG).show();
                        intent.putExtra("tag",doct.getSpeciality());
                        intent.putExtra("doc_name",doct.getUser_name());
                        startActivity(intent);

                    }
                });


            }

            @Override
            public void onFailure(Call<List<ConsultDoctors>> call, Throwable t) {
                Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
