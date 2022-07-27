package com.example.easycare.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easycare.R;
import com.example.easycare.adaptersclasses.DoctorAdapter;
import com.example.easycare.api.easycareapi;
import com.example.easycare.dataclasses.Doctors;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nex3z.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.easycare.Constants.Constant.BASE_NAME;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DoctorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DoctorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recyclerView;
    DoctorAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Doctors> doctorsArrayList;
    FlowLayout filter_display;
    TextView textView,title;
    ProgressDialog progressDialog;
    FloatingActionButton fb;
    BottomSheetDialog dialog;
    ArrayList<String> mylist;
    Context context;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DoctorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoctorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DoctorFragment newInstance(String param1, String param2) {
        DoctorFragment fragment = new DoctorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        textView = view.findViewById(R.id.textView9);
        title=getActivity().findViewById(R.id.textView);
        title.setText("Doctors");
        filter_display = view.findViewById(R.id.filter_display);
        textView.setVisibility(View.INVISIBLE);
        fb = view.findViewById(R.id.floatingActionButton);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        mylist = new ArrayList<>();
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View view1 = getLayoutInflater().inflate(R.layout.filter_sheet, null);
                dialog = new BottomSheetDialog(view1.getContext(), R.style.BottomSheetDialog);
                ArrayList<String> locations = new ArrayList<>();
                locations.add("ratnagiri");
                locations.add("dapoli");
                locations.add("chiplun");
                final Button filter_button = (Button) view1.findViewById(R.id.apply_filter);
                final FlowLayout fl = view1.findViewById(R.id.fl);
                for (String s : locations) {
                    Chip chip = (Chip) getLayoutInflater().inflate(R.layout.chip_layout, null);
                    chip.setText(s);
                    fl.addView(chip);
                }

                filter_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String ch = new String();
                        ArrayList<String> loc = new ArrayList<>();
                        for (int i = 0; i < fl.getChildCount(); i++) {
                            Chip c = (Chip) fl.getChildAt(i);
                            if (c.isChecked()) {
                                //loc.add(c.getText().toString());
                                if(mylist.contains(c.getText().toString()))
                                {

                                }
                                else{
                                    mylist.add(c.getText().toString());
                                }

                                ch += c.getText().toString();
                                ch += ",";
                                final Chip chip1 = (Chip) getLayoutInflater().inflate(R.layout.filtered_chip_layout, null);
                                chip1.setText(c.getText().toString());
                                chip1.setCheckable(false);

                                for (int j = 0; j < filter_display.getChildCount(); j++) {
                                    Chip cw = (Chip) filter_display.getChildAt(j);
                                    loc.add(cw.getText().toString());
                                }
                                chip1.setOnCloseIconClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        mylist.remove(chip1.getText().toString());
                                        filter_display.removeView(chip1);
                                       // Toast.makeText(view1.getContext(),mylist.toString(),Toast.LENGTH_SHORT).show();
                                        loaddoctors(mylist);


                                    }
                                });
                                if (loc.contains(chip1.getText().toString())) {
                                    //filter_display.addView(chip1);
                                }
                                else{
                                    filter_display.addView(chip1);
                                }



                            }
                        }
                        /*for(String i:loc)
                        {
                            Chip chip1=(Chip)getLayoutInflater().inflate(R.layout.chip_layout,null);
                            chip1.setText(i);
                            filter_display.addView(chip1);
                        }*/
                        /*textView.setVisibility(View.VISIBLE);
                        textView.setText(ch);*/
                        if (ch.equals("")) {
                            Toast.makeText(getContext(), "Please choose at least one filter", Toast.LENGTH_SHORT).show();
                        } else {
                            //ch = ch.substring(0, ch.length() - 1);
                            //Toast.makeText(view1.getContext(),loc.toString(),Toast.LENGTH_SHORT).show();
                            loaddoctors(mylist);
                            dialog.dismiss();
                        }

                        //Toast.makeText(view1.getContext(),ch,Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setContentView(view1);
                dialog.show();
            }
        });
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
        return inflater.inflate(R.layout.fragment_doctor, container, false);
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

        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_NAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        easycareapi api = retrofit.create(easycareapi.class);

        Call<List<Doctors>> call = api.getDoctors(null, null);
        call.enqueue(new Callback<List<Doctors>>() {
            @Override
            public void onResponse(Call<List<Doctors>> call, Response<List<Doctors>> response) {
                if (!response.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_LONG).show();

                    return;
                }
                final List<Doctors> doctors = response.body();
                doctorsArrayList = new ArrayList<Doctors>();
                if (doctors.size() == 0) {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("jkcsnk");
                }

                for (Doctors doc : doctors) {
                    Doctors item = new Doctors();
                    item.setDoc_name(doc.getDoc_name());
                    item.setDoc_address(doc.getDoc_address());
                    item.setDoc_degree(doc.getDoc_degree());
                    item.setDoc_location(doc.getDoc_location());
                    doctorsArrayList.add(item);


                }
                adapter = new DoctorAdapter(doctorsArrayList);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();


                adapter.setOnItemClickListener(new DoctorAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemOnClick(int position) {
                        Doctors d = doctors.get(position);
                        //Toast.makeText(getContext(),String.valueOf(position),Toast.LENGTH_LONG).show();
                        View view1 = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
                        context = view1.getContext();
                        dialog = new BottomSheetDialog(context, R.style.BottomSheetDialog);
                        dialog.setContentView(view1);
                        TextView doc_name = (TextView) view1.findViewById(R.id.textView19);
                        TextView doc_address = (TextView) view1.findViewById(R.id.textView18);
                        TextView doc_speciality = (TextView) view1.findViewById(R.id.textView17);
                        TextView doc_contact = (TextView) view1.findViewById(R.id.textView16);
                        TextView doc_experience = (TextView) view1.findViewById(R.id.textView15);
                        doc_name.setText(d.getDoc_name());
                        doc_address.setText(d.getDoc_address());
                        doc_speciality.setText(d.getDoc_speciality());
                        doc_contact.setText(String.valueOf(d.getDoc_mobile()));
                        doc_experience.setText("Exp: " + d.getDoc_experience());
                        Toast.makeText(getContext(), d.getDoc_name(), Toast.LENGTH_SHORT).show();
                        dialog.show();

                    }
                });

            }

            @Override
            public void onFailure(Call<List<Doctors>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Internet connection is unavailable\n" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void loaddoctors(String location) {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_NAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        easycareapi api = retrofit.create(easycareapi.class);

        Call<List<Doctors>> call = api.getDoctors(location, null);
        call.enqueue(new Callback<List<Doctors>>() {
            @Override
            public void onResponse(Call<List<Doctors>> call, Response<List<Doctors>> response) {
                if (!response.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_LONG).show();

                    return;
                }
                final List<Doctors> doctors = response.body();
                doctorsArrayList = new ArrayList<Doctors>();
                if (doctors.size() == 0) {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("jkcsnk");
                }

                for (Doctors doc : doctors) {
                    Doctors item = new Doctors();
                    item.setDoc_name(doc.getDoc_name());
                    item.setDoc_address(doc.getDoc_address());
                    item.setDoc_location(doc.getDoc_location());
                    item.setDoc_degree(doc.getDoc_degree());
                    doctorsArrayList.add(item);


                }
                adapter = new DoctorAdapter(doctorsArrayList);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();


                adapter.setOnItemClickListener(new DoctorAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemOnClick(int position) {
                        Doctors d = doctors.get(position);
                        //Toast.makeText(getContext(),String.valueOf(position),Toast.LENGTH_LONG).show();
                        View view1 = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
                        context = view1.getContext();
                        dialog = new BottomSheetDialog(context, R.style.BottomSheetDialog);
                        dialog.setContentView(view1);
                        TextView doc_name = (TextView) view1.findViewById(R.id.textView19);
                        TextView doc_address = (TextView) view1.findViewById(R.id.textView18);
                        TextView doc_speciality = (TextView) view1.findViewById(R.id.textView17);
                        TextView doc_contact = (TextView) view1.findViewById(R.id.textView16);
                        TextView doc_experience = (TextView) view1.findViewById(R.id.textView15);
                        doc_name.setText(d.getDoc_name());
                        doc_address.setText(d.getDoc_address());
                        doc_speciality.setText(d.getDoc_speciality());
                        doc_contact.setText(String.valueOf(d.getDoc_mobile()));
                        doc_experience.setText("Exp: " + d.getDoc_experience());
                        dialog.show();

                    }
                });

            }

            @Override
            public void onFailure(Call<List<Doctors>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Internet connection is unavailable\n" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void loaddoctors(ArrayList<String> mylist) {
        String location = "";
        if(!mylist.isEmpty())
        {
            for (String s : mylist) {
                location += s;
                location += ",";
            }
            location = location.substring(0, location.length() - 1);
        }

        else{
            location=null;
        }
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_NAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        easycareapi api = retrofit.create(easycareapi.class);

        Call<List<Doctors>> call = api.getDoctors(location, null);
        call.enqueue(new Callback<List<Doctors>>() {
            @Override
            public void onResponse(Call<List<Doctors>> call, Response<List<Doctors>> response) {
                if (!response.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_LONG).show();

                    return;
                }
                final List<Doctors> doctors = response.body();
                doctorsArrayList = new ArrayList<Doctors>();
                if (doctors.size() == 0) {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("jkcsnk");
                }

                for (Doctors doc : doctors) {
                    Doctors item = new Doctors();
                    item.setDoc_name(doc.getDoc_name());
                    item.setDoc_address(doc.getDoc_address());
                    item.setDoc_degree(doc.getDoc_degree());
                    item.setDoc_location(doc.getDoc_location());
                    doctorsArrayList.add(item);


                }
                adapter = new DoctorAdapter(doctorsArrayList);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();


                adapter.setOnItemClickListener(new DoctorAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemOnClick(int position) {
                        Doctors d = doctors.get(position);
                        //Toast.makeText(getContext(),String.valueOf(position),Toast.LENGTH_LONG).show();
                        View view1 = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
                        context = view1.getContext();
                        dialog = new BottomSheetDialog(context, R.style.BottomSheetDialog);
                        dialog.setContentView(view1);
                        TextView doc_name = (TextView) view1.findViewById(R.id.textView19);
                        TextView doc_address = (TextView) view1.findViewById(R.id.textView18);
                        TextView doc_speciality = (TextView) view1.findViewById(R.id.textView17);
                        TextView doc_contact = (TextView) view1.findViewById(R.id.textView16);
                        TextView doc_experience = (TextView) view1.findViewById(R.id.textView15);
                        doc_name.setText(d.getDoc_name());
                        doc_address.setText(d.getDoc_address());
                        doc_speciality.setText(d.getDoc_speciality());
                        doc_contact.setText(String.valueOf(d.getDoc_mobile()));
                        doc_experience.setText("Exp: " + d.getDoc_experience());
                        dialog.show();

                    }
                });

            }

            @Override
            public void onFailure(Call<List<Doctors>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Internet connection is unavailable\n" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
