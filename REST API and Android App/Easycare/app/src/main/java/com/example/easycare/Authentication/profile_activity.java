package com.example.easycare.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.easycare.R;
import com.example.easycare.api.easycareapi;
import com.example.easycare.dataclasses.Profile;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.easycare.Constants.Constant.BASE_NAME;
import static com.example.easycare.Constants.Constant.MY_PREF_NAME;
import static com.example.easycare.Constants.Constant.USER_TOKEN;

public class profile_activity extends AppCompatActivity {
    SharedPreferences SP;
    ProgressDialog progressDialog;
    final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_NAME)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    easycareapi easycareapi = retrofit.create(easycareapi.class);
    String user;
ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        progressDialog=new ProgressDialog(profile_activity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading Profile");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        SP = getSharedPreferences(MY_PREF_NAME, MODE_PRIVATE);
        imageView=(ImageView)findViewById(R.id.imageView9);
        if (SP.contains(USER_TOKEN)) {
            user = SP.getString(USER_TOKEN, null);
            //Toast.makeText(getApplicationContext(), user, Toast.LENGTH_LONG).show();
            progressDialog.show();
            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_NAME)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            final easycareapi easycareapi = retrofit.create(easycareapi.class);
            Call<Profile> call = easycareapi.getProfile("Token " + user);
            call.enqueue(new Callback<Profile>() {
                @Override
                public void onResponse(Call<Profile> call, Response<Profile> response) {
                    if(SP.contains("is_doctor"))
                    {
                        if(SP.getBoolean("is_doctor",true)) {
                            Toast.makeText(getApplicationContext(), "Doctor", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "User", Toast.LENGTH_SHORT).show();
                        }
                    }
                    //Toast.makeText(getApplicationContext(), response.body().getPhoto_url(), Toast.LENGTH_LONG).show();
                    Call<ResponseBody> call1=easycareapi.getProfileImage(response.body().getPhoto_url());
                    call1.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            progressDialog.dismiss();
                            Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                            imageView.setImageBitmap(bmp);

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                }

                @Override
                public void onFailure(Call<Profile> call, Throwable t) {

                }
            });
        }
    }
}
