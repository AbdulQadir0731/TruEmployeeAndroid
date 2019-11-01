package com.truemployee.app.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.truemployee.app.Adaptors.MyProfileAdaptor;
import com.truemployee.app.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyEmployeesFragment extends Fragment {
    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public MyEmployeesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_employees, container, false);
        // Inflate the layout for this fragment


        recyclerView = view.findViewById(R.id.my_employees_list);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        LinearLayout[] myDataset = new LinearLayout[10];


        mAdapter = new MyProfileAdaptor(getActivity() , myDataset);
        recyclerView.setAdapter(mAdapter);
        return view;
    }



    public static MyEmployeesFragment newInstance() {
        MyEmployeesFragment fragment = new MyEmployeesFragment();
        return fragment;
    }


}
