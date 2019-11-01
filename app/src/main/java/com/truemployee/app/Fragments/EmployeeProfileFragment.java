package com.truemployee.app.Fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.truemployee.app.R;

import java.lang.reflect.Array;


/**
 * A simple {@link Fragment} subclass.
 */
public class EmployeeProfileFragment extends Fragment {


    public EmployeeProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_employee_profile, container, false);





        return view;
    }




}
