package com.example.easycare.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easycare.MyBottomNavigation;
import com.example.easycare.R;
import com.example.easycare.api.easycareapi;
import com.example.easycare.dataclasses.Profile;
import com.example.easycare.dataclasses.Token;
import com.example.easycare.home;
import com.thekhaeng.pushdownanim.PushDownAnim;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.easycare.Constants.Constant.BASE_NAME;
import static com.example.easycare.Constants.Constant.MY_PREF_NAME;
import static com.example.easycare.Constants.Constant.USER_TOKEN;
import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_STATIC_DP;

public class login extends AppCompatActivity {
    EditText username,password;
    Button login,forgot_password;
    TextView signup;
    ProgressDialog progressDialog;
    SharedPreferences SP;
    String user;
    String newtoken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        SP=getSharedPreferences(MY_PREF_NAME,MODE_PRIVATE);
        progressDialog=new ProgressDialog(login.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Logging in");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        if(SP.contains(USER_TOKEN))
        {
            Intent i=new Intent(getApplicationContext(), home.class);
            startActivity(i);
        }

        username=(EditText)findViewById(R.id.username_input);
        password=(EditText)findViewById(R.id.password_input);
        login=(Button)findViewById(R.id.login_button);
        signup=(TextView) findViewById(R.id.signup_button);
        forgot_password=(Button)findViewById(R.id.forgot_password_button);
        PushDownAnim.setPushDownAnimTo( login )
                .setScale(  MODE_STATIC_DP,
                         PushDownAnim.DEFAULT_PUSH_STATIC  )
                .setDurationPush( PushDownAnim.DEFAULT_PUSH_DURATION )
                .setDurationRelease( PushDownAnim.DEFAULT_RELEASE_DURATION )
                .setInterpolatorPush( PushDownAnim.DEFAULT_INTERPOLATOR )
                .setInterpolatorRelease( PushDownAnim.DEFAULT_INTERPOLATOR )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        progressDialog.show();
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(BASE_NAME)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        final easycareapi easycareapi = retrofit.create(easycareapi.class);
                        Call<Token> call=easycareapi.getToken(username.getText().toString(),password.getText().toString());
                        call.enqueue(new Callback<Token>() {
                            @Override
                            public void onResponse(Call<Token> call, Response<Token> response) {
                                if (!response.isSuccessful()) {
                                    progressDialog.dismiss();
                                    username.setText("");
                                    password.setText("");
                                    username.setError("Invalid username/password");
                                    password.setError("Invalid username/password");
                                    username.requestFocus();
                                    return;
                                }

                                Token token=response.body();
                                if(token!=null)
                                {

                                    Intent i=new Intent(getApplicationContext(), MyBottomNavigation.class);
                                    startActivity(i);
                                    SharedPreferences.Editor editor=SP.edit();
                                    editor.putString(USER_TOKEN,token.getToken());
                                    editor.putBoolean("haslogged",true);
                                    editor.apply();
                                    progressDialog.dismiss();
                                    Call<Profile> call1 = easycareapi.getProfile("Token " + token.getToken());
                                    call1.enqueue(new Callback<Profile>() {
                                        @Override
                                        public void onResponse(Call<Profile> call, Response<Profile> response) {
                                            Profile profile=response.body();
                                            if(profile.isIs_doctor()) {
                                                Toast.makeText(getApplicationContext(), String.valueOf(profile.isIs_doctor()), Toast.LENGTH_LONG).show();
                                            }
                                            SharedPreferences.Editor editor=SP.edit();
                                            editor.putBoolean("is_doctor",profile.isIs_doctor());
                                            editor.apply();
                                        }

                                        @Override
                                        public void onFailure(Call<Profile> call, Throwable t) {

                                        }
                                    });
                                    finish();
                                }

                            }

                            @Override
                            public void onFailure(Call<Token> call, Throwable t) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Internet connection is unavailable\n"+t.getMessage(),Toast.LENGTH_LONG).show();

                            }
                        });
                    }
                });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.show();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_NAME)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                final easycareapi easycareapi = retrofit.create(easycareapi.class);
                Call<Token> call=easycareapi.getToken(username.getText().toString(),password.getText().toString());
                call.enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        if (!response.isSuccessful()) {
                            progressDialog.dismiss();
                            username.setText("");
                            password.setText("");
                            username.setError("Invalid username/password");
                            password.setError("Invalid username/password");
                            username.requestFocus();
                            return;
                        }

                        Token token=response.body();
                        if(token!=null)
                        {

                            Intent i=new Intent(getApplicationContext(),home.class);
                            startActivity(i);
                            SharedPreferences.Editor editor=SP.edit();
                            editor.putString(USER_TOKEN,token.getToken());
                            editor.putBoolean("haslogged",true);
                            editor.apply();
                            progressDialog.dismiss();
                            Call<Profile> call1 = easycareapi.getProfile("Token " + token.getToken());
                            call1.enqueue(new Callback<Profile>() {
                                @Override
                                public void onResponse(Call<Profile> call, Response<Profile> response) {
                                    Profile profile=response.body();
                                    if(profile.isIs_doctor()) {
                                        Toast.makeText(getApplicationContext(), String.valueOf(profile.isIs_doctor()), Toast.LENGTH_LONG).show();
                                    }
                                    SharedPreferences.Editor editor=SP.edit();
                                    editor.putBoolean("is_doctor",profile.isIs_doctor());
                                    editor.apply();
                                }

                                @Override
                                public void onFailure(Call<Profile> call, Throwable t) {

                                }
                            });
                        }

                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Internet connection is unavailable\n"+t.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), com.example.easycare.Authentication.signup.class);
                startActivity(i);
            }
        });
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(login.this, Forgot_password.class);
                startActivity(intent);
            }
        });
    }

}
