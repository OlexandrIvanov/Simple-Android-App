package com.example.simpleandroidapp.ui;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simpleandroidapp.R;
import com.example.simpleandroidapp.viewmodel.MainScreenViewModel;


public class MainActivity extends AppCompatActivity {


    private ProgressDialog progressDialog;
    private MainScreenViewModel mainScreenViewModel;

    private EditText enterSeedEt;
    private TextView currentSeedTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enterSeedEt = findViewById(R.id.enter_seed_et);
        currentSeedTv = findViewById(R.id.current_seed_tv);

        EditText searchEt = findViewById(R.id.search_et);
        
        mainScreenViewModel = new MainScreenViewModel(this);

        setupView();
        initDialog();
        initLiveData();


        findViewById(R.id.apply_btn).setOnClickListener(v -> mainScreenViewModel.applyOnClick(enterSeedEt.getText().toString()));

        findViewById(R.id.cleat_btn).setOnClickListener(v -> {
            mainScreenViewModel.clearOnClick();
            enterSeedEt.setText("");
            currentSeedTv.setText("");
        });

        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mainScreenViewModel.searchEtChanged(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}});
    }

    private void initLiveData(){
      mainScreenViewModel.getSeedLiveData().observe(this, s -> currentSeedTv.setText(s));
      mainScreenViewModel.getShowDialogLiveData().observe(this,s->{
          if (s!=null && s){
              progressDialog.show();
              return;
          }
          progressDialog.dismiss();
      });
      mainScreenViewModel.getShowToastLiveData().observe(this, s-> Toast.makeText(MainActivity.this, R.string.connection_error, Toast.LENGTH_LONG).show());
    }

    private void setupView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mainScreenViewModel.getAdapter());

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
