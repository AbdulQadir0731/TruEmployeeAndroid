package com.truemployee.app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.truemployee.app.R;

public class SubcriptionActivity extends AppCompatActivity {

    ListView planlist;


    public static boolean jazzcash, easypaisa, banktransfer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcription);
        getSupportActionBar().setTitle("Select a Plan");

        Button jazzcashbutton , easypaisabutton , banktransferbutton;
        jazzcashbutton = findViewById(R.id.jazzcash);
        easypaisabutton = findViewById(R.id.easypaisa);
        banktransferbutton = findViewById(R.id.banktransfer);


        jazzcashbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                banktransfer = false;
                easypaisa = false;
                jazzcash = true;
                Intent intent = new Intent(SubcriptionActivity.this, BankDetailActivity.class);
                startActivity(intent);


            }
        });

        banktransferbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                easypaisa = false;
                banktransfer = true;
                jazzcash = false;
                Intent intent = new Intent(SubcriptionActivity.this, BankDetailActivity.class);
                startActivity(intent);
            }
        });

        easypaisabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                easypaisa = true;
                banktransfer = false;
                jazzcash = false;
                Intent intent = new Intent(SubcriptionActivity.this, BankDetailActivity.class);
                startActivity(intent);
            }
        });





        planlist = findViewById(R.id.plan_list);


        planlist.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 3;
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
                    convertView = LayoutInflater.from(SubcriptionActivity.this).inflate(R.layout.list_item_subcription, null);


                }
                return convertView;
            }
        });


        setListViewHeightBasedOnChildren(planlist);


    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        Log.d("ITEM HEIGHT", "");
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        View listItem = listAdapter.getView(0, null, listView);
        listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
        totalHeight = listItem.getMeasuredHeight();

        Log.d("ITEMS", String.valueOf(totalHeight));
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        Log.d("PARAMS", String.valueOf(params.height));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
