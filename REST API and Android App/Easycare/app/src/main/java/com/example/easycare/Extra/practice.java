package com.example.easycare.Extra;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.easycare.Fragments.DoctorFragment;
import com.example.easycare.Fragments.MyHomeFragment;
import com.example.easycare.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class practice extends AppCompatActivity implements MyHomeFragment.OnFragmentInteractionListener, DoctorFragment.OnFragmentInteractionListener {
    public TextView title;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        title=(TextView) findViewById(R.id.textView);
        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        loadFragment(new MyHomeFragment());


    }
    @Override
    public void onFragmentInteraction(Uri uri)
    {
        return;
    }
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            switch (menuItem.getItemId())
            {
                case R.id.homeicon:
                    title.setText("Home");
                    fragment=new MyHomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.doctoricon:
                    title.setText("Doctor");
                    fragment=new DoctorFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.hospitalicon:
                    title.setText("Hospital");
                    fragment=new MyHomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.chaticon:
                    title.setText("Consult");
                    fragment=new MyHomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.medicineicon:
                    title.setText("Medicine");
                    fragment=new MyHomeFragment();
                    loadFragment(fragment);
                    return true;

            }
            return false;
        }
    };
    private void loadFragment(Fragment fragment)
    {
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.substitute,fragment);
        transaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void dosome(View view)
    {
        title.setText("AMOL");
        Fragment fragment=new MyHomeFragment();
        loadFragment(fragment);
    }
}
