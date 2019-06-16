package com.example.simpleandroidapp.repository.request;

import com.example.simpleandroidapp.BuildConfig;


import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class Client {
    private static Client ourInstance = new Client();
    private static Api api;

    private static final String BASE_URL = "http://randomuser.me/";

    public static Client getInstance() {
        return ourInstance;
    }

    private Client() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel((BuildConfig.DEBUG) ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();


        api = retrofit.create(Api.class);
    }

    public Api getApi() {
        return api;
    }
}
