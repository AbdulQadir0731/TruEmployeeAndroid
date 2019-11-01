package com.truemployee.app.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.truemployee.app.AppConstant;
import com.phonenumberui.CountryCodeActivity;
import com.phonenumberui.VerificationCodeActivity;
import com.phonenumberui.countrycode.Country;
import com.phonenumberui.countrycode.CountryUtils;
import com.phonenumberui.utility.Utility;
import com.truemployee.app.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;

import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;

public class Register extends AppCompatActivity {
    private AppCompatEditText etCountryCode;
    private AppCompatEditText etPhoneNumber;
    private CardView btnSendConfirmationCode;
    private ImageView imgFlag;
    private AppCompatTextView tvToolbarTitle;
    private Activity mActivity = Register.this;
    private PhoneNumberUtil mPhoneUtil;
    private Country mSelectedCountry;
    private static final int COUNTRYCODE_ACTION = 1;
    private static final int VERIFICATION_ACTION = 2;
    private Pattern pattern;
    private Matcher matcher;
    private static final String PASSWORD_PATTERN = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})";
    public String title = "";

    SharedPreferences sharedPreferences;



    //Fields
    EditText fullname;
    EditText password;
    EditText confirm_password;
    EditText cnic;
    EditText mother_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        pattern = Pattern.compile(PASSWORD_PATTERN);
        setUpUI();

        final ScrollView sv = (ScrollView)findViewById(R.id.scrollview);
        fullname = findViewById(R.id.full_name);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
        cnic = findViewById(R.id.cnic);
        mother_name = (EditText) findViewById(R.id.mother_name);



        mother_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d("DownCLICK", "down");



            }
        });





        final ImageButton employer_but = (ImageButton) findViewById(R.id.employer_but);
        final ImageButton employee_but = (ImageButton) findViewById(R.id.employee_but);


        final int activecolor = getResources().getColor(R.color.colorPrimary);
        final int disablecolor = getResources().getColor(R.color.light_gray);


        employer_but.setColorFilter(disablecolor);
        employee_but.setColorFilter(activecolor);



        employer_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this , Register_Employer.class);
                startActivity(intent);
                finish();

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
    private void setUpUI() {
        etCountryCode = findViewById(com.phonenumberui.R.id.etCountryCode);
        etPhoneNumber = findViewById(com.phonenumberui.R.id.etPhoneNumber);
        imgFlag = findViewById(com.phonenumberui.R.id.flag_imv);
        btnSendConfirmationCode = findViewById(com.phonenumberui.R.id.btnSendConfirmationCode);
        tvToolbarTitle = findViewById(com.phonenumberui.R.id.tvToolbarTitle);
        mPhoneUtil = PhoneNumberUtil.createInstance(mActivity);

//
        TelephonyManager tm = (TelephonyManager) getSystemService(getApplicationContext().TELEPHONY_SERVICE);
        final String countryISO = tm.getNetworkCountryIso();
        String countryNumber = "";
        String countryName = "";
        Utility.log(countryISO);

        if(!TextUtils.isEmpty(countryISO))
        {
            for (Country country : CountryUtils.getAllCountries(mActivity)) {
                if (countryISO.toLowerCase().equalsIgnoreCase(country.getIso().toLowerCase())) {
                    countryNumber = country.getPhoneCode();
                    countryName = country.getName();
                    break;
                }
            }
            Country country = new Country(countryISO,
                    countryNumber,
                    countryName);
            this.mSelectedCountry = country;
            etCountryCode.setText("+" + country.getPhoneCode() + "");
            imgFlag.setImageResource(CountryUtils.getFlagDrawableResId(country.getIso()));
            Utility.log(countryNumber);
        }
        else {
            Country country = new Country(getString(com.phonenumberui.R.string.country_united_states_code),
                    getString(com.phonenumberui.R.string.country_united_states_number),
                    getString(com.phonenumberui.R.string.country_united_states_name));
            this.mSelectedCountry = country;
            etCountryCode.setText("+" + country.getPhoneCode() + "");
            imgFlag.setImageResource(CountryUtils.getFlagDrawableResId(country.getIso()));
            Utility.log(countryNumber);
        }

        setPhoneNumberHint();
        etCountryCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.hideKeyBoardFromView(mActivity);
                etPhoneNumber.setError(null);
                Intent intent = new Intent(mActivity, CountryCodeActivity.class);
                intent.putExtra("TITLE", getResources().getString(com.phonenumberui.R.string.app_name));
                startActivityForResult(intent, COUNTRYCODE_ACTION);
            }
        });
        btnSendConfirmationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.hideKeyBoardFromView(mActivity);
                etPhoneNumber.setError(null);
                String fname , mname , cni , passw , cpasssw ;


                fname = fullname.getText().toString();
                mname = mother_name.getText().toString();
                cni = cnic.getText().toString();
                passw = password.getText().toString();
                cpasssw = confirm_password.getText().toString();

                if(fname.isEmpty()){

                    fullname.setError("Please Enter your Full Name");

                }


                 if(mname.isEmpty()){

                    mother_name.setError("Please Enter your Mother Name");

                }

                 if(cni.isEmpty()){

                    cnic.setError("Please Enter your CNIC");

                }
                 if(passw.isEmpty()){

                    password.setError("Please Enter your Password");

                }
                if(!validatepassword(password.getText().toString())){

                    password.setError("This password is too short. It must contain at least 8 characters.");
                    Utility.showToast(Register.this, "This password is too short. It must contain at least 8 characters.");
                }
                if(cpasssw.isEmpty()){

                    confirm_password.setError("Please Enter your Confirm Password");


                } else if(!confirm_password.getText().toString().equals(password.getText().toString())){
                    Utility.showToast(Register.this,  " Please make sure your Password and Confirm Password are same" + "Password = " + password.getText() + "   Confirm Password = " + confirm_password.getText() );
                    confirm_password.setError("Your Password and Confirm Password does not match");
                }


                if (validate()  && !passw.isEmpty() && !cpasssw.isEmpty() && confirm_password.getText().toString().equals(password.getText().toString()) && validatepassword(password.getText().toString())) {
                    sharedPreferences = getSharedPreferences("truEmployeePref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor myPrefEditor = sharedPreferences.edit();


                    myPrefEditor.putString("Password", passw);
                    myPrefEditor.putString("Full_Name",fullname.getText().toString());
                    myPrefEditor.putString("Mother_Name",mother_name.getText().toString());
                    myPrefEditor.putString("CNIC",cnic.getText().toString());



                    myPrefEditor.commit();



                    Intent verificationIntent = new Intent(mActivity, VerificationCodeActivity.class);
                    verificationIntent.putExtra(AppConstant.PhoneNumber, etPhoneNumber.getText().toString().trim());
                    verificationIntent.putExtra(AppConstant.PhoneCode, mSelectedCountry.getPhoneCode() + "");
                    verificationIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                    startActivity(verificationIntent);
                    finish();
                }
            }
        });

        if (getIntent().getExtras() != null) {
            if (getIntent().hasExtra("PHONE_NUMBER")) {

                etPhoneNumber.setText(getIntent().getStringExtra("PHONE_NUMBER"));
                etPhoneNumber.setSelection(etPhoneNumber.getText().toString().trim().length());
            }
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void setPhoneNumberHint() {
        if (mSelectedCountry != null) {
            Phonenumber.PhoneNumber phoneNumber =
                    mPhoneUtil.getExampleNumberForType(mSelectedCountry.getIso().toUpperCase(),
                            PhoneNumberUtil.PhoneNumberType.MOBILE);
            if (phoneNumber != null) {
                String format = mPhoneUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164);
                if (format.length() > mSelectedCountry.getPhoneCode().length())
                    etPhoneNumber.setHint(
                            format.substring((mSelectedCountry.getPhoneCode().length() + 1), format.length()));
            }
        }
    }

    private boolean validate() {
        if (TextUtils.isEmpty(etPhoneNumber.getText().toString().trim())) {
            etPhoneNumber.setError("Please enter phone number");
            etPhoneNumber.requestFocus();
            return false;
        } else if (!isValid()) {
            etPhoneNumber.setError("Please enter valid phone number");
            etPhoneNumber.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == COUNTRYCODE_ACTION) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    if (data.hasExtra(AppConstant.COUNTRY)) {
                        Country country = (Country) data.getSerializableExtra(AppConstant.COUNTRY);
                        this.mSelectedCountry = country;
                        setPhoneNumberHint();
                        etCountryCode.setText("+" + country.getPhoneCode() + "");
                        MainActivity.usertype = "Employee";
                        imgFlag.setImageResource(CountryUtils.getFlagDrawableResId(country.getIso()));
                    }
                }
            }
        } else if (requestCode == VERIFICATION_ACTION) {
            if (data != null) {

            }
        }
    }

    public boolean isValid() {
        Phonenumber.PhoneNumber phoneNumber = getPhoneNumber();
        return phoneNumber != null && mPhoneUtil.isValidNumber(phoneNumber);
    }

    public Phonenumber.PhoneNumber getPhoneNumber() {
        try {
            String iso = null;
            if (mSelectedCountry != null) {
                iso = mSelectedCountry.getIso().toUpperCase();
            }
            return mPhoneUtil.parse(etPhoneNumber.getText().toString().trim(), iso);
        } catch (NumberParseException ignored) {
            ignored.printStackTrace();
            return null;
        }
    }
    public boolean validatepassword(final String password) {

        matcher = pattern.matcher(password);
        return matcher.matches();

    }
}
