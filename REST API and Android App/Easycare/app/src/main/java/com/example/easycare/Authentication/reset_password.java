package com.example.easycare.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
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

public class reset_password extends AppCompatActivity {
    EditText password,password1,token_text;
    Button reset,r;
    String username,reset_token;
    TextView link_expire,textView20;
    FrameLayout frameLayout;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        password=(EditText)findViewById(R.id.password_text);
        password1=(EditText)findViewById(R.id.password1_text);
        //token_text=(EditText)findViewById(R.id.token_text);
        reset=(Button)findViewById(R.id.reset_button);
        link_expire=(TextView) findViewById(R.id.link_expire_text);
        frameLayout=(FrameLayout) findViewById(R.id.frame);
        textView20=(TextView) findViewById(R.id.textView20);
        linearLayout=(LinearLayout)findViewById(R.id.lina);
       // link_expire.setVisibility(View.INVISIBLE);
        Intent intent = getIntent();
        String action = intent.getAction();
        final Uri data = intent.getData();
       // Toast.makeText(getApplicationContext(),data.getLastPathSegment().toString(),Toast.LENGTH_SHORT).show();
      //  username= getIntent().getStringExtra("username");
        /*r=(Button)findViewById(R.id.reset);*/
        PushDownAnim.setPushDownAnimTo( reset )
        .setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick( View view ){
                boolean if_password_matching = password1.getText().toString().equals(password.getText().toString());
                if(!if_password_matching)
                {
                    password1.setText("");
                    password1.setError("Passwords doesn't match");
                }
                if(password.getText().toString().equals("") || password1.getText().toString().equals(""))
                {
                    password.setError("Please enter password");
                }
                if(if_password_matching) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_NAME)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    final easycareapi easycareapi = retrofit.create(easycareapi.class);
                    Call<Post_Put_Response> call = easycareapi.resetPassword(data.getLastPathSegment().toString(),password.getText().toString());
                    call.enqueue(new Callback<Post_Put_Response>() {
                        @Override
                        public void onResponse(Call<Post_Put_Response> call, Response<Post_Put_Response> response) {
                           if(!response.isSuccessful())
                           {
                               linearLayout.setVisibility(View.INVISIBLE);
                               textView20.setVisibility(View.INVISIBLE);
                               frameLayout.setVisibility(View.INVISIBLE);
                               link_expire.setVisibility(View.VISIBLE);
                               link_expire.setText("Sorry !\n The link has expired.");
                           }
                           else {

                            Post_Put_Response post_put_response=response.body();
                            if(post_put_response.getResult().equals("Success"))
                            {
                                Intent intent=new Intent(reset_password.this, reset_success.class);
                                startActivity(intent);
                            }
                            if(post_put_response.getResult().equals("Fail"))
                            {
                                Toast.makeText(getApplicationContext(),"Error resetting password",Toast.LENGTH_SHORT).show();
                            }
                            if(post_put_response.getResult().equals("Expired"))
                            {
                                linearLayout.setVisibility(View.INVISIBLE);
                                textView20.setVisibility(View.INVISIBLE);
                                frameLayout.setVisibility(View.INVISIBLE);
                                link_expire.setVisibility(View.VISIBLE);
                                link_expire.setText("Sorry !\n The link has expired.");
                            }

                           }
                        }

                        @Override
                        public void onFailure(Call<Post_Put_Response> call, Throwable t) {
                            linearLayout.setVisibility(View.INVISIBLE);
                            textView20.setVisibility(View.INVISIBLE);
                            frameLayout.setVisibility(View.INVISIBLE);
                            link_expire.setVisibility(View.VISIBLE);
                            link_expire.setText("Sorry !\n The link has expired.");
                        }
                    });
                }
            }

        } );

       /* reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean if_password_matching = password1.getText().toString().equals(password.getText().toString());
                if(!if_password_matching)
                {
                    password1.setText("");
                    password1.setError("Passwords doesn't match");
                }
                if(password.getText().toString().equals("") || password1.getText().toString().equals(""))
                {
                    password.setError("Please enter password");
                }
                if(if_password_matching) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://192.168.43.50:8000/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    final easycareapi easycareapi = retrofit.create(easycareapi.class);
                    Call<Post_Put_Response> call = easycareapi.resetPassword(reset_token,password.getText().toString());
                    call.enqueue(new Callback<Post_Put_Response>() {
                        @Override
                        public void onResponse(Call<Post_Put_Response> call, Response<Post_Put_Response> response) {
                            Post_Put_Response post_put_response=response.body();
                            if(post_put_response.getResult().equals("Success"))
                            {
                                Intent intent=new Intent(reset_password.this,login.class);
                                startActivity(intent);
                            }
                            if(post_put_response.getResult().equals("Fail"))
                            {
                                Toast.makeText(getApplicationContext(),"Error resetting password",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Post_Put_Response> call, Throwable t) {

                        }
                    });
                }
            }
        });*/
    }
}
