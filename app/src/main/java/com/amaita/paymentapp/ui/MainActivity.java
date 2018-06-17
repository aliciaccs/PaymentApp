package com.amaita.paymentapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amaita.paymentapp.R;
import com.amaita.paymentapp.data.database.Payment;
import com.amaita.paymentapp.ui.adapters.PaymentsAdapter;
import com.amaita.paymentapp.ui.viewmodel.MainViewModel;
import com.amaita.paymentapp.ui.viewmodel.MainViewModelFactory;
import com.amaita.paymentapp.utils.InjectorUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView title;
    private RecyclerView rv_list;
    private MainViewModel mViewModel;
    private PaymentsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        title = findViewById(R.id.title);
        rv_list = findViewById(R.id.rv_list);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AmountActivity.class);
                startActivity(intent);
            }
        });

        progressBar.setVisibility(View.VISIBLE);
        title.setText(R.string.payments_explanation);
        //get the viewModel from the factory
        MainViewModelFactory factory = InjectorUtils.provideMainViewModelFactory(this );
        mViewModel = ViewModelProviders.of(this,factory).get(MainViewModel.class);
        mViewModel.getPayments().observe(this, new Observer<List<Payment>>() {
            @Override
            public void onChanged(@Nullable List<Payment> payments) {
                progressBar.setVisibility(View.GONE);
                if (payments != null)
                    setPaymentsAdapter(payments);
            }
        });
    }

    public void setPaymentsAdapter (List<Payment> payments) {
        GridLayoutManager layoutManager = new GridLayoutManager(this,1,GridLayoutManager.VERTICAL,false);
        rv_list.setLayoutManager(layoutManager);
        mAdapter = new PaymentsAdapter(this,payments);
        rv_list.setAdapter(mAdapter);
    }

}
