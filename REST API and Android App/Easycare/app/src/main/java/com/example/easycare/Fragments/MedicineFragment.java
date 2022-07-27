package com.example.easycare.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.easycare.MedicineList;
import com.example.easycare.R;
import com.example.easycare.adaptersclasses.DiseaseAdapter;
import com.example.easycare.adaptersclasses.MedicineAdapter;
import com.example.easycare.api.easycareapi;
import com.example.easycare.dataclasses.Medicines;
import com.google.android.material.bottomsheet.BottomSheetDialog;

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
 * {@link MedicineFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MedicineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MedicineFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recyclerView,recyclerView2;
    MedicineAdapter adapter;
    DiseaseAdapter adapter2;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Medicines> medicinesArrayList;
    ArrayList<String> d;
    TextView textView,title;
    ProgressDialog progressDialog;
    BottomSheetDialog dialog;
    Context context;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MedicineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MedicineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MedicineFragment newInstance(String param1, String param2) {
        MedicineFragment fragment = new MedicineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        layoutManager = new GridLayoutManager(context,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        textView = view.findViewById(R.id.textView9);
        title=getActivity().findViewById(R.id.textView);
        title.setText("Medicines");
        textView.setVisibility(View.INVISIBLE);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
      // loadmedicines();
         getDiseases();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_medicine, container, false);
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

    public void loadmedicines(String name) {

        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_NAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        easycareapi api = retrofit.create(easycareapi.class);

        Call<List<Medicines>> call = api.getMedicines(name);
        call.enqueue(new Callback<List<Medicines>>() {
            @Override
            public void onResponse(Call<List<Medicines>> call, Response<List<Medicines>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    return;
                }
                List<Medicines> medicines = response.body();
                medicinesArrayList = new ArrayList<Medicines>();
                if (medicines.size() == 0) {

                }

                for (Medicines med : medicines) {
                    Medicines item = new Medicines();
                    item.setMed_name(med.getMed_name());
                    item.setMed_disaese(med.getMed_disaese());
                    medicinesArrayList.add(item);


                }
                adapter = new MedicineAdapter(medicinesArrayList);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();
                /*adapter.setOnItemClickListener(new MedicineAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemOnClick(int position) {
                        Toast.makeText(getContext(),String.valueOf(position),Toast.LENGTH_LONG).show();
                        View view1=getLayoutInflater().inflate(R.layout.bottom_sheet,null);
                        context=view1.getContext();
                        dialog = new BottomSheetDialog(context,R.style.BottomSheetDialog);
                        dialog.setContentView(view1);
                        TextView t1=(TextView)view1.findViewById(R.id.textView10);
                        t1.setText("amol");
                        dialog.show();
                    }
                });*/

            }

            @Override
            public void onFailure(Call<List<Medicines>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(),"Internet connection is unavailable\n"+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
    public void getDiseases()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_NAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        easycareapi api = retrofit.create(easycareapi.class);
        Call<List<String>> call=api.getDiseaseNames();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                d=new ArrayList<String>();
                List<String> li=response.body();
                for(String s:li)
                {
                    d.add(s);
                }
                adapter2=new DiseaseAdapter(d);
                recyclerView.setAdapter(adapter2);
                adapter2.setOnItemClickListener(new DiseaseAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemOnClick(int position) {
                        String s=d.get(position);
                        Intent intent=new Intent(getContext(), MedicineList.class);
                        intent.putExtra("dis_name",s);
                        //loadmedicines(s);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
    }
}
