package com.example.simpleandroidapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simpleandroidapp.R;
import com.example.simpleandroidapp.Tools;
import com.example.simpleandroidapp.repository.response.Result;
import com.example.simpleandroidapp.ui.UserDetailsScreen;
import com.example.simpleandroidapp.ui.intentModel.IntentModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.Holder> {
    private List<Result> data;

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
            holder.dataField.setText(Tools.parceDateToRightFormat(data.get(position).getDob().getDate()));

            holder.consLayout.setOnClickListener(v -> context.startActivity(getIntentForUserDetailsScreen(position)));
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
        if (newData!=null){
            data.addAll(newData);
            notifyDataSetChanged();
        }
    }

    private Intent getIntentForUserDetailsScreen(int position){
        IntentModel intentModel = new IntentModel(data.get(position).getPicture().getLarge(),
                data.get(position).getName().getFirst(),
                data.get(position).getName().getLast(),
                data.get(position).getDob().getDate(),
                data.get(position).getGender(),
                data.get(position).getLocation().getCity(),
                data.get(position).getEmail());

        Intent intent = new Intent(context, UserDetailsScreen.class);
        intent.putExtra(IntentModel.class.getCanonicalName(), intentModel);
        return intent;
    }

    public void updateListBySearch(List<Result> newData) {
        if (newData!=null){
            data.clear();
            data.addAll(newData);
            notifyDataSetChanged();
        }
    }

    public void clearList(){
        data.clear();
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView userImage;
        TextView firstName;
        TextView secondName;
        TextView dataField;
        TextView age;
        ConstraintLayout consLayout;

        Holder(View v) {
            super(v);
            userImage = v.findViewById(R.id.user_image);
            firstName = v.findViewById(R.id.first_name_tv);
            secondName = v.findViewById(R.id.second_name_tv);
            dataField = v.findViewById(R.id.data_field_tv);
            age = v.findViewById(R.id.user_age_tv);
            consLayout = v.findViewById(R.id.cons_layout);
        }
    }
}
