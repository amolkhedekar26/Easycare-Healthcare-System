package com.example.easycare.Extra;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.easycare.R;
import com.example.easycare.adaptersclasses.DoctorAdapter;
import com.example.easycare.api.easycareapi;
import com.example.easycare.dataclasses.Doctors;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DocotrFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DocotrFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DocotrFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recyclerView;
    DoctorAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Doctors> doctorsArrayList;
    TextView textView;

    BottomSheetDialog dialog ;
    Context context;
    public static ProgressDialog progressBar;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DocotrFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DocotrFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DocotrFragment newInstance(String param1, String param2) {
        DocotrFragment fragment = new DocotrFragment();
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
        textView=view.findViewById(R.id.textView9);
        textView.setVisibility(View.INVISIBLE);
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
        View view = inflater.inflate(R.layout.fragment_docotr, container, false);

        return view;
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

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.50:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        easycareapi api = retrofit.create(easycareapi.class);

        Call<List<Doctors>> call = api.getDoctors(null,null);
        call.enqueue(new Callback<List<Doctors>>() {
            @Override
            public void onResponse(Call<List<Doctors>> call, Response<List<Doctors>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_LONG).show();

                    return;
                }
                List<Doctors> doctors = response.body();
                doctorsArrayList = new ArrayList<Doctors>();
                if(doctors.size()==0)
                {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("jkcsnk");
                }

                for (Doctors doc : doctors) {
                    Doctors item = new Doctors();
                    item.setDoc_name(doc.getDoc_name());
                    item.setDoc_address(doc.getDoc_address());
                    doctorsArrayList.add(item);


                }
                adapter = new DoctorAdapter(doctorsArrayList);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new DoctorAdapter.OnItemClickListener() {
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
                });

            }

            @Override
            public void onFailure(Call<List<Doctors>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
