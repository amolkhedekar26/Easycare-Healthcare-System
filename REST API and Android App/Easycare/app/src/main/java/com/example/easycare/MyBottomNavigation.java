package com.example.easycare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.easycare.Authentication.login;
import com.example.easycare.Authentication.profile_activity;
import com.example.easycare.Fragments.ConsultDoctorFragment;
import com.example.easycare.Fragments.DoctorFragment;
import com.example.easycare.Fragments.HospitalFragment;
import com.example.easycare.Fragments.MedicineFragment;
import com.example.easycare.Fragments.MyHomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nex3z.flowlayout.FlowLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import static com.example.easycare.Constants.Constant.MY_PREF_NAME;
import static com.example.easycare.Constants.Constant.USER_TOKEN;

public class MyBottomNavigation extends AppCompatActivity implements MyHomeFragment.OnFragmentInteractionListener, DoctorFragment.OnFragmentInteractionListener, HospitalFragment.OnFragmentInteractionListener, ConsultDoctorFragment.OnFragmentInteractionListener, MedicineFragment.OnFragmentInteractionListener  {
    PopupMenu popupMenu;
    BottomNavigationView bottomNavigationView;
    ImageView imageView;
    BottomSheetDialog dialog;
    SharedPreferences SP;
    FloatingActionButton fb;
    int m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bottom_navigation);
        BottomNavigationView navView = findViewById(R.id.nav_view);
       /* fb=(FloatingActionButton) findViewById(R.id.floatingActionButton);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View view1=getLayoutInflater().inflate(R.layout.filter_sheet,null);
                dialog = new BottomSheetDialog(view1.getContext(),R.style.BottomSheetDialog);
               // final ChipGroup chipGroup=view1.findViewById(R.id.chipgroup);
                Button filter_button=(Button)view1.findViewById(R.id.apply_filter);
                final FlowLayout fl=view1.findViewById(R.id.fl);
                for(int i=0;i<10;i++)
                {
                    Chip chip= (Chip) getLayoutInflater().inflate(R.layout.chip_layout,null);
                    chip.setText(String.valueOf(i));
                    fl.addView(chip);


                }
                filter_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String ch=new String();
                        for(int i=0;i<fl.getChildCount();i++)
                        {
                            Chip c= (Chip) fl.getChildAt(i);
                            if(c.isChecked())
                            {
                                ch+=c.getText().toString()+" ";
                            }
                        }
                        Toast.makeText(view1.getContext(),ch,Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setContentView(view1);
                dialog.show();
            }
        });*/
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
       /* AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();*/
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
       // NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        imageView = (ImageView) findViewById(R.id.imageView);
        SP = getSharedPreferences(MY_PREF_NAME, MODE_PRIVATE);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SP.contains(USER_TOKEN))
                {
                    m=R.menu.logged_in_menu;
                }
                else {
                    m=R.menu.logged_out_menu;
                }
                popupMenu = new PopupMenu(MyBottomNavigation.this, view);
                popupMenu.inflate(m);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        int s = menuItem.getItemId();
                        if(m==R.menu.logged_in_menu) {
                            switch (s) {
                                case R.id.profile:
                                    Intent intent = new Intent(MyBottomNavigation.this, profile_activity.class);
                                    startActivity(intent);
                                    //Toast.makeText(getApplicationContext(), String.valueOf(s), Toast.LENGTH_LONG).show();
                                    break;
                                case R.id.logout:
                                    SharedPreferences.Editor editor = SP.edit();
                                    editor.clear();
                                    editor.apply();
                            }
                        }
                        if(m==R.menu.logged_out_menu) {
                            switch (s)
                            {
                                case R.id.login:
                                    Intent intent=new Intent(MyBottomNavigation.this, login.class);
                                    startActivity(intent);
                                    finish();
                            }
                        }
                        return true;
                    }
                });
                popupMenu.show();

            }
        });
    }
    @Override
    public void onFragmentInteraction(Uri uri) {
        return;
    }
}
