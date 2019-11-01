package com.truemployee.app.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.truemployee.app.Activities.AddEmployeeActivity;
import com.truemployee.app.Adaptors.ViewPagerAdaptor;
import com.truemployee.app.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    View view;
    ViewPager viewPager;
    TabLayout tabLayout;
    ViewPagerAdaptor viewPagerAdapter;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Button add_employee =  view.findViewById(R.id.add_employee);

        add_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , AddEmployeeActivity.class);
                startActivity(intent);
            }
        });

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPagerAdapter = new ViewPagerAdaptor(getChildFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);


        return view;
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

}
