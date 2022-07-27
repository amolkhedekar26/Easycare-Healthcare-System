package com.example.easycare;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class modalsheet extends AppCompatActivity {
    Button button;
    BottomSheetDialog dialog ;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modalsheet);
        button=findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                View view1=getLayoutInflater().inflate(R.layout.bottom_sheet,null);
                context=view1.getContext();
                dialog = new BottomSheetDialog(context,R.style.BottomSheetDialog);
                dialog.setContentView(view1);
                TextView t1=(TextView)view1.findViewById(R.id.textView10);
                t1.setText("amol");
                dialog.show();

            }
        });
    }

}
