// Copyright (C) 2018 INTUZ.

// Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
// "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish,
// distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to
// the following conditions:

// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
// MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR
// ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
// THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

package com.truemployee.app.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.util.Log;
import android.view.View;


import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.libraries.places.api.Places;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.truemployee.app.AppConstant;
import com.truemployee.app.Models.EmployeeModel;
import com.truemployee.app.R;
import com.truemployee.app.Requests.RequestOkhttp;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    EmployeeModel[] employeemodel;
    private String mobileNumber = "";
    private Button btnVerify;
    private static final int REQUEST_PHONE_VERIFICATION = 1080;

    public static final String MyPREFERENCES = "truEmployeePref" ;
    public static final String Verified = "verified";
    public static final String Phone_Number = "Phone_Number";
    SharedPreferences sharedpreferences;
    public static  String usertype = "";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        Places.initialize(getApplicationContext() , AppConstant.apikey);



                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

               Boolean verfy =  sharedpreferences.getBoolean(Verified , false);

                if (verfy == false) {

                }

        CardView letssearch = (CardView) findViewById(R.id.letssearch);
                letssearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this , FilterActivity.class);
                        startActivity(intent);
                    }
                });




        Button sign_in = (Button) findViewById(R.id.sign_in_button);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , Login.class);
                startActivity(intent);
            }
        });

        ImageButton search_home = (ImageButton) findViewById(R.id.search_home);

        search_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , SearchResultsActivity.class);
                startActivity(intent);
            }
        });

        final ConstraintLayout signinpage = findViewById(R.id.sign_in);
        final ProgressBar progressBar = findViewById(R.id.progressbar);

        signinpage.setVisibility(View.INVISIBLE);

        String url = AppConstant.Base_URL + "employee/";
        RequestOkhttp.get(url, null, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                if(!checkinternetconnection()) {
                    Toast.makeText(MainActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    progressBar.setVisibility(View.INVISIBLE);
                    signinpage.setVisibility(View.VISIBLE);
                    employeemodel = (EmployeeModel[]) GsonToModel(response );

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_PHONE_VERIFICATION:
// If mobile number is verified successfully then you get your phone number to perform further operations.
                if (data != null && data.hasExtra("PHONE_NUMBER") && data.getStringExtra("PHONE_NUMBER") != null) {
                    String phoneNumber = data.getStringExtra("PHONE_NUMBER");
                    mobileNumber = phoneNumber;

                    Log.d("Phone_Number", mobileNumber);

                } else {
                    // If mobile number is not verified successfully You can hendle according to your requirement.
                    Toast.makeText(MainActivity.this,getString(R.string.mobile_verification_fails),Toast.LENGTH_SHORT);
                }
                break;
        }
    }


    public  static  Object[] GsonToModel(Response response) throws IOException, JSONException {
        JSONArray jsonArray = new JSONArray(response.body().string());
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Object[] obj = gson.fromJson(jsonArray.toString(), EmployeeModel[].class);
        return obj;
    }

    public boolean checkinternetconnection(){

        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(MainActivity.this.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return connected = true;
        }
        else
            return connected = false;
    }
}
