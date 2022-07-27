package com.example.easycare.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easycare.R;
import com.example.easycare.api.easycareapi;
import com.example.easycare.dataclasses.Post_Put_Response;
import com.example.easycare.home;
import com.thekhaeng.pushdownanim.PushDownAnim;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.easycare.Constants.Constant.BASE_NAME;

public class signup extends AppCompatActivity {
    EditText username,email,password1,password;
    Button signup,login;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        progressDialog=new ProgressDialog(signup.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Signing up");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        username=(EditText)findViewById(R.id.username_input);
        email=(EditText)findViewById(R.id.email_input);
        password1=(EditText)findViewById(R.id.password1_input);
        password=(EditText)findViewById(R.id.password_input);
        signup=(Button)findViewById(R.id.signup_button);
        login=(Button)findViewById(R.id.login_button);
        PushDownAnim.setPushDownAnimTo( signup )
                .setScale(PushDownAnim.MODE_SCALE,0.90F)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean valid_username = username.getText().toString().trim().length()==0;
                        boolean valid_email = Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches();
                        boolean if_password_matching = password1.getText().toString().equals(password.getText().toString());
                        boolean valid_password = password1.getText().toString().length()>=8;
                        if(valid_username){
                            Toast.makeText(getApplicationContext(),"Signin Failed",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            username.setText("");
                            username.setError("Please Enter username");
                        }
                        if(!valid_email){
                            Toast.makeText(getApplicationContext(),"Signin Failed",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            email.setText("");
                            email.setError("Please enter valid email");
                        }
                        if(valid_password){

                            if (!if_password_matching) {
                                Toast.makeText(getApplicationContext(),"Signin Failed",Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                password1.setText("");
                                password1.setError("Passwords doesn't match");
                            }
                        }
                        if(!valid_password)
                        {
                            if(password.getText().toString().equals("") || password1.getText().toString().equals(""))
                            {
                                Toast.makeText(getApplicationContext(),"Signin Failed",Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                password.setError("Please enter password ");
                                password.setText("");
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Signin Failed",Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                password.setError("Password must be of at least 8 letters ");
                                password.setText("");
                            }
                        }

                        if(valid_email && if_password_matching && valid_password && !valid_username){
                            progressDialog.show();
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(BASE_NAME)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            final easycareapi easycareapi = retrofit.create(easycareapi.class);
                            Call<Post_Put_Response> call=easycareapi.doSignup(username.getText().toString(),password1.getText().toString(),email.getText().toString());
                            call.enqueue(new Callback<Post_Put_Response>() {
                                @Override
                                public void onResponse(Call<Post_Put_Response> call, Response<Post_Put_Response> response) {
                                    Post_Put_Response post_put_response=response.body();
                                    if(post_put_response.getResult().equals("User already exits"))
                                    {
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(),"Signin Failed",Toast.LENGTH_SHORT).show();
                                        username.setError("User already exits");
                                        username.setText("");
                                    }
                                    if(post_put_response.getResult().equals("Success"))
                                    {
                                        Intent i=new Intent(getApplicationContext(), home.class);
                                        startActivity(i);
                                        Toast.makeText(getApplicationContext(),"Account created successfully",Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }

                                }

                                @Override
                                public void onFailure(Call<Post_Put_Response> call, Throwable t) {

                                }
                            });
                        }
                    }
                });
        /*signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean valid_username = username.getText().toString().trim().length()==0;
                boolean valid_email = Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches();
                boolean if_password_matching = password1.getText().toString().equals(password.getText().toString());
                boolean valid_password = password1.getText().toString().length()>=8;
                if(valid_username){
                    Toast.makeText(getApplicationContext(),"Signin Failed",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    username.setText("");
                    username.setError("Please Enter username");
                }
                if(!valid_email){
                    Toast.makeText(getApplicationContext(),"Signin Failed",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    email.setText("");
                    email.setError("Please enter valid email");
                }
                if(valid_password){
                    
                    if (!if_password_matching) {
                        Toast.makeText(getApplicationContext(),"Signin Failed",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        password.setText("");
                        password.setError("Passwords doesn't match");
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Signin Failed",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    password1.setError("Password must be of at least 8 letters ");
                    password1.setText("");
                }
                if(valid_email && if_password_matching && valid_password && !valid_username){
                    progressDialog.show();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://192.168.43.50:8000/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    final easycareapi easycareapi = retrofit.create(easycareapi.class);
                    Call<Post_Put_Response> call=easycareapi.doSignup(username.getText().toString(),password1.getText().toString(),email.getText().toString());
                    call.enqueue(new Callback<Post_Put_Response>() {
                        @Override
                        public void onResponse(Call<Post_Put_Response> call, Response<Post_Put_Response> response) {
                            Post_Put_Response post_put_response=response.body();
                            if(post_put_response.getResult().equals("User already exits"))
                            {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Signin Failed",Toast.LENGTH_SHORT).show();
                                username.setError("User already exits");
                                username.setText("");
                            }
                            if(post_put_response.getResult().equals("Success"))
                            {
                                Intent i=new Intent(getApplicationContext(),home.class);
                                startActivity(i);
                                Toast.makeText(getApplicationContext(),"Account created successfully",Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        }

                        @Override
                        public void onFailure(Call<Post_Put_Response> call, Throwable t) {

                        }
                    });
                }
            }
        });*/
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), com.example.easycare.Authentication.login.class);
                startActivity(i);
            }
        });
    }
}
