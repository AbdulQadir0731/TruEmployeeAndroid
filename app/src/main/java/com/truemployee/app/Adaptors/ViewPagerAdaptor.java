package com.truemployee.app.Adaptors;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.truemployee.app.Fragments.MyEmployeesFragment;
import com.truemployee.app.Fragments.SubcribedEmployeesFragment;

public class ViewPagerAdaptor extends FragmentPagerAdapter {
    private Fragment[] childFragments;
    public ViewPagerAdaptor(FragmentManager fm) {
        super(fm);
        childFragments = new Fragment[] {
                new MyEmployeesFragment(), //0
                new SubcribedEmployeesFragment(), //1

        };



    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        return childFragments[position];

    }

    @Override
    public int getCount() {
        return childFragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0) {

            return "MY EMPLOYEES";
        }
        if(position == 1) {

            return "SUBSCRIBED EMPLOYEES";
        }



        return "";
    }

}