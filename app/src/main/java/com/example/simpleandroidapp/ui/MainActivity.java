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
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    private EditText enterSeedEt;
    private TextView currentSeedTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enterSeedEt = findViewById(R.id.enter_seed_et);
        currentSeedTv = findViewById(R.id.current_seed_tv);

        EditText searchEt = findViewById(R.id.search_et);
        Button applyBtn = findViewById(R.id.apply_btn);
        Button cleatBtn = findViewById(R.id.cleat_btn);
        
        mainScreenViewModel = ViewModelProviders.of(this).get(MainScreenViewModel.class);
        setupView();
        initDialog();
        
        applyBtn.setOnClickListener(v -> {
           mainScreenViewModel.clearList();
           dataAdapter.clearList();
           listSize = 0;
           currentFirstVisible = 0;
           loadData(enterSeedEt.getText().toString());});

        cleatBtn.setOnClickListener(v -> {
            mainScreenViewModel.clearList();
            dataAdapter.clearList();
            enterSeedEt.setText("");
            currentSeedTv.setText("");
        });

        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dataAdapter.updateListBySearch(mainScreenViewModel.searchByName(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {}});
  }

    private void setupView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int newCurrentFirstVisible = linearLayoutManager.findFirstVisibleItemPosition();
                
                if ((listSize-7) <= newCurrentFirstVisible){
                    if (currentFirstVisible != newCurrentFirstVisible){
                       //loadData(enterSeedEt.getText().toString());
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

        dataAdapter = new DataAdapter(this);
        recyclerView.setAdapter(dataAdapter);

    }

    private void loadData(String seed){
        mainScreenViewModel.addDataObserver(new Observer<UserModel>() {
            @Override
            public void onSubscribe(Disposable d) {
                progressDialog.show();
            }

            @Override
            public void onNext(UserModel userModel) {
                //Log.d("Log",userModel.getInfo().getSeed());
                dataAdapter.updateList(userModel.getResults());
                mainScreenViewModel.updateList(userModel.getResults());
                progressDialog.dismiss();
                currentSeedTv.setText(userModel.getInfo().getSeed());
                listSize+=10;
            }

            @Override
            public void onError(Throwable e)
            {
               // Log.d("Log",e+"");
                listSize = listSize>0 ? listSize: 0;
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, R.string.connection_error, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onComplete() {

            }
        },seed);
    }
    
    private void initDialog(){
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }
}
