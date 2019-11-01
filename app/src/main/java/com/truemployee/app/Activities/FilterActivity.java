package com.truemployee.app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.truemployee.app.R;
import com.truemployee.app.Activities.home;

public class FilterActivity extends AppCompatActivity {

    int AUTOCOMPLETE_REQUEST_CODE = 1;
    EditText areaselect;
    ListView profession_list;
    String[] myResArray;
    TextView searcharea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        myResArray = getResources().getStringArray(R.array.designation);
        getSupportActionBar().hide();
        Button search = findViewById(R.id.search);
        Button cancel = findViewById(R.id.cancel);

        SeekBar ratingseekbar = findViewById(R.id.rating_seekbar);
        final TextView ratingtext = findViewById(R.id.rating);

        profession_list = findViewById(R.id.professionlist);


        profession_list.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {

                int count = myResArray.length;
                return count;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if (convertView == null) {
                    convertView = LayoutInflater.from(FilterActivity.this).inflate(R.layout.filter_professions, null);
                }



                final RadioButton radioButton = (RadioButton) convertView.findViewById(R.id.radiobutton_profession);


                radioButton.setText(myResArray[position]);


                return convertView;
            }
        });






        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(FilterActivity.this , home.class);
                startActivity(intent);
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(FilterActivity.this , home.class);
                startActivity(intent);
            }
        });

        ratingseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String rat =String.valueOf(progress);
                ratingtext.setText(rat);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });





    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
}
