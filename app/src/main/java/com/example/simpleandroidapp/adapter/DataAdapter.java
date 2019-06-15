package com.example.simpleandroidapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.simpleandroidapp.R;
import com.example.simpleandroidapp.repository.response.Result;
import com.squareup.picasso.Picasso;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.Holder> {
    private List<Result> data;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    private Context context;
    public DataAdapter(Context context) {
        this.data = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.seed_list_item,
                new FrameLayout(parent.getContext()), false);
        return new Holder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        if (data!=null){
            holder.firstName.setText(data.get(position).getName().getFirst());
            holder.secondName.setText(data.get(position).getName().getLast());
            holder.age.setText(data.get(position).getDob().getAge().toString());
            Picasso.with(context).load(data.get(position).getPicture().getMedium()).into(holder.userImage);
            //holder.dataField.setText(data.get(position).getDob().getDate());

//            String s = data.get(position).getDob().getDate();
//            String stop =   s.substring(0, s.length() - 1);

            Calendar cal;
            Date date1 = null;
            try {
                date1 = formatter.parse(data.get(position).getDob().getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }finally {
                cal = Calendar.getInstance();
                cal.setTime(date1);
                String asd = cal.get(Calendar.MONTH)+ "-" +cal.get(Calendar.DAY_OF_MONTH) +"-" +cal.get(Calendar.YEAR);
                holder.dataField.setText(asd);
            }




        }

    }

    @Override
    public int getItemCount() {
        return  data!= null? data.size(): 0;
    }

    @Override
    public void onViewAttachedToWindow(Holder holder) {
        super.onViewAttachedToWindow(holder);

    }

    @Override
    public void onViewDetachedFromWindow(Holder holder) {
        super.onViewDetachedFromWindow(holder);

    }

    public void updateList(List<Result> newData) {
        data.addAll(newData);
        notifyDataSetChanged();
    }


    class Holder extends RecyclerView.ViewHolder {
        ImageView userImage;
        TextView firstName;
        TextView secondName;
        TextView dataField;
        TextView age;

        Holder(View v) {
            super(v);
            userImage = v.findViewById(R.id.user_image);
            firstName = v.findViewById(R.id.first_name_tv);
            secondName = v.findViewById(R.id.second_name_tv);
            dataField = v.findViewById(R.id.data_field_tv);
            age = v.findViewById(R.id.user_age_tv);

        }
    }
}
