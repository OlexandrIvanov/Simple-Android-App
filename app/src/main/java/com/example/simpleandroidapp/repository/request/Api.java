package com.example.simpleandroidapp.repository.request;

import com.example.simpleandroidapp.repository.response.UserModel;


import io.reactivex.Observable;
import retrofit2.http.GET;

public interface Api {
    //TODO: Зробити нормально.
    @GET("?results=10")
    Observable<UserModel> getUserList();
}
