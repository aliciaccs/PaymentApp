package com.amaita.paymentapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amaita.paymentapp.R;
import com.amaita.paymentapp.data.network.response.PaymentMethod;
import com.amaita.paymentapp.ui.adapters.PaymentMethodsAdapter;
import com.amaita.paymentapp.ui.viewmodel.PaymentMethodsViewModel;
import com.amaita.paymentapp.ui.viewmodel.PaymentMethodsViewModelFactory;
import com.amaita.paymentapp.utils.InjectorUtils;

import java.util.List;

public class PaymentMethodsActivity extends AppCompatActivity implements  PaymentMethodsAdapter.ListItemClickListener {

    private RecyclerView rv_paymentmethods;
    private PaymentMethodsAdapter mAdapter;
    //view Model
    private PaymentMethodsViewModel mViewModel;
    private ProgressBar progressBar;
    private TextView txt_amount_payment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_methods);

        double monto = getIntent().getDoubleExtra("amount",0);

        rv_paymentmethods =  findViewById(R.id.rv_paymentmethods);
        progressBar =  findViewById(R.id.progressBar);
        txt_amount_payment = findViewById(R.id.txt_amount_payment);

        progressBar.setVisibility(View.VISIBLE);
        //get the viewModel from the factory
        PaymentMethodsViewModelFactory factory = InjectorUtils.providePaymentMethodsViewModelFactory(this );
        mViewModel = ViewModelProviders.of(this,factory).get(PaymentMethodsViewModel.class);
        mViewModel.getMethods().observe(this, new Observer<List<PaymentMethod>>() {
            @Override
            public void onChanged(@Nullable List<PaymentMethod> methods) {
                progressBar.setVisibility(View.GONE);
                if (methods != null)
                    setMethodsAdapter(methods);
            }
        });
        txt_amount_payment.setText(String.valueOf (monto));
    }

    public void setMethodsAdapter (List<PaymentMethod> methods) {
        GridLayoutManager layoutManager = new GridLayoutManager(this,1,GridLayoutManager.VERTICAL,false);
        rv_paymentmethods.setLayoutManager(layoutManager);
        mAdapter = new PaymentMethodsAdapter(methods,this);
        rv_paymentmethods.setAdapter(mAdapter);
    }


    @Override
    public void onListItemClick(int itemPosition) {

    }
}


