package com.example.easycare.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.easycare.R;
import com.thekhaeng.pushdownanim.PushDownAnim;

public class reset_success extends AppCompatActivity {
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_success);
        login=findViewById(R.id.verify_email_button);
        PushDownAnim.setPushDownAnimTo( login )
                .setScale(PushDownAnim.MODE_SCALE,0.90F)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(reset_success.this, com.example.easycare.Authentication.login.class);
                        startActivity(intent);
                    }
                });
    }
}
