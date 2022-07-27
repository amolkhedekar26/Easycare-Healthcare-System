package com.example.easycare.Extra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.easycare.R;
import com.example.easycare.adaptersclasses.DoctorAdapter;
import com.example.easycare.api.easycareapi;
import com.example.easycare.dataclasses.Doctors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class doctor_home extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Doctors> doctorsArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_home);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.50:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        easycareapi api = retrofit.create(easycareapi.class);

        Call<List<Doctors>> call = api.getDoctors("dapoli"+","+"chiplun"+","+"ratnagiri",null);
        call.enqueue(new Callback<List<Doctors>>() {
            @Override
            public void onResponse(Call<List<Doctors>> call, Response<List<Doctors>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),response.code(),Toast.LENGTH_LONG).show();

                    return;
                }
                List<Doctors> doctors = response.body();
                doctorsArrayList=new ArrayList<Doctors>();

                for (Doctors doc : doctors) {
                    Doctors item=new Doctors();
                    item.setDoc_name(doc.getDoc_name());
                    item.setDoc_address(doc.getDoc_address());
                    doctorsArrayList.add(item);


                }
                adapter=new DoctorAdapter(doctorsArrayList);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<List<Doctors>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

}
