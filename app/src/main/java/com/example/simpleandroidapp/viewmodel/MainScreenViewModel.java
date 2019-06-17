package com.example.simpleandroidapp.viewmodel;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.simpleandroidapp.adapter.DataAdapter;
import com.example.simpleandroidapp.repository.RepositoryController;
import com.example.simpleandroidapp.repository.response.Result;
import com.example.simpleandroidapp.repository.response.UserModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class MainScreenViewModel extends ViewModel {

    private int startListSize = 0;
    private String searchSeed;

    private LifecycleOwner lifecycleOwner;

    private DataAdapter dataAdapter;

    private RepositoryController repositoryController;

    private MutableLiveData<String> seedLiveData = new MutableLiveData<>();

    public LiveData<String> getSeedLiveData() {
        return seedLiveData;
    }

    private MutableLiveData<Boolean> showDialogLiveData = new MutableLiveData<>();

    public LiveData<Boolean> getShowDialogLiveData() {
        return showDialogLiveData;
    }

    private MutableLiveData<Boolean> showToastLiveData = new MutableLiveData<>();

    public LiveData<Boolean> getShowToastLiveData() {
        return showToastLiveData;
    }

    public DataAdapter getAdapter() {
        return this.dataAdapter;
    }

    public MainScreenViewModel(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
        repositoryController = new RepositoryController();
        dataAdapter = new DataAdapter();
        initLiveData();
    }

   private void initLiveData(){
        dataAdapter.getListPositionLiveData().observe(lifecycleOwner, this::upDataList);
    }

    private void upDataList(int position){
        if (startListSize-1 == position){
            showDialogLiveData.setValue(true);
            repositoryController.getUsersBySeed(new Observer<UserModel>() {

                @Override
                public void onSubscribe(Disposable d) { }

                @Override
                public void onNext(UserModel userModel) {
                    dataAdapter.updateList(userModel.getResults());
                    showDialogLiveData.setValue(false);
                    startListSize += userModel.getResults().size();
                }

                @Override
                public void onError(Throwable e) {
                    showDialogLiveData.setValue(false);
                    showToastLiveData.setValue(true);
                }
                @Override
                public void onComplete() {}

            }, 10, searchSeed);

        }
    }

    public void applyOnClick(String searchSeed){
        this.searchSeed = searchSeed;
        showDialogLiveData.setValue(true);
        repositoryController.getUsersBySeed(new Observer<UserModel>() {
            @Override
            public void onSubscribe(Disposable d) {}

            @Override
            public void onNext(UserModel userModel) {
                dataAdapter.clearList();
                dataAdapter.updateList(userModel.getResults());
                seedLiveData.setValue(userModel.getInfo().getSeed());
                showDialogLiveData.setValue(false);
                startListSize = userModel.getResults().size();
            }

            @Override
            public void onError(Throwable e)
            {
                showDialogLiveData.setValue(false);
                showToastLiveData.setValue(true);
            }

            @Override
            public void onComplete() {}
        }, 10, searchSeed);


    }

    public void clearOnClick(){
        dataAdapter.clearList();
    }

    public void searchEtChanged(String searchText) {
        List<Result> resultList = null;
        List<Result> dataFromAdapter = dataAdapter.getData();

        if (searchText.length()>2){
            resultList = new ArrayList<>();
            for (int a = 0; a < dataFromAdapter.size(); a++){
                if (dataFromAdapter.get(a).getName().getFirst().contains(searchText) || dataFromAdapter.get(a).getName().getLast().contains(searchText)){
                    resultList.add(dataFromAdapter.get(a));
                }
            }
        }
        dataAdapter.updateListBySearch(resultList);
    }
}
