package com.truemployee.app.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.truemployee.app.Activities.MainActivity;
import com.truemployee.app.Adaptors.Home_Adaptor;

import com.truemployee.app.Models.EmployeeModel;
import com.truemployee.app.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Explore extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public Explore() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.explore_recycleview);



        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        final EmployeeModel[] propertiesdetails = gson.fromJson(MainActivity.DATA_EMP.toString(), EmployeeModel[].class);
        recyclerView.setAdapter(new Home_Adaptor(getActivity(), propertiesdetails));







        return  view;
    }

}
