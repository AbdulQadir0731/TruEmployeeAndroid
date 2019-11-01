package com.truemployee.app.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.truemployee.app.Activities.SubcriptionActivity;
import com.truemployee.app.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Wallet extends Fragment {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public static  int CREDITS = 10;
    Button subcribenow;

    public Wallet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        TextView credits = view.findViewById(R.id.credits);
        subcribenow = view.findViewById(R.id.subscribenow);
        subcribenow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SubcriptionActivity.class);
                startActivity(intent);
            }
        });
        credits.setText(String.valueOf(CREDITS));
        return view;
    }

}
