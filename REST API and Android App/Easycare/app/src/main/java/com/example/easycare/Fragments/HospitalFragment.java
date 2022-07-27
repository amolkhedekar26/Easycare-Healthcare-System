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
import com.example.easycare.adaptersclasses.HospitalAdapter;
import com.example.easycare.api.easycareapi;
import com.example.easycare.dataclasses.Hospitals;
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
 * {@link HospitalFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HospitalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HospitalFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recyclerView;
    HospitalAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Hospitals> hospitalsArrayList;
    FlowLayout filter_display;
    TextView textView,title;
    FloatingActionButton fb;
    ArrayList<String> mylist;
    ProgressDialog progressDialog;
    BottomSheetDialog dialog ;
    Context context;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HospitalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HospitalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HospitalFragment newInstance(String param1, String param2) {
        HospitalFragment fragment = new HospitalFragment();
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
        textView=view.findViewById(R.id.textView9);
        title=getActivity().findViewById(R.id.textView);
        title.setText("Hospitals");
        filter_display = view.findViewById(R.id.filter_display);
        textView.setVisibility(View.INVISIBLE);
        fb = view.findViewById(R.id.floatingActionButton);
        progressDialog=new ProgressDialog(getContext());
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
                                        loadhospitals(mylist);


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
                            loadhospitals(mylist);
                            dialog.dismiss();
                        }

                        //Toast.makeText(view1.getContext(),ch,Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setContentView(view1);
                dialog.show();
            }
        });
        loadhospitals();
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
        return inflater.inflate(R.layout.fragment_hospital, container, false);
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
    public void loadhospitals() {

        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_NAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        easycareapi api = retrofit.create(easycareapi.class);

        Call<List<Hospitals>> call = api.getHospitals(null,null);
        call.enqueue(new Callback<List<Hospitals>>() {
            @Override
            public void onResponse(Call<List<Hospitals>> call, Response<List<Hospitals>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    return;
                }
                final List<Hospitals> hospitals = response.body();
                hospitalsArrayList = new ArrayList<Hospitals>();
                if(hospitals.size()==0)
                {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("jkcsnk");
                }

                for (Hospitals hosp : hospitals) {
                    Hospitals item = new Hospitals();
                    item.setH_name(hosp.getH_name());
                    item.setH_location(hosp.getH_location());
                    item.setH_address(hosp.getH_address());
                    hospitalsArrayList.add(item);


                }
                adapter = new HospitalAdapter(hospitalsArrayList);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();
                adapter.setOnItemClickListener(new HospitalAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemOnClick(int position) {
                        Hospitals h=hospitals.get(position);
                      //  Toast.makeText(getContext(),String.valueOf(position)+h.getH_name(),Toast.LENGTH_LONG).show();
                        View view1=getLayoutInflater().inflate(R.layout.bottom_sheet,null);
                        context=view1.getContext();
                        dialog = new BottomSheetDialog(context,R.style.BottomSheetDialog);
                        dialog.setContentView(view1);
                        TextView hosp_name=(TextView)view1.findViewById(R.id.textView19);
                        TextView hosp_address=(TextView)view1.findViewById(R.id.textView18);
                        TextView hosp_speciality=(TextView)view1.findViewById(R.id.textView17);
                        TextView hosp_contact=(TextView)view1.findViewById(R.id.textView16);
                        TextView hosp_location=(TextView)view1.findViewById(R.id.textView15);
                        hosp_name.setText(h.getH_name());
                        hosp_address.setText(h.getH_address());
                        hosp_speciality.setText(h.getH_speciality());
                        hosp_contact.setText(String.valueOf(h.getH_contact()));
                        hosp_location.setText(h.getH_location());
                        dialog.show();
                    }
                });

            }

            @Override
            public void onFailure(Call<List<Hospitals>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(),"Internet connection is unavailable\n"+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
    public void loadhospitals(ArrayList<String> mylist) {
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

        Call<List<Hospitals>> call = api.getHospitals(location,null);
        call.enqueue(new Callback<List<Hospitals>>() {
            @Override
            public void onResponse(Call<List<Hospitals>> call, Response<List<Hospitals>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    return;
                }
                final List<Hospitals> hospitals = response.body();
                hospitalsArrayList = new ArrayList<Hospitals>();
                if(hospitals.size()==0)
                {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("jkcsnk");
                }

                for (Hospitals hosp : hospitals) {
                    Hospitals item = new Hospitals();
                    item.setH_name(hosp.getH_name());
                    item.setH_location(hosp.getH_location());
                    item.setH_address(hosp.getH_address());
                    hospitalsArrayList.add(item);


                }
                adapter = new HospitalAdapter(hospitalsArrayList);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();
                adapter.setOnItemClickListener(new HospitalAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemOnClick(int position) {
                        Hospitals h=hospitals.get(position);
                        //  Toast.makeText(getContext(),String.valueOf(position)+h.getH_name(),Toast.LENGTH_LONG).show();
                        View view1=getLayoutInflater().inflate(R.layout.bottom_sheet,null);
                        context=view1.getContext();
                        dialog = new BottomSheetDialog(context,R.style.BottomSheetDialog);
                        dialog.setContentView(view1);
                        TextView hosp_name=(TextView)view1.findViewById(R.id.textView19);
                        TextView hosp_address=(TextView)view1.findViewById(R.id.textView18);
                        TextView hosp_speciality=(TextView)view1.findViewById(R.id.textView17);
                        TextView hosp_contact=(TextView)view1.findViewById(R.id.textView16);
                        TextView hosp_location=(TextView)view1.findViewById(R.id.textView15);
                        hosp_name.setText(h.getH_name());
                        hosp_address.setText(h.getH_address());
                        hosp_speciality.setText(h.getH_speciality());
                        hosp_contact.setText(String.valueOf(h.getH_contact()));
                        hosp_location.setText(h.getH_location());
                        dialog.show();
                    }
                });

            }

            @Override
            public void onFailure(Call<List<Hospitals>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(),"Internet connection is unavailable\n"+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
}
