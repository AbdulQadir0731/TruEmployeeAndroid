package com.truemployee.app.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.truemployee.app.Activities.Employee_Detail;
import com.truemployee.app.Models.EmployeeModel;
import com.truemployee.app.R;

public class Home_Adaptor extends RecyclerView.Adapter<Home_Adaptor.MyViewHolder> {
    private EmployeeModel[] mDataset;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout linearLayout;
        ImageView emp_pic;
        TextView emp_name;
        TextView emp_designation;
        TextView emp_city;
        LinearLayout lay;

        public MyViewHolder(@Nullable  View itemView) {
            super(itemView);

            emp_pic = (ImageView) itemView.findViewById(R.id.emppic);
            emp_name = (TextView) itemView.findViewById(R.id.emp_name);
            emp_designation = (TextView) itemView.findViewById(R.id.designation);
            emp_city = (TextView) itemView.findViewById(R.id.city);
            lay = (LinearLayout) itemView.findViewById(R.id.parentview);


        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public Home_Adaptor(Context context , EmployeeModel[] myDataset) {
        mDataset = myDataset;
        Log.d("LENGHT", String.valueOf(mDataset.length));
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public Home_Adaptor.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_home, parent, false);



        MyViewHolder vh = new MyViewHolder(v);
        TextView emp_name = parent.findViewById(R.id.emp_name);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
      //  holder.linearLayout.setBackgroundColor(Color.BLUE);
       // holder.textView.setText(mDataset[position]);

        holder.emp_name.setText(mDataset[position].getName());
        holder.emp_designation.setText(mDataset[position].getDesignation());
        holder.emp_city.setText(mDataset[position].getCity());
        holder.emp_name.setText(mDataset[position].getName());

        Glide.with(holder.emp_pic.getContext()).load(mDataset[position].getAvatar()).into(holder.emp_pic);


        holder.lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Employee_Detail.Emp_Name = mDataset[position].getName();
                Employee_Detail.Emp_Description = mDataset[position].getDescription();
                Employee_Detail.Emp_Designation = mDataset[position].getDesignation();
                Employee_Detail.Emp_City = mDataset[position].getCity();

                Employee_Detail.Emp_pic = mDataset[position].getAvatar();


                Intent intent = new Intent(context , Employee_Detail.class);
                context.startActivity(intent);
            }
        });






    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }


}
