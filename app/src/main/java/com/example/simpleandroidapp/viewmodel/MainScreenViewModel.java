package com.example.simpleandroidapp.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.example.simpleandroidapp.repository.RepositoryController;
import com.example.simpleandroidapp.repository.response.UserModel;

import io.reactivex.Observer;


public class MainScreenViewModel extends ViewModel {

    private RepositoryController repositoryController;

    public void addDataObserver (Observer<UserModel> observer) {
        repositoryController.getUsers(observer);
    }

    public MainScreenViewModel() {
        repositoryController = new RepositoryController();

    }

}
