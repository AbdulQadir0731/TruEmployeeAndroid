package com.truemployee.app.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.truemployee.app.Adaptors.MyAdaptor;

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
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        LinearLayout[] myDataset = new LinearLayout[10];


        mAdapter = new MyAdaptor(getActivity() , myDataset);
        recyclerView.setAdapter(mAdapter);





        return  view;
    }

}
