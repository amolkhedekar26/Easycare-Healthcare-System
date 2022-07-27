package com.example.easycare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easycare.adaptersclasses.MedicineAdapter;
import com.example.easycare.api.easycareapi;
import com.example.easycare.dataclasses.Medicines;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.easycare.Constants.Constant.BASE_NAME;
import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_STATIC_DP;

public class MedicineList extends AppCompatActivity {
    RecyclerView recyclerView;
    MedicineAdapter adapter;
    TextView dis_name;
    String d;
    ImageView back;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Medicines> medicinesArrayList;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_list);
        progressDialog = new ProgressDialog(MedicineList.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        back=findViewById(R.id.leftarrow);
        recyclerView = (RecyclerView) findViewById(R.id.medicinelist);
        layoutManager = new GridLayoutManager(getBaseContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        d=getIntent().getStringExtra("dis_name");
        dis_name=findViewById(R.id.diseasename);
        dis_name.setText(d);
        PushDownAnim.setPushDownAnimTo( back )
                .setScale(PushDownAnim.MODE_SCALE,0.89F)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
        loadmedicines(d);
    }

    public void loadmedicines(String name) {

        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_NAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        easycareapi api = retrofit.create(easycareapi.class);

        Call<List<Medicines>> call = api.getMedicines(name);
        call.enqueue(new Callback<List<Medicines>>() {
            @Override
            public void onResponse(Call<List<Medicines>> call, Response<List<Medicines>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getBaseContext(), response.code(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    return;
                }
                List<Medicines> medicines = response.body();
                medicinesArrayList = new ArrayList<Medicines>();
                if (medicines.size() == 0) {

                }

                for (Medicines med : medicines) {
                    Medicines item = new Medicines();
                    item.setMed_name(med.getMed_name());
                    item.setMed_disaese(med.getMed_disaese());
                    medicinesArrayList.add(item);


                }
                adapter = new MedicineAdapter(medicinesArrayList);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();
                /*adapter.setOnItemClickListener(new MedicineAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemOnClick(int position) {
                        Toast.makeText(getContext(),String.valueOf(position),Toast.LENGTH_LONG).show();
                        View view1=getLayoutInflater().inflate(R.layout.bottom_sheet,null);
                        context=view1.getContext();
                        dialog = new BottomSheetDialog(context,R.style.BottomSheetDialog);
                        dialog.setContentView(view1);
                        TextView t1=(TextView)view1.findViewById(R.id.textView10);
                        t1.setText("amol");
                        dialog.show();
                    }
                });*/

            }

            @Override
            public void onFailure(Call<List<Medicines>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getBaseContext(),"Internet connection is unavailable\n"+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
}
