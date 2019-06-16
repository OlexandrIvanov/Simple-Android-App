package com.example.simpleandroidapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simpleandroidapp.R;
import com.example.simpleandroidapp.Tools;
import com.example.simpleandroidapp.ui.intentModel.IntentModel;
import com.squareup.picasso.Picasso;

public class UserDetailsScreen extends AppCompatActivity {

    ImageView userImage;
    TextView firstNameTv, secondNameTv,birthTv,genderTv,locationTv, emailTv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details_screen);

        IntentModel intentModel = getIntent().getParcelableExtra(
                IntentModel.class.getCanonicalName());

        userImage = findViewById(R.id.user_image);

        firstNameTv = findViewById(R.id.first_name_tv);
        secondNameTv = findViewById(R.id.second_name_tv);
        birthTv = findViewById(R.id.birth_tv);
        genderTv = findViewById(R.id.gender_tv);
        locationTv = findViewById(R.id.location_tv);
        emailTv = findViewById(R.id.email_tv);

        Picasso.with(getApplicationContext()).load(intentModel.getUserImageURL()).into(userImage);
        firstNameTv.setText(intentModel.getFirstName());
        secondNameTv.setText(intentModel.getSecondName());
        birthTv.setText(Tools.parceDateToRightFormat(intentModel.getBirth()));

        genderTv.setText(intentModel.getGender());
        locationTv.setText(intentModel.getLocation());
        emailTv.setText(intentModel.getEmail());
    }
}
