package com.example.simpleandroidapp.ui;


import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.simpleandroidapp.R;
import com.example.simpleandroidapp.Tools;
import com.example.simpleandroidapp.databinding.ActivityUserDetailsScreenBinding;
import com.example.simpleandroidapp.ui.intentModel.IntentModel;
import com.example.simpleandroidapp.viewmodel.UserDetailsScreenViewModel;
import com.squareup.picasso.Picasso;

public class UserDetailsScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details_screen);

        IntentModel intentModel = getIntent().getParcelableExtra(
                IntentModel.class.getCanonicalName());

        UserDetailsScreenViewModel mainScreenViewModel = ViewModelProviders.of(this).get(UserDetailsScreenViewModel.class);
        mainScreenViewModel.init(intentModel.getFirstName(),
                intentModel.getSecondName(),
                Tools.parceDateToRightFormat(intentModel.getBirth()),
                intentModel.getGender(),
                intentModel.getLocation(),
                intentModel.getEmail()
        );

        ActivityUserDetailsScreenBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_user_details_screen);
        binding.setViewModel(mainScreenViewModel
        );

        ImageView userImage = findViewById(R.id.user_image);

        Picasso.with(getApplicationContext()).load(intentModel.getUserImageURL()).into(userImage);
    }
}
