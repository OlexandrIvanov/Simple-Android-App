package com.example.simpleandroidapp.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

public class ListItemViewModel extends BaseObservable {
    private String dataModel;

    public ListItemViewModel(String dataModel) {
        this.dataModel = dataModel;
    }

    public void setUp() {
        // perform set up tasks, such as adding listeners
    }

    public void tearDown() {
        // perform tear down tasks, such as removing listeners
    }

    @Bindable
    public String getTitle() {
        return dataModel;
    }
}
