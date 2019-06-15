package com.example.simpleandroidapp.ui;


import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;

import com.example.simpleandroidapp.R;
import com.example.simpleandroidapp.adapter.DataAdapter;

import com.example.simpleandroidapp.repository.response.UserModel;
import com.example.simpleandroidapp.viewmodel.MainScreenViewModel;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private MainScreenViewModel mainScreenViewModel;
    private DataAdapter dataAdapter;

    private int listSize = 0;
    private int currentFirstVisible = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainScreenViewModel = ViewModelProviders.of(this).get(MainScreenViewModel.class);
        setupView();
        loadData();
    }

    private EditText searchEditText;

    private void setupView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        //searchEditText = (EditText) findViewById(R.id.et_search);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false);

        Log.d("Log","asdas");
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int newCurrentFirstVisible = linearLayoutManager.findFirstVisibleItemPosition();


                if ((listSize-7) <= newCurrentFirstVisible){

                    if (currentFirstVisible != newCurrentFirstVisible){
                        loadData();
                        currentFirstVisible = newCurrentFirstVisible;
                    }

                }
            }
        });
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.hasFixedSize();
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(), LinearLayoutManager.VERTICAL
        );
        recyclerView.addItemDecoration(mDividerItemDecoration);

        dataAdapter = new DataAdapter(getApplicationContext());
        recyclerView.setAdapter(dataAdapter);

    }

    private void loadData(){
        mainScreenViewModel.addDataObserver(new Observer<UserModel>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(UserModel userModel) {
                dataAdapter.updateList(userModel.getResults());
                progressDialog.dismiss();
                listSize+=10;
            }

            @Override
            public void onError(Throwable e) {
                listSize = listSize>0 ? listSize: 0;
            }

            @Override
            public void onComplete() {

            }
        });

    }
}
