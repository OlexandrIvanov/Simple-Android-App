package com.example.simpleandroidapp.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.simpleandroidapp.repository.RepositoryController;
import com.example.simpleandroidapp.repository.response.Result;
import com.example.simpleandroidapp.repository.response.UserModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;


public class MainScreenViewModel extends ViewModel {

    private List<Result> data;

    private RepositoryController repositoryController;

    public MainScreenViewModel() {
        repositoryController = new RepositoryController();
        data = new ArrayList<>();
    }

    public void addDataObserver (Observer<UserModel> observer, String seed) {
        repositoryController.getUsersBySeed(observer, 10, seed);
    }
    public void updateList(List<Result> newData) {

        if (newData!=null){
            data.addAll(newData);
        }
    }

    public void clearList(){
        data.clear();
    }

    public List<Result> searchByName(String searchText) {
        List<Result> resultList = null;
        if (searchText.length()>2){
            resultList = new ArrayList<>();
            Log.d("Log", ""+ data.size());
            for (int a = 0; a < data.size(); a++){
                if (data.get(a).getName().getFirst().contains(searchText) || data.get(a).getName().getLast().contains(searchText)){
                    resultList.add(data.get(a));
                }
            }
        }
        return resultList;
    }
}
