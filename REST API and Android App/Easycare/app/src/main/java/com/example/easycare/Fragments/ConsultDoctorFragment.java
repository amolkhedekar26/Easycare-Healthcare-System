package com.example.easycare.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easycare.NewMessageWindow;
import com.example.easycare.R;
import com.example.easycare.adaptersclasses.ConsultDoctorAdapter;
import com.example.easycare.adaptersclasses.UserAdapter;
import com.example.easycare.api.easycareapi;
import com.example.easycare.dataclasses.ConsultDoctors;
import com.example.easycare.dataclasses.User;
import com.example.easycare.Authentication.login;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.easycare.Constants.Constant.BASE_NAME;
import static com.example.easycare.Constants.Constant.MY_PREF_NAME;
import static com.example.easycare.Constants.Constant.USER_TOKEN;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConsultDoctorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConsultDoctorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConsultDoctorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recyclerView,recyclerView2;
    ConsultDoctorAdapter adapter;
    UserAdapter adapter2;
    SharedPreferences SP;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.LayoutManager layoutManager2;
    ArrayList<ConsultDoctors> doctorsArrayList;
    ArrayList<User> userArrayList;
    TextView textView,title;
    ImageView info_icon;
    ProgressDialog progressDialog;
    BottomSheetDialog dialog ;
    Context context;
    String user;
    FloatingActionButton fb;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ConsultDoctorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConsultDoctorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConsultDoctorFragment newInstance(String param1, String param2) {
        ConsultDoctorFragment fragment = new ConsultDoctorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        /*recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);*/

        layoutManager2=new LinearLayoutManager(getContext());
        recyclerView2=(RecyclerView)view.findViewById(R.id.recyclerviewpatient);
       // recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setLayoutManager(layoutManager2);
        SP=getActivity().getSharedPreferences(MY_PREF_NAME,Context.MODE_PRIVATE);
        textView=view.findViewById(R.id.textView9);
        title=getActivity().findViewById(R.id.textView);
        title.setText("Cosunlt Doctors");
        textView.setVisibility(View.INVISIBLE);
        info_icon=(ImageView)view.findViewById(R.id.info_icon);

        progressDialog=new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        fb=view.findViewById(R.id.newchatwindow);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), NewMessageWindow.class);
                startActivity(intent);
            }
        });
        displayconsulted();
        loaddoctors();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_consult_doctor, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public void loaddoctors() {

        if (SP.contains(USER_TOKEN)) {
            user = SP.getString(USER_TOKEN, null);
        }
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_NAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        easycareapi api = retrofit.create(easycareapi.class);

        Call<List<ConsultDoctors>> call = api.getConsultDoctors("Token " + user);

        call.enqueue(new Callback<List<ConsultDoctors>>() {
            @Override
            public void onResponse(Call<List<ConsultDoctors>> call, final Response<List<ConsultDoctors>> response) {
                if (!response.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "You have to login first", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(getActivity(), login.class);
                    startActivity(intent);
                    return;
                }
                final List<ConsultDoctors> doctors = response.body();
                doctorsArrayList = new ArrayList<ConsultDoctors>();
                if(doctors.size()==0)
                {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("Sorry No Doctors Today");
                }

                for (ConsultDoctors doc : doctors) {
                    ConsultDoctors item = new ConsultDoctors();
                    item.setFirst_name(doc.getFirst_name());
                    item.setLast_name(doc.getLast_name());
                    item.setExperience(doc.getExperience());
                    item.setSpeciality(doc.getSpeciality());
                    doctorsArrayList.add(item);


                }
                adapter = new ConsultDoctorAdapter(doctorsArrayList);
                //recyclerView.setAdapter(adapter);
                progressDialog.dismiss();
                adapter.setOnItemClickListener(new ConsultDoctorAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemOnClick(int position) {
                        Toast.makeText(getContext(),String.valueOf(position),Toast.LENGTH_LONG).show();
                       /* View view1=getLayoutInflater().inflate(R.layout.bottom_sheet,null);
                        context=view1.getContext();
                        dialog = new BottomSheetDialog(context,R.style.BottomSheetDialog);
                        dialog.setContentView(view1);
                        TextView t1=(TextView)view1.findViewById(R.id.textView10);
                        t1.setText("amol");
                        dialog.show();*/
                        Intent intent=new Intent(getActivity(), Chat.class);
                        ConsultDoctors doct=doctors.get(position);
                        intent.putExtra("tag",doct.getSpeciality());
                        intent.putExtra("doc_name",doct.getUser_name());
                        startActivity(intent);

                    }
                });


            }

            @Override
            public void onFailure(Call<List<ConsultDoctors>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void displayconsulted()
    {
        if(SP.contains("is_doctor"))
        {
            boolean t=SP.getBoolean("is_doctor",false);
            if(t==true)
            {
                if (SP.contains(USER_TOKEN)) {
                    user = SP.getString(USER_TOKEN, null);
                }
                progressDialog.show();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_NAME)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                easycareapi api = retrofit.create(easycareapi.class);
                Call<List<User>> call=api.getaskedPatients("Token " + user);
                call.enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        final List<User> users = response.body();

                        userArrayList = new ArrayList<User>();
                        if(users.size()==0)
                        {
                            recyclerView2.setVisibility(View.INVISIBLE);
                            info_icon.setBackgroundResource(R.drawable.info_icon);
                            textView.setVisibility(View.VISIBLE);
                            textView.setText("You have no any questions yet.");

                        }
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("Consult with our specialists");
                        for (User user:users) {
                            User item = new User();
                            item.setFirst_name(user.getFirst_name());
                            item.setLast_name(user.getLast_name());
                            //item.setExperience(doc.getExperience());
                            //item.setSpeciality(doc.getSpeciality());
                            userArrayList.add(item);


                        }

                        adapter2 = new UserAdapter(userArrayList);
                        recyclerView2.setAdapter(adapter2);
                        progressDialog.dismiss();
                        adapter2.setOnItemClickListener(new UserAdapter.OnItemClickListener() {
                            @Override
                            public void OnItemOnClick(int position) {
                                User u=users.get(position);
                                Toast.makeText(getContext(),String.valueOf(position)+u.getUsername(),Toast.LENGTH_LONG).show();
                       /* View view1=getLayoutInflater().inflate(R.layout.bottom_sheet,null);
                        context=view1.getContext();
                        dialog = new BottomSheetDialog(context,R.style.BottomSheetDialog);
                        dialog.setContentView(view1);
                        TextView t1=(TextView)view1.findViewById(R.id.textView10);
                        t1.setText("amol");
                        dialog.show();*/
                                Intent intent=new Intent(getActivity(),Chat.class);
                                User user=users.get(position);
                                intent.putExtra("user_name",u.getUsername());
                                // intent.putExtra("tag",doct.getSpeciality());
                                //intent.putExtra("doc_name",doct.getUser_name());
                                startActivity(intent);
                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {

                    }
                });
            }
            else
            {
                if (SP.contains(USER_TOKEN)) {
                    user = SP.getString(USER_TOKEN, null);
                }
                progressDialog.show();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_NAME)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                easycareapi api = retrofit.create(easycareapi.class);

                Call<List<ConsultDoctors>> call = api.getConsultedDoctors("Token " + user);

                call.enqueue(new Callback<List<ConsultDoctors>>() {
                    @Override
                    public void onResponse(Call<List<ConsultDoctors>> call, final Response<List<ConsultDoctors>> response) {
                        if (!response.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), response.code(), Toast.LENGTH_LONG).show();

                            return;
                        }
                        final List<ConsultDoctors> doctors = response.body();

                        doctorsArrayList = new ArrayList<ConsultDoctors>();
                        if(doctors.size()==0)
                        {
                            recyclerView2.setVisibility(View.INVISIBLE);
                            info_icon.setBackgroundResource(R.drawable.info_icon);
                            textView.setVisibility(View.VISIBLE);
                            textView.setText("You have no any consultations yet.");

                        }
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("Consult with our specialists");
                        for (ConsultDoctors doc : doctors) {
                            ConsultDoctors item = new ConsultDoctors();
                            item.setFirst_name(doc.getFirst_name());
                            item.setLast_name(doc.getLast_name());
                            item.setExperience(doc.getExperience());
                            item.setSpeciality(doc.getSpeciality());
                            doctorsArrayList.add(item);


                        }

                        adapter = new ConsultDoctorAdapter(doctorsArrayList);
                        recyclerView2.setAdapter(adapter);
                        progressDialog.dismiss();
                        adapter.setOnItemClickListener(new ConsultDoctorAdapter.OnItemClickListener() {
                            @Override
                            public void OnItemOnClick(int position) {
                                Toast.makeText(getContext(),String.valueOf(position),Toast.LENGTH_LONG).show();
                       /* View view1=getLayoutInflater().inflate(R.layout.bottom_sheet,null);
                        context=view1.getContext();
                        dialog = new BottomSheetDialog(context,R.style.BottomSheetDialog);
                        dialog.setContentView(view1);
                        TextView t1=(TextView)view1.findViewById(R.id.textView10);
                        t1.setText("amol");
                        dialog.show();*/
                                Intent intent=new Intent(getActivity(),Chat.class);
                                ConsultDoctors doct=doctors.get(position);
                                intent.putExtra("tag",doct.getSpeciality());
                                intent.putExtra("doc_name",doct.getUser_name());
                                startActivity(intent);

                            }
                        });


                    }

                    @Override
                    public void onFailure(Call<List<ConsultDoctors>> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(),"Internet connection is unavailable\n"+t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        /*if (SP.contains(USER_TOKEN)) {
            user = SP.getString(USER_TOKEN, null);
        }
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.50:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        easycareapi api = retrofit.create(easycareapi.class);

        Call<List<ConsultDoctors>> call = api.getConsultedDoctors("Token " + user);

        call.enqueue(new Callback<List<ConsultDoctors>>() {
            @Override
            public void onResponse(Call<List<ConsultDoctors>> call, final Response<List<ConsultDoctors>> response) {
                if (!response.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_LONG).show();

                    return;
                }
                final List<ConsultDoctors> doctors = response.body();

                doctorsArrayList = new ArrayList<ConsultDoctors>();
                if(doctors.size()==0)
                {
                    recyclerView2.setVisibility(View.INVISIBLE);
                    info_icon.setBackgroundResource(R.drawable.info_icon);
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("You have no any consultations yet.");

                }
                textView.setVisibility(View.VISIBLE);
                textView.setText("Consult with our specialists");
                for (ConsultDoctors doc : doctors) {
                    ConsultDoctors item = new ConsultDoctors();
                    item.setFirst_name(doc.getFirst_name());
                    item.setLast_name(doc.getLast_name());
                    item.setExperience(doc.getExperience());
                    item.setSpeciality(doc.getSpeciality());
                    doctorsArrayList.add(item);


                }

                adapter = new ConsultDoctorAdapter(doctorsArrayList);
                recyclerView2.setAdapter(adapter);
                progressDialog.dismiss();
                adapter.setOnItemClickListener(new ConsultDoctorAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemOnClick(int position) {
                        Toast.makeText(getContext(),String.valueOf(position),Toast.LENGTH_LONG).show();
                       *//* View view1=getLayoutInflater().inflate(R.layout.bottom_sheet,null);
                        context=view1.getContext();
                        dialog = new BottomSheetDialog(context,R.style.BottomSheetDialog);
                        dialog.setContentView(view1);
                        TextView t1=(TextView)view1.findViewById(R.id.textView10);
                        t1.setText("amol");
                        dialog.show();*//*
                        Intent intent=new Intent(getActivity(),Chat.class);
                        ConsultDoctors doct=doctors.get(position);
                        intent.putExtra("tag",doct.getSpeciality());
                        intent.putExtra("doc_name",doct.getUser_name());
                        startActivity(intent);

                    }
                });


            }

            @Override
            public void onFailure(Call<List<ConsultDoctors>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/

    }
}
