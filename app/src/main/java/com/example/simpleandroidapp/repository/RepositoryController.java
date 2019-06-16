package com.example.simpleandroidapp.repository;

import com.example.simpleandroidapp.repository.request.Api;
import com.example.simpleandroidapp.repository.request.Client;
import com.example.simpleandroidapp.repository.response.UserModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class RepositoryController {

    private Api api = Client.getInstance().getApi();


    public void getUsersBySeed(Observer<UserModel> arrayListObserver, int numberOfResults, String seed) {
        api.getUserList(numberOfResults, seed)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(arrayListObserver);
    }
}
