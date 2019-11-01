package com.truemployee.app.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.truemployee.app.Fragments.Explore;
import com.truemployee.app.Fragments.MoreFragment;
import com.truemployee.app.Fragments.ProfileFragment;
import com.truemployee.app.R;
import com.truemployee.app.Fragments.Wallet;

public class home extends AppCompatActivity {


    public static final String MyPREFERENCES = "truEmployeePref" ;
    public static boolean firstloogedin = false;
    SharedPreferences sharedpreferences;

    private TabLayout tabLayout;
    Fragment fragment = null;
    MenuItem item ,itemfilter , itemeditprofile;
    FragmentTransaction[] ft = {null};





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        tabLayout = (TabLayout)findViewById(R.id.tablayout);

        fragment = new Explore();
      ft = new FragmentTransaction[]{getSupportFragmentManager().beginTransaction()};
        ft[0].replace(R.id.output, fragment);
        ft[0].setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft[0].commit();
        getSupportActionBar().setTitle("EXPLORE");




        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        getSupportActionBar().setTitle("EXPLORE");
                        item.setVisible(true);
                        itemfilter.setVisible(true);
                        itemeditprofile.setVisible(false);



                        fragment = new Explore();
                        break;

                    case 1:
                        getSupportActionBar().setTitle("WALLET");
                        item.setVisible(false);
                        itemfilter.setVisible(false);
                        itemeditprofile.setVisible(false);
                        fragment = new Wallet();
                        break;

                    case 2:

                            getSupportActionBar().setTitle("PROFILE");
                            item.setVisible(false);
                            itemfilter.setVisible(false);
                            itemeditprofile.setVisible(true);



                        fragment = new ProfileFragment();
                     /*   if(MainActivity.usertype == "Employer") {
                            fragment = new ProfileFragment();
                      }   else {

                            fragment = new EmployeeProfileFragment();
                        }*/
                        break;

                    case 3:

                        getSupportActionBar().setTitle("MORE");
                        item.setVisible(false);
                        itemfilter.setVisible(false);
                        itemeditprofile.setVisible(false);
                        fragment = new MoreFragment();

                        break;



                }
                ft[0] = getSupportFragmentManager().beginTransaction();
                ft[0].replace(R.id.output, fragment);
                ft[0].setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft[0].commit();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ft[0] = getSupportFragmentManager().beginTransaction();
                ft[0].detach(fragment);
                ft[0].commit();
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Boolean firstlogin = sharedpreferences.getBoolean("firstlogin", false);




        firstloogedin = firstlogin;

        Log.d("FIRSTLOGIN" , "bool " + firstloogedin);



        if(!firstloogedin) {

            SharedPreferences.Editor myPrefEditor = sharedpreferences.edit();
            myPrefEditor.putBoolean("firstlogin", true);
            myPrefEditor.commit();
            Intent intent = new Intent(home.this, CreateProfileActivity.class);
            startActivity(intent);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Get the SearchView and set the searchable configuration

         item = menu.findItem(R.id.action_search);
        itemfilter = menu.findItem(R.id.action_filter);
        itemeditprofile = menu.findItem(R.id.action_edit_profile);
        itemeditprofile.setVisible(false);


        itemfilter.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(home.this, FilterActivity.class);
                startActivity(intent);

                return true;
            }
        });

        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent intent = new Intent(home.this, SearchResultsActivity.class);
                startActivity(intent);

                return true;
            }

        });
        itemeditprofile.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent intent = new Intent(home.this, EditProfileActivity.class);
                startActivity(intent);

                return true;
            }

        });



        return true;
    }
}
