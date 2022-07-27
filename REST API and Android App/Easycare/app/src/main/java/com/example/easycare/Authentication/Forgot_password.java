package com.example.easycare.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easycare.R;
import com.example.easycare.api.easycareapi;
import com.example.easycare.dataclasses.Post_Put_Response;
import com.thekhaeng.pushdownanim.PushDownAnim;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.easycare.Constants.Constant.BASE_NAME;

public class Forgot_password extends AppCompatActivity {
    EditText email_text, user_name;
    Button verify, gotologin;
    ProgressDialog progressDialog;

    //    SharedPreferences SP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//        SP=getSharedPreferences(MY_PREF_NAME,MODE_PRIVATE);
        // Toast.makeText(getApplicationContext(),username,Toast.LENGTH_LONG).show();
        progressDialog = new ProgressDialog(Forgot_password.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Verifying email");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        email_text = (EditText) findViewById(R.id.email_input);
        user_name = (EditText) findViewById(R.id.username_input);
        verify = (Button) findViewById(R.id.verify_email_button);
        PushDownAnim.setPushDownAnimTo(verify)
                .setScale(PushDownAnim.MODE_SCALE, 0.90F)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        if (user_name.getText().toString().equals("")) {
                            user_name.setError("Please enter username");
                        }
                        if (email_text.getText().toString().equals("")) {
                            email_text.setError("Please enter email");
                        } else {
                            progressDialog.show();
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(BASE_NAME)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            final easycareapi easycareapi = retrofit.create(easycareapi.class);
                            Call<Post_Put_Response> call = easycareapi.verifyEmail(user_name.getText().toString(), email_text.getText().toString());
                            call.enqueue(new Callback<Post_Put_Response>() {
                                @Override
                                public void onResponse(Call<Post_Put_Response> call, Response<Post_Put_Response> response) {
                                    Post_Put_Response post_put_response = response.body();
                                    if(!response.isSuccessful())
                                    {
                                        progressDialog.dismiss();
                                        Toast.makeText(view.getContext(), "Email verification failed", Toast.LENGTH_LONG).show();
                                    }
                                    if (post_put_response.getResult().equals("Verified")) {
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(Forgot_password.this, email_verify_success_activity.class);
                                        startActivity(intent);
                                    }
                                    if (post_put_response.getResult().equals("Not Verified")) {
                                        progressDialog.dismiss();
                                        Toast.makeText(view.getContext(), "Email verification failed", Toast.LENGTH_LONG).show();
                                    }
                                    if (post_put_response.getResult().equals("Username does not exist")) {
                                        progressDialog.dismiss();
                                        Toast.makeText(view.getContext(), "User does not exist", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Post_Put_Response> call, Throwable t) {
                                    progressDialog.dismiss();


                                }
                            });

                        }
                    }

                    ;

        /*PushDownAnim.setPushDownAnimTo( verify )
                .setScale(PushDownAnim.MODE_SCALE,0.90F)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(user_name.getText().toString().equals(""))
                        {
                            user_name.setError("Please enter username");
                        }
                        if(email_text.getText().toString().equals("") )
                        {
                            email_text.setError("Please enter email");
                        }
                        else {
                            progressDialog.show();
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl("http://192.168.43.50:8000/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            final easycareapi easycareapi = retrofit.create(easycareapi.class);
                            Call<ResetToken> call = easycareapi.verifyEmail(user_name.getText().toString(), email_text.getText().toString());
                            call.enqueue(new Callback<ResetToken>() {
                                @Override
                                public void onResponse(Call<ResetToken> call, Response<ResetToken> response) {

                                    ResetToken rs = response.body();
                                    String reset_token = rs.getToken();
                        *//*SharedPreferences.Editor editor=SP.edit();
                        editor.putString("reset_token",reset_token);
                        editor.apply();
*//*
                                    if (reset_token == null) {
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "Email verification failed", Toast.LENGTH_LONG).show();
                                    } else {
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(Forgot_password.this, reset_password.class);
                                        intent.putExtra("reset_token", reset_token);
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResetToken> call, Throwable t) {

                                }
                            });
                        }
                    }
                });*/

        /*verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.43.50:8000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                final easycareapi easycareapi = retrofit.create(easycareapi.class);
                Call<ResetToken> call=easycareapi.verifyEmail(user_name.getText().toString(),email_text.getText().toString());
                call.enqueue(new Callback<ResetToken>() {
                    @Override
                    public void onResponse(Call<ResetToken> call, Response<ResetToken> response) {

                        ResetToken rs=response.body();
                        String reset_token=rs.getToken();
                        *//*SharedPreferences.Editor editor=SP.edit();
                        editor.putString("reset_token",reset_token);
                        editor.apply();
*//*
                        if(reset_token==null)
                        {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Email verification failed",Toast.LENGTH_LONG).show();
                        }
                        else {
                            progressDialog.dismiss();
                            Intent intent = new Intent(Forgot_password.this, reset_password.class);
                            intent.putExtra("reset_token", reset_token);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResetToken> call, Throwable t) {

                    }
                });

            }
        });*/

                });
    }
}
