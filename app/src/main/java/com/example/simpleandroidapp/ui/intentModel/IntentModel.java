package com.example.simpleandroidapp.ui.intentModel;

import android.os.Parcel;
import android.os.Parcelable;

public class IntentModel implements Parcelable {

    private String userImageURL, firstName,secondName,birth,gender,location,email;

    public String getUserImageURL() {
        return userImageURL;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getBirth() {
        return birth;
    }

    public String getGender() {
        return gender;
    }

    public String getLocation() {
        return location;
    }

    public String getEmail() {
        return email;
    }

    public IntentModel(String userImageURL, String firstName, String secondName, String birth, String gender, String location, String email) {
        this.userImageURL = userImageURL;
        this.firstName = firstName;
        this.secondName = secondName;
        this.birth = birth;
        this.gender = gender;
        this.location = location;
        this.email = email;
    }

    private IntentModel(Parcel in) {
        userImageURL = in.readString();
        firstName = in.readString();
        secondName = in.readString();
        birth = in.readString();
        gender = in.readString();
        location = in.readString();
        email = in.readString();
    }


    public static final Creator<IntentModel> CREATOR = new Creator<IntentModel>() {
        @Override
        public IntentModel createFromParcel(Parcel in) {
            return new IntentModel(in);
        }

        @Override
        public IntentModel[] newArray(int size) {
            return new IntentModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(userImageURL);
        parcel.writeString(firstName);
        parcel.writeString(secondName);
        parcel.writeString(birth);
        parcel.writeString(gender);
        parcel.writeString(location);
        parcel.writeString(email);
    }
}
