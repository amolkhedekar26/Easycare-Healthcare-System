package com.example.easycare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.easycare.Authentication.login;
import com.example.easycare.Authentication.profile_activity;
import com.example.easycare.Fragments.ConsultDoctorFragment;
import com.example.easycare.Fragments.DoctorFragment;
import com.example.easycare.Fragments.HospitalFragment;
import com.example.easycare.Fragments.MedicineFragment;
import com.example.easycare.Fragments.MyHomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.example.easycare.Constants.Constant.MY_PREF_NAME;
import static com.example.easycare.Constants.Constant.USER_TOKEN;

public class home extends AppCompatActivity implements MyHomeFragment.OnFragmentInteractionListener, DoctorFragment.OnFragmentInteractionListener, HospitalFragment.OnFragmentInteractionListener, MedicineFragment.OnFragmentInteractionListener, ConsultDoctorFragment.OnFragmentInteractionListener {
    public TextView title;
    PopupMenu popupMenu;
    BottomNavigationView bottomNavigationView;
    ImageView imageView;
    SharedPreferences SP;
    int m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        title = (TextView) findViewById(R.id.textView);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_bar);
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
                popupMenu = new PopupMenu(home.this, view);
                popupMenu.inflate(m);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        int s = menuItem.getItemId();
                        if(m==R.menu.logged_in_menu) {
                            switch (s) {
                                case R.id.profile:
                                    Intent intent = new Intent(home.this, profile_activity.class);
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
                                    Intent intent=new Intent(home.this, login.class);
                                    startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
                popupMenu.show();

            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        loadFragment(new MyHomeFragment());


    }


    @Override
    public void onFragmentInteraction(Uri uri) {
        return;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            switch (menuItem.getItemId()) {
                case R.id.homeicon:
                    title.setText("Easycare");
                    fragment = new MyHomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.doctoricon:
                    title.setText("Doctor");
                    fragment = new DoctorFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.hospitalicon:
                    title.setText("Hospital");
                    fragment = new HospitalFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.chaticon:
                    title.setText("Consult");
                   fragment = new ConsultDoctorFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.medicineicon:
                    title.setText("Medicine");
                    fragment = new MedicineFragment();
                    loadFragment(fragment);
                    return true;

            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.substitute, fragment);
        final FragmentManager fm = getSupportFragmentManager();


        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void dosome(View view) {
        title.setText("AMOL");
        Fragment fragment = new MyHomeFragment();
        loadFragment(fragment);
    }

}
