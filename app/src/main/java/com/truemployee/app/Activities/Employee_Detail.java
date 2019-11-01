package com.truemployee.app.Activities;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.truemployee.app.R;
import com.truemployee.app.Fragments.Wallet;

public class Employee_Detail extends AppCompatActivity {

    public static String Emp_Name, Emp_Designation , Emp_City , Emp_Description , Emp_pic;
    private TextView Emp_Name_Text , Emp_Designation_Text , Emp_City_Text , Emp_Description_Text;

    private CircleImageView Emp_pic_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee__detail);
        getSupportActionBar().setTitle("Employee Detail");


        Emp_Name_Text = findViewById(R.id.Detail_Name);
        Emp_Designation_Text = findViewById(R.id.Detail_Designation);
        Emp_City_Text = findViewById(R.id.Detail_City);
        Emp_Description_Text = findViewById(R.id.Detail_Description);

        Emp_pic_view = findViewById(R.id.Detail_Pic);



        Emp_Name_Text.setText(Emp_Name);
        Emp_Description_Text.setText(Emp_Description);
        Emp_Designation_Text.setText(Emp_Designation);
        Emp_City_Text.setText(Emp_City);


        Glide.with(Emp_pic_view.getContext()).load(Emp_pic).into(Emp_pic_view);




        Button show = findViewById(R.id.show);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Wallet.CREDITS -= 1;
                Intent in = new Intent(Employee_Detail.this, SubcriptionActivity.class);
                startActivity(in);
            }
        });
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        Log.d("ITEM HEIGHT", "" );
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
