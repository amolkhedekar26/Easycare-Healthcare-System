package com.example.easycare.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.easycare.R;
import com.thekhaeng.pushdownanim.PushDownAnim;

public class email_verify_success_activity extends AppCompatActivity {
    Button open_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verify_success_activity);
        open_email=findViewById(R.id.verify_email_button);
        PushDownAnim.setPushDownAnimTo(open_email)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mailClient = new Intent(Intent.ACTION_VIEW);
                        mailClient.setClassName("com.google.android.gm", "com.google.android.gm.ConversationListActivityGmail");
                        startActivity(mailClient);
                    }
                });
    }
}
