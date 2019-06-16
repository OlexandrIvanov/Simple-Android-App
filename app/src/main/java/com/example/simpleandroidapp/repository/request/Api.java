package com.example.simpleandroidapp.repository.request;

import com.example.simpleandroidapp.repository.response.UserModel;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("api/")
    Observable<UserModel> getUserList(@Query("results") int results, @Query("seed") String seed);
}
