package com.truemployee.app.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.phonenumberui.utility.Utility;
import com.truemployee.app.R;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

public class Login extends AppCompatActivity {
    private static final int REQUEST_PHONE_VERIFICATION = 1080;
    private String mobileNumber = "";
    public static final String MyPREFERENCES = "truEmployeePref" ;
    public static final String Verified = "verified";
    public static final String Phone_Number = "Phone_Number";
    SharedPreferences sharedpreferences;
    private CardView sign_in_button_login;
    private EditText phone_no_login;
    private EditText password_login;
    private String phoneno = "";
    private String fullname, mothername , cnic , password;
    private Boolean alreadyregistered ;
    public static boolean firstloogedin = false;


    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }



        progressBar = findViewById(R.id.progressBar);
        sign_in_button_login = findViewById(R.id.sign_in_button_login);
        phone_no_login = findViewById(R.id.phone_no_login);
        password_login = findViewById(R.id.password_login);



        sign_in_button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    LoginPostRequest();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Button sign_up = (Button) findViewById(R.id.signup_btn);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this , Register_Employer.class);
                startActivity(intent);
            }
        });
        ImageButton back = (ImageButton) findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                    Log.d("Success", mobileNumber);
                } else {
                    // If mobile number is not verified successfully You can hendle according to your requirement.
                    Toast.makeText(Login.this,getString(R.string.mobile_verification_fails),Toast.LENGTH_SHORT);
                }
                break;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Boolean verify = sharedpreferences.getBoolean(Verified, false);
        phoneno = sharedpreferences.getString("Phone_Number", "not available");
        fullname = sharedpreferences.getString("Full_Name", "not available");
        mothername = sharedpreferences.getString("Mother_Name", "not available");
        cnic = sharedpreferences.getString("CNIC", "not available");
        password = sharedpreferences.getString("Password", "not available");

        alreadyregistered = sharedpreferences.getBoolean("alreadyregistered", false);

        Log.d("AlreadyRegister", "  " + alreadyregistered);

        Log.d("Verified", verify.toString());
        if (verify) {
            Log.d("In", "Verify");
            Log.d("Phone", phoneno);
            if (!password.contains("not") && !fullname.contains("not")) {
                //sign in as Employee
                Log.d("EMPLOYEE", "SIGN IN");

                if (alreadyregistered) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    builder1.setTitle("Continue");
                    builder1.setMessage("as Employee with Full name " + fullname + " and Phone Number " + phoneno);
                    builder1.setCancelable(true);
                    builder1.setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    builder1.setPositiveButton("Login",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    try {
                                        if (alreadyregistered) {
                                            LoginPostRequest();
                                        }

                                    } catch (IOException e) {
                                        Log.d("Error before", String.valueOf(e));
                                    }
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

            } else if (!password.contains("not") && mothername.contains("not")) {

                //sign in as Employer
                if (alreadyregistered) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    builder1.setTitle("Continue");
                    builder1.setMessage("as Employer with" + " Phone Number " + phoneno);
                    builder1.setCancelable(true);
                    builder1.setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    builder1.setPositiveButton("Sign Up",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    try {
                                        if (alreadyregistered) {
                                            LoginPostRequest();
                                        }

                                    } catch (IOException e) {
                                        Log.d("Error", String.valueOf(e));
                                    }
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                    Log.d("EMPLOYER", "SIGN IN");
                }
            }


        }

        if (verify) {

            if (!alreadyregistered) {
                if (!password.contains("not") && !fullname.contains("not")) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    builder1.setTitle("Continue");
                    builder1.setMessage("as Employee with Full name " + fullname + " and Phone Number " + phoneno);
                    builder1.setCancelable(true);
                    builder1.setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    builder1.setPositiveButton("Sign Up",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    try {

                                        postRequest();


                                    } catch (IOException e) {
                                        Log.d("Error before", String.valueOf(e));
                                    }
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                } else if (!password.contains("not") && mothername.contains("not")) {

                    //sign in as Employer

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    builder1.setTitle("Continue");
                    builder1.setMessage("as Employer with" + " Phone Number " + phoneno);
                    builder1.setCancelable(true);
                    builder1.setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    builder1.setPositiveButton("Sign Up",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    try {

                                        postRequest();


                                    } catch (IOException e) {
                                        Log.d("Error", String.valueOf(e));
                                    }
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                    Log.d("EMPLOYER", "SIGN IN");
                }
            }


            Log.i("Resume", "On Resume .....");
        }
    }





    public void postRequest() throws IOException {
        progressBar.setVisibility(ProgressBar.VISIBLE);
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        String url = "https://truemployee.herokuapp.com/api/v1/rest-auth/registration/";

        OkHttpClient client = new OkHttpClient();

        JSONObject postdata = new JSONObject();

        try {
            postdata.put("username", phoneno);
            postdata.put("password1", password);
            postdata.put("password2", password);
        } catch(JSONException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.d("INRegister", "in register");
        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);


                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                        Utility.showToast(Login.this , "mMessage");



                    }
                });

                //call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        SharedPreferences.Editor myPrefEditor = sharedpreferences.edit();
                        myPrefEditor.putBoolean("alreadyregistered", true);
                        myPrefEditor.commit();
                        Boolean test = sharedpreferences.getBoolean("alreadyregistered" , false);
                        Log.d("Already" , test.toString() ) ;
                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                        try {
                            LoginPostRequest();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }




                    }
                });
                String mMessage = response.body().string();
                Log.e("Message", mMessage);

            }
        });
    }



    public void LoginPostRequest() throws IOException {
        progressBar.setVisibility(ProgressBar.VISIBLE);
        if(phone_no_login.getText().toString().isEmpty() || password_login.getText().toString().isEmpty()){
            phone_no_login.setText(phoneno);
            password_login.setText(password);

        }

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        String url = "https://truemployee.herokuapp.com/api/v1/rest-auth/login/";

        OkHttpClient client = new OkHttpClient();

        JSONObject postdata = new JSONObject();


        try {
            postdata.put("username", phone_no_login.getText().toString());
            postdata.put("password", password_login.getText().toString());

        } catch(JSONException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                        Utility.showToast(Login.this , "mMessage");
                    }
                });
                //call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String mMessage = response.body().string();
                Log.e("Message", mMessage);

                if(mMessage.contains("Unable")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(ProgressBar.INVISIBLE);
                                Utility.showToast(Login.this , "Credentials incorrect" );
                            }
                        });
                }
                else if(mMessage.contains("blank")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(ProgressBar.INVISIBLE);
                            Utility.showToast(Login.this , "Please provide credentials." );
                        }
                    });
                } else {
                    if (firstloogedin == false) {
                        firstloogedin = true;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(ProgressBar.INVISIBLE);
                                Intent intent = new Intent(Login.this, CreateProfileActivity.class);
                                startActivity(intent);
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(ProgressBar.INVISIBLE);
                                Intent intent = new Intent(Login.this, home.class);
                                startActivity(intent);
                            }
                        });
                    }
                }
            }
        });
    }
}
