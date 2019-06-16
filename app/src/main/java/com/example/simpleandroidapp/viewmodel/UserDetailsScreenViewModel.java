package com.example.simpleandroidapp.viewmodel;

import android.arch.lifecycle.ViewModel;

public class UserDetailsScreenViewModel  extends ViewModel {
    private String firstName,secondName,birth,gender,location,email;

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

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void init(String firstName, String secondName, String birth,String gender, String location, String email) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.birth = birth;
        this.gender = gender;
        this.location = location;
        this.email = email;
    }
}
